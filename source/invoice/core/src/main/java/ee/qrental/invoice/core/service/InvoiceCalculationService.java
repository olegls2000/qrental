package ee.qrental.invoice.core.service;

import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.query.GetFirmLinkQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.firm.api.in.response.FirmResponse;
import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import ee.qrental.invoice.api.out.InvoiceCalculationAddPort;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrental.invoice.core.mapper.InvoiceCalculationAddRequestMapper;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceCalculation;
import ee.qrental.invoice.domain.InvoiceCalculationResult;
import ee.qrental.invoice.domain.InvoiceItem;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService implements InvoiceCalculationAddUseCase {

  private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.819672d);
  private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, Month.JANUARY, 2);

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetFirmLinkQuery firmLinkQuery;
  private final EmailSendUseCase emailSendUseCase;
  private final InvoiceCalculationLoadPort loadPort;
  private final InvoiceCalculationAddRequestMapper addRequestMapper;
  private final InvoiceCalculationBusinessRuleValidator invoiceCalculationBusinessRuleValidator;
  private final InvoiceCalculationAddPort invoiceCalculationAddPort;
  private final InvoiceToPdfConverter invoiceToPdfConverter;
  private final InvoiceToPdfModelMapper invoiceToPdfModelMapper;

  @Transactional
  @Override
  public void add(final InvoiceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
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
    getQWeeksForCalculationOrdered(latestCalculatedWeek, requestedQWeek);
    final var violationsCollector = invoiceCalculationBusinessRuleValidator.validateAdd(domain);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
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
      final var qFirm = firmQuery.getById(qFirmId);

      return qFirm;
    }
    final var qFirmId = firmLink.getFirmId();
    final var qFirm = firmQuery.getById(qFirmId);

    return qFirm;
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
                System.out.println(
                    "Invoice Sum is 0 EUR, no need to send invoice to Driver");
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

              final var emailSendRequest =
                  EmailSendRequest.builder()
                      .type(EmailType.INVOICE)
                      .recipients(singletonList(recipient))
                      .attachment(attachment)
                      .properties(properties)
                      .build();
              emailSendUseCase.sendEmail(emailSendRequest);
              System.out.println("Email was sent: " + emailSendRequest);

              progressTracking(handledInvoices, invoicesCount);
              final var handledInvoicesInt = handledInvoices.getAndIncrement();
              System.out.println(
                  format("Handled %d from %d invoices", handledInvoicesInt, invoicesCount));
            });
  }

  private void progressTracking(final AtomicInteger handledInvoices, final int invoicesCount) {
    final var handledInvoicesInt = handledInvoices.getAndIncrement();
    System.out.println(format("Handled %d from %d invoices", handledInvoicesInt, invoicesCount));
  }

  private InputStream getAttachment(final Invoice invoice) {
    final var invoicePdfModel = invoiceToPdfModelMapper.getPdfModel(invoice);

    return invoiceToPdfConverter.getPdfInputStream(invoicePdfModel);
  }

  // TODO move to the Qweek Service
  private List<QWeekResponse> getQWeeksForCalculationOrdered(
      final QWeekResponse lastCalculationWeek, final QWeekResponse latestRequestedWeek) {
    final var qWeeksForCalculation =
        lastCalculationWeek == null
            ? qWeekQuery.getAllBeforeById(latestRequestedWeek.getId())
            : qWeekQuery.getAllBetweenByIdsReversedOrder(
                lastCalculationWeek.getId(), latestRequestedWeek.getId());
    qWeeksForCalculation.sort(
        comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber));

    return qWeeksForCalculation;
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
