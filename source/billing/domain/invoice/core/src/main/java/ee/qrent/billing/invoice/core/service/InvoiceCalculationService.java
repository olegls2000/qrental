package ee.qrent.billing.invoice.core.service;

import static ee.qrent.queue.api.in.EntryType.INVOICE_EMAIL;
import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.firm.api.in.response.FirmResponse;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import ee.qrent.billing.invoice.api.out.InvoiceCalculationAddPort;
import ee.qrent.billing.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrent.billing.invoice.core.mapper.InvoiceCalculationAddRequestMapper;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrent.billing.invoice.domain.Invoice;
import ee.qrent.billing.invoice.domain.InvoiceCalculation;
import ee.qrent.billing.invoice.domain.InvoiceCalculationResult;
import ee.qrent.billing.invoice.domain.InvoiceItem;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService implements InvoiceCalculationAddUseCase {

  private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.819672d);

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetTransactionKindQuery transactionKindQuery;
  private final GetFirmLinkQuery firmLinkQuery;
  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final InvoiceCalculationLoadPort loadPort;
  private final InvoiceCalculationAddRequestMapper addRequestMapper;
  private final AddRequestValidator<InvoiceCalculationAddRequest> addRequestValidator;
  private final InvoiceCalculationAddPort invoiceCalculationAddPort;
  private final InvoiceToPdfConverter invoiceToPdfConverter;
  private final InvoiceToPdfModelMapper invoiceToPdfModelMapper;
  private final QDateTime qDateTime;

  @Transactional
  @Override
  public void add(final InvoiceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addRequestValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
    final var requestedQWeekId = addRequest.getQWeekId();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);
    final var actionDate = addRequest.getActionDate();
    var latestCalculatedWeek = getLatestCalculatedWeek();
    domain.setStartQWeekId(latestCalculatedWeek.getId());
    final var nextAfterLatestCalculated = qWeekQuery.getOneAfterById(latestCalculatedWeek.getId());
    final var qWeeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(
            nextAfterLatestCalculated.getId(),
            requestedQWeek.getId(),
            GetQWeekQuery.DEFAULT_COMPARATOR);

    final var drivers = driverQuery.getAll();
    qWeeksForCalculation.forEach(
        week -> {
          final var qWeekId = week.getId();
          final var weekNumber = week.getNumber();
          final var weekYear = week.getYear();
          final var weekStartDay = week.getStart();
          final var weekEndDay = week.getEnd();

          final var previousQWeek = qWeekQuery.getOneBeforeById(qWeekId);
          if (previousQWeek == null) return;
          final var previousQWeekStartDay = previousQWeek.getStart();
          final var previousQWeekEndDay = previousQWeek.getEnd();

          drivers.forEach(
              driver -> {
                final var driverCreationDate = driver.getCreatedDate();
                final var driverId = driver.getId();
                if (driverCreationDate.isAfter(weekEndDay)) {
                  System.out.println(
                      format(
                          "Invoice for the week %d will not be created, cause driver (id: %d) was not created at that period",
                          weekNumber, driverId));
                  return;
                }

                checkIfBalanceForCurrentWeekExists(driver, week);
                final var filter =
                    PeriodAndKindAndDriverTransactionFilter.builder()
                        .driverId(driverId)
                        .dateStart(weekStartDay)
                        .dateEnd(weekEndDay)
                        .transactionKindIds(
                            transactionKindQuery.getAllByCodes(asList("F", "NFA", "FA", "P", "R")).stream()
                                .map(TransactionKindResponse::getId)
                                .toList())
                        .build();
                final var driversTransactions =
                    transactionQuery.getAllByFilter(filter).stream().toList();

                final var driverCompanyVat = driver.getCompanyVat();
                final var driverInfo =
                    format(
                        "%s %s, %d",
                        driver.getFirstName(), driver.getLastName(), driver.getIsikukood());
                final var qFirm = getQFirmForInvoice(driver, weekStartDay, weekYear, weekNumber);
                final var invoiceNumber = getInvoiceNumber(weekYear, weekNumber, driverId);
                final var previousQWeekBalance = getWeekBalanceOrDefault(driverId, previousQWeek);
                final var previousWeekBalanceAmountWithoutFee =
                    previousQWeekBalance.getAmount().subtract(previousQWeekBalance.getFeeAmount());
                final var previousWeekFeeBalanceAmount = previousQWeekBalance.getFeeAmount();
                final var currentWeekFee =
                    driversTransactions.stream()
                        .filter(tx -> "F".equals(tx.getKind()))
                        .map(TransactionResponse::getRealAmount)
                        .reduce(BigDecimal::add)
                        .orElse(ZERO)
                        .negate();

                final var invoicesIncludedTransactions =
                    driversTransactions.stream()
                        .filter(TransactionResponse::getInvoiceIncluded)
                        .toList();
                final var invoiceItems = getInvoiceItems(invoicesIncludedTransactions, qFirm);

                final var filterForPreviousWeek =
                    PeriodAndKindAndDriverTransactionFilter.builder()
                        .driverId(driverId)
                        .dateStart(previousQWeekStartDay)
                        .dateEnd(previousQWeekEndDay)
                        .build();
                final var previousWeekDriversTransactions =
                    transactionQuery.getAllByFilter(filterForPreviousWeek).stream().toList();

                final var sumOfPositiveTransactionsFromPreviousWeekNotIncludedIntoInvoice =
                    previousWeekDriversTransactions.stream()
                        .filter(tx -> !tx.getInvoiceIncluded())
                        .filter(tx -> "P".equals(tx.getKind()))
                        .map(TransactionResponse::getRealAmount)
                        .reduce(BigDecimal::add)
                        .orElse(ZERO);

                final var invoice =
                    Invoice.builder()
                        .id(null)
                        .number(invoiceNumber)
                        .driverId(driverId)
                        .qWeekId(week.getId())
                        .driverCompany(driver.getCompanyName())
                        .driverInfo(driverInfo)
                        .driverCompanyAddress(driver.getCompanyAddress())
                        .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
                        .driverCompanyVat(driverCompanyVat)
                        .qFirmId(qFirm.getId())
                        .qFirmName(qFirm.getName())
                        .qFirmPostAddress(qFirm.getPostAddress())
                        .qFirmEmail(qFirm.getEmail())
                        .qFirmPhone(qFirm.getPhone())
                        .qFirmBank(qFirm.getBank())
                        .qFirmIban(qFirm.getIban())
                        .qFirmRegNumber(qFirm.getRegistrationNumber())
                        .qFirmVatNumber(qFirm.getVatNumber())
                        .created(actionDate)
                        .balance(previousWeekBalanceAmountWithoutFee)
                        .currentWeekFee(currentWeekFee)
                        .previousWeekBalanceFee(previousWeekFeeBalanceAmount)
                        .previousWeekPositiveTxSum(
                            sumOfPositiveTransactionsFromPreviousWeekNotIncludedIntoInvoice)
                        .items(invoiceItems)
                        .build();
                final var transactionIds =
                    driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
                final var result =
                    InvoiceCalculationResult.builder()
                        .invoice(invoice)
                        .transactionIds(transactionIds)
                        .build();
                domain.getResults().add(result);
              });
        });
    invoiceCalculationAddPort.add(domain);
    sendEmails(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("Invoice Calculation took %d milli seconds", calculationDuration);
  }

  private void checkIfBalanceForCurrentWeekExists(
      final DriverResponse driver, final QWeekResponse qWeek) {
    final var driverId = driver.getId();
    final var weekBalance = balanceQuery.getByDriverIdAndQWeekId(driverId, qWeek.getId());
    if (weekBalance == null) {
      throw new RuntimeException(
          format(
              "Derived Balance for the qWeek with number: %d and Driver (id: %d, first name: %s, last name: %s, created on: %s) , must exist",
              qWeek.getNumber(),
              driverId,
              driver.getFirstName(),
              driver.getLastName(),
              driver.getCreatedDate().toString()));
    }
  }

  private BalanceResponse getWeekBalanceOrDefault(final Long driverId, final QWeekResponse qWeek) {
    final var zeroBalance =
        BalanceResponse.builder()
            .qWeekId(null)
            .positiveAmount(ZERO)
            .feeAmount(ZERO)
            .feeAbleAmount(ZERO)
            .nonFeeAbleAmount(ZERO)
            .driverId(driverId)
            .build();

    if (qWeek == null) {
      return zeroBalance;
    }

    final var balancesCount = balanceQuery.getCountByDriver(driverId);
    if (balancesCount == 0) {
      return zeroBalance;
    }

    final var weekBalance = balanceQuery.getByDriverIdAndQWeekId(driverId, qWeek.getId());

    if (weekBalance == null) {
      throw new RuntimeException(
          format(
              "Balance for the qWeek %d and driver with id %d, must exist",
              qWeek.getNumber(), driverId));
    }

    return weekBalance;
  }

  private QWeekResponse getLatestCalculatedWeek() {
    var lastCalculation = loadPort.loadLastCalculation();
    if (lastCalculation == null) {

      return qWeekQuery.getFirstWeek();
    }

    return qWeekQuery.getById(lastCalculation.getEndQWeekId());
  }

  private FirmResponse getQFirmForInvoice(
      final DriverResponse driver,
      final LocalDate weekStartDay,
      final Integer weekYear,
      final Integer weekNumber) {
    final var firmLink =
        firmLinkQuery.getOneByDriverIdAndRequiredDate(driver.getId(), weekStartDay);
    if (firmLink == null) {
      System.out.printf(
          "Driver %s did not have a Firm Link on %s, current Firm will be taken for the %d year - %d week Invoice \n",
          format("%s %s", driver.getFirstName(), driver.getLastName()),
          weekStartDay.format(DateTimeFormatter.ISO_LOCAL_DATE),
          weekYear,
          weekNumber);
      final var qFirmId = driver.getQFirmId();

      return firmQuery.getById(qFirmId);
    }
    final var qFirmId = firmLink.getFirmId();

    return firmQuery.getById(qFirmId);
  }

  private void sendEmails(InvoiceCalculation invoiceCalculation) {
    final var invoicesCount = invoiceCalculation.getResults().size();
    var handledInvoices = new AtomicInteger();
    invoiceCalculation.getResults().stream()
        .map(InvoiceCalculationResult::getInvoice)
        .sorted(comparing(Invoice::getNumber))
        .forEach(
            invoice -> {
              final var invoiceSum = invoice.getSum();
              if (invoiceSum.compareTo(ZERO) == 0) {
                System.out.println("Invoice Sum is 0 EUR, no need to send invoice to Driver");
                progressTracking(handledInvoices, invoicesCount);

                return;
              }

              final var driverId = invoice.getDriverId();
              final var driver = driverQuery.getById(driverId);
              if (!driver.getNeedInvoicesByEmail()) {
                progressTracking(handledInvoices, invoicesCount);
                System.out.println(
                    "Sending Invoices by Email is not activated for Driver: "
                        + driver.getFirstName()
                        + ", "
                        + driver.getLastName());
                return;
              }
              final var recipient = driver.getEmail();
              final var attachment = getAttachment(invoice);
              final var properties = new HashMap<String, Object>();
              properties.put("invoiceNumber", invoice.getNumber());
              final var notificationQueuePushRequest =
                  QueueEntryPushRequest.builder()
                      .occurredAt(qDateTime.getNow())
                      .type(INVOICE_EMAIL)
                      .payloadRecipients(singletonList(recipient))
                      .payloadAttachment(attachment)
                      .payloadProperties(properties)
                      .build();

              notificationQueuePushUseCase.push(notificationQueuePushRequest);

              progressTracking(handledInvoices, invoicesCount);
              final var handledInvoicesInt = handledInvoices.getAndIncrement();
              System.out.printf("Handled %d from %d invoices%n", handledInvoicesInt, invoicesCount);
            });
  }

  private void progressTracking(final AtomicInteger handledInvoices, final int invoicesCount) {
    final var handledInvoicesInt = handledInvoices.getAndIncrement();
    System.out.printf("Handled %d from %d invoices%n", handledInvoicesInt, invoicesCount);
  }

  private InputStream getAttachment(final Invoice invoice) {
    final var invoicePdfModel = invoiceToPdfModelMapper.getPdfModel(invoice);

    return invoiceToPdfConverter.getPdfInputStream(invoicePdfModel);
  }

  private String getInvoiceNumber(
      final Integer weekYear, final Integer weekNumber, final Long driverId) {
    final var formattedWeekNumber = String.format("%02d", weekNumber);

    return format("%d%s%d", weekYear, formattedWeekNumber, driverId);
  }

  private List<InvoiceItem> getInvoiceItems(
      final List<TransactionResponse> transactions, final FirmResponse firm) {
    final var withoutVat = isFirmWithoutVAT(firm);
    final var typeVsTransactions =
        transactions.stream().collect(groupingBy(TransactionResponse::getType));

    return typeVsTransactions.entrySet().stream()
        .map(entry -> getInvoiceItem(entry.getKey(), entry.getValue(), withoutVat))
        .collect(toList());
  }

  private InvoiceItem getInvoiceItem(
      final String type, final List<TransactionResponse> transactions, final boolean withoutVat) {
    final var vatRate = withoutVat ? ONE : VAT_RATE;
    final var amount =
        transactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(ZERO)
            .multiply(vatRate);
    final var transactionTypeName = transactions.get(0).getType();
    final var transactionType = transactionTypeQuery.getByName(transactionTypeName);
    final var invoiceItemName = transactionType.getInvoiceName();

    return InvoiceItem.builder().type(type).description(invoiceItemName).amount(amount).build();
  }

  private boolean isFirmWithoutVAT(final FirmResponse firm) {
    return firm.getVatNumber() == null || firm.getVatNumber().isEmpty();
  }
}
