package ee.qrental.invoice.core.service;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.*;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import ee.qrental.invoice.api.out.InvoiceCalculationAddPort;
import ee.qrental.invoice.core.mapper.InvoiceCalculationAddRequestMapper;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceCalculation;
import ee.qrental.invoice.domain.InvoiceCalculationResult;
import ee.qrental.invoice.domain.InvoiceItem;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService implements InvoiceCalculationAddUseCase {
  private final InvoiceCalculationAddRequestMapper invoiceCalculationAddRequestMapper;
  private final InvoiceCalculationBusinessRuleValidator invoiceCalculationBusinessRuleValidator;
  private final InvoiceCalculationAddPort invoiceCalculationAddPort;
  private final InvoiceCalculationPeriodService invoiceCalculationPeriodService;
  private final InvoiceToPdfConverter invoiceToPdfConverter;
  private final InvoiceToPdfModelMapper invoiceToPdfModelMapper;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetFirmQuery firmQuery;
  private final GetBalanceQuery balanceQuery;
  private final EmailSendUseCase emailSendUseCase;

  @Transactional
  @Override
  public void add(final InvoiceCalculationAddRequest addRequest) {
    final var domain = invoiceCalculationAddRequestMapper.toDomain(addRequest);
    final var violationsCollector = invoiceCalculationBusinessRuleValidator.validateAdd(domain);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
    final var actionDate = addRequest.getActionDate();
    final var weekIterator = invoiceCalculationPeriodService.getWeekIterator(actionDate);
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var weekNumber = week.weekNumber();
      final var weekStartDay = week.start();
      final var weekEndDay = week.end();
      final var filter = PeriodFilter.builder().dateStart(weekStartDay).datEnd(weekEndDay).build();
      // TODO move to PeriodFilter
      final var weekNegativeTransactions =
          transactionQuery.getAllByFilter(filter).stream()
              .filter(tx -> tx.getRealAmount().compareTo(BigDecimal.ZERO) < 0)
              .collect(toList());

      final var driverVsTransaction =
          weekNegativeTransactions.stream().collect(groupingBy(TransactionResponse::getDriverId));

      if (driverVsTransaction.isEmpty()) {
        System.out.printf(
            "Invoices for Week %d: was not created. No transactions in period: [%s, %s]%n",
            weekNumber, weekStartDay, weekEndDay);
        continue;
      }

      for (Map.Entry<Long, List<TransactionResponse>> entry : driverVsTransaction.entrySet()) {
        final var driversTransactions = entry.getValue();
        final var driverId = entry.getKey();
        final var driver = driverQuery.getById(driverId);
        final var driverCompanyVat = driver.getCompanyVat();
        final var driverInfo =
            String.format(
                "%s %s, %d", driver.getFirstName(), driver.getLastName(), driver.getIsikukood());
        final var driversCalSign = getActiveCallSign(driverId, weekStartDay);
        if (driversTransactions.isEmpty()) {
          System.out.printf(
              "Invoice for Week %d and Driver with call Sign %d: was not created. No transactions in period: [%s, %s]%n",
              weekNumber, driversCalSign, weekStartDay, weekEndDay);
          continue;
        }
        final var qFirmId = driver.getQFirmId();
        final var qFirm = firmQuery.getById(qFirmId);

        final var invoiceNumber = getInvoiceNumber(week, driversCalSign);
        final var previousWeek = weekNumber - 1;
        final var previousWeekBalance =
            balanceQuery.getByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeek);
        final var previousWeekBalanceAmount = previousWeekBalance.getAmount();

        final var currentWeekBalance =
            balanceQuery.getByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), weekNumber);
        final var fee = currentWeekBalance.getFee();
        final var invoiceItems = getInvoiceItems(driversTransactions);
        final var invoice =
            Invoice.builder()
                .id(null)
                .number(invoiceNumber)
                .weekNumber(weekNumber)
                .driverId(driverId)
                .driverCompany(driver.getCompanyName())
                .driverInfo(driverInfo)
                .driverCompanyAddress(driver.getCompanyAddress())
                .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
                .driverCallSign(driversCalSign)
                .driverCompanyVat(driverCompanyVat)
                .qFirmId(qFirmId)
                .qFirmName(qFirm.getFirmName())
                .qFirmBank(qFirm.getBank())
                .qFirmIban(qFirm.getIban())
                .qFirmRegNumber(qFirm.getRegNumber())
                .qFirmVatNumber(qFirm.getVatNumber())
                .created(actionDate)
                .balance(previousWeekBalanceAmount)
                .fee(fee)
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
      }
    }
    invoiceCalculationAddPort.add(domain);
    sendEmails(domain);
  }

  private Integer getActiveCallSign(final Long driverId, final LocalDate date) {
    final var driversCallSignLink =
        callSignLinkQuery.getCallSignLinkByDriverIdAndDate(driverId, date);
    if (driversCallSignLink == null) {
      System.out.println("No Active call sign was found for driver with ID: " + driverId);
      return Integer.MIN_VALUE;
    }
    return driversCallSignLink.getCallSign();
  }

  private void sendEmails(InvoiceCalculation invoiceCalculation) {
    invoiceCalculation.getResults().stream()
        .map(InvoiceCalculationResult::getInvoice)
        .forEach(
            invoice -> {
              final var driverId = invoice.getDriverId();
              final var driver = driverQuery.getById(driverId);
              if (!driver.getNeedInvoicesByEmail()) {
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
            });
  }

  private InputStream getAttachment(final Invoice invoice) {
    final var invoicePdfModel = invoiceToPdfModelMapper.getPdfModel(invoice);

    return invoiceToPdfConverter.getPdfInputStream(invoicePdfModel);
  }

  private String getInvoiceNumber(final Week week, final Integer callSign) {
    final var year = week.getYear();
    final var weekNumber = week.weekNumber();
    return String.format("%d%d%d", year, weekNumber, callSign);
  }

  private List<InvoiceItem> getInvoiceItems(final List<TransactionResponse> transactions) {
    final var typeVsTransactions =
        transactions.stream().collect(groupingBy(TransactionResponse::getType));

    return typeVsTransactions.entrySet().stream()
        .map(entry -> getInvoiceItem(entry.getKey(), entry.getValue()))
        .collect(toList());
  }

  private InvoiceItem getInvoiceItem(String type, List<TransactionResponse> transactions) {
    final var amount =
        transactions.stream().map(TransactionResponse::getRealAmount).reduce(BigDecimal::add).get();
    final var transactionTypeDescription = transactions.get(0).getTypeDescription();

    return InvoiceItem.builder()
        .type(type)
        .description(transactionTypeDescription)
        .amount(amount)
        .build();
  }
}
