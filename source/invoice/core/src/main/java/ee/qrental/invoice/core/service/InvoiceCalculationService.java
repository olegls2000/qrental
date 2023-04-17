package ee.qrental.invoice.core.service;

import static java.util.stream.Collectors.*;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import ee.qrental.invoice.api.out.InvoiceCalculationAddPort;
import ee.qrental.invoice.core.mapper.InvoiceCalculationAddRequestMapper;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceCalculationResult;
import ee.qrental.invoice.domain.InvoiceItem;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService implements InvoiceCalculationAddUseCase {

  private final InvoiceCalculationAddPort addPort;

  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetFirmQuery firmQuery;

  private final InvoiceCalculationAddRequestMapper addRequestMapper;
  private final InvoiceCalculationBusinessRuleValidator businessRuleValidator;
  private final InvoiceCalculationPeriodService datesCalculationService;

  @Transactional
  @Override
  public void add(final InvoiceCalculationAddRequest addRequest) {
    final var domain = addRequestMapper.toDomain(addRequest);
    final var violationsCollector = businessRuleValidator.validateAdd(domain);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
    final var actionDate = addRequest.getActionDate();
    final var weekIterator = datesCalculationService.getWeekIterator(actionDate);
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var filter = PeriodFilter.builder().dateStart(week.start()).datEnd(week.end()).build();
      final var weekTransactions = transactionQuery.getAllByFilter(filter);
      final var driverVsTransaction =
          weekTransactions.stream().collect(groupingBy(TransactionResponse::getDriverId));

      if (driverVsTransaction.isEmpty()) {
        System.out.println(
            String.format(
                "Invoices for Week %d: was not created. No transactions in period: [%s, %s]",
                week.weekNumber(), week.start(), week.end()));
        continue;
      }

      for (Map.Entry<Long, List<TransactionResponse>> entry : driverVsTransaction.entrySet()) {
        final var driversTransactions = entry.getValue();

        final var driverId = entry.getKey();
        final var driver = driverQuery.getById(driverId);

        final var driversCallSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);
        final var driversCalSign = driversCallSignLink.getCallSign();
        if (driversTransactions.isEmpty()) {
          System.out.println(
              String.format(
                  "Invoice for Week %d and Driver with call Sign %d: was not created. No transactions in period: [%s, %s]",
                  week.weekNumber(), driversCalSign, week.start(), week.end()));
          continue;
        }

        final var qFirmId = 3L; // TODO get from Driver
        final var qFirm = firmQuery.getById(qFirmId);

        final var invoiceNumber = getInvoiceNumber(week, driversCalSign);
        final var invoiceItems = getInvoiceItems(driversTransactions);
        final var invoice =
            Invoice.builder()
                .id(null)
                .number(invoiceNumber)
                .weekNumber(week.weekNumber())
                .driverId(driverId)
                .driverCompany(driver.getCompanyName())
                .driverCompanyAddress(driver.getCompanyAddress())
                .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
                .driverCallSign(driversCalSign)
                .qFirmId(qFirmId)
                .qFirmName(qFirm.getFirmName())
                .qFirmBank(qFirm.getBank())
                .qFirmIban(qFirm.getIban())
                .qFirmRegNumber(qFirm.getRegNumber())
                .qFirmVatNumber(qFirm.getVatNumber())
                .created(actionDate)
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
    addPort.add(domain);
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

    return InvoiceItem.builder().type(type).amount(amount).build();
  }
}
