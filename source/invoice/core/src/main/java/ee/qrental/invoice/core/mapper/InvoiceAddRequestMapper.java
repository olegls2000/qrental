package ee.qrental.invoice.core.mapper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.common.core.utils.QWeek;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.request.InvoiceAddRequest;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceAddRequestMapper implements AddRequestMapper<InvoiceAddRequest, Invoice> {

  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetFirmQuery firmQuery;

  @Override
  public Invoice toDomain(InvoiceAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var callSign = callSignLinkQuery.getCallSignLinkByDriverId(driverId).getCallSign();
    final var year = request.getYear();
    final var week = request.getWeek();

    final var qFirmId = request.getQFirmId();
    final var qFirm = firmQuery.getById(qFirmId);

    final var filter =
        YearAndWeekAndDriverFilter.builder().driverId(driverId).year(year).week(week).build();

    return Invoice.builder()
        .id(null)
        .number(getInvoiceNumber(year, week, callSign))
        .weekNumber(week.getNumber())
        .driverId(driverId)
        .driverCallSign(callSign)
        .driverCompany(driver.getCompanyName())
        .driverCompanyAddress(driver.getCompanyAddress())
        .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
        .qFirmId(qFirmId)
        .qFirmName(qFirm.getFirmName())
        .qFirmRegNumber(qFirm.getRegNumber())
        .qFirmVatNumber(qFirm.getVatNumber())
        .qFirmBank(qFirm.getBank())
        .qFirmIban(qFirm.getIban())
        .items(getInvoiceItems(filter))
        .created(LocalDate.now())
        .comment(request.getComment())
        .build();
  }

  private String getInvoiceNumber(final Integer year, final QWeek week, final Integer callSign) {
    final var weekNumber = week.getNumber();
    return String.format("%d%d%d", year, weekNumber, callSign);
  }

  private List<InvoiceItem> getInvoiceItems(final YearAndWeekAndDriverFilter filter) {
    final Map<String, List<TransactionResponse>> transactionsGroupedByType =
        getTransactionsGroupedByType(filter);

    return transactionsGroupedByType.entrySet().stream()
        .map(entry -> getInvoiceItem(entry.getKey(), entry.getValue()))
        .collect(toList());
  }

  private Map<String, List<TransactionResponse>> getTransactionsGroupedByType(
      final YearAndWeekAndDriverFilter filter) {
    return transactionQuery.getAllByFilter(filter).stream()
        .collect(groupingBy(TransactionResponse::getType));
  }

  private InvoiceItem getInvoiceItem(
      final String transactionType, final List<TransactionResponse> transactions) {
    return InvoiceItem.builder()
        .type(transactionType)
        .amount(
            transactions.stream()
                .map(TransactionResponse::getRealAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add))
        .build();
  }
}
