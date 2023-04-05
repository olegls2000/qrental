package ee.qrental.invoice.core.mapper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
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

  @Override
  public Invoice toDomain(InvoiceAddRequest request) {
    final var driver = driverQuery.getById(request.getDriverId());
    final var filter =
        YearAndWeekAndDriverFilter.builder()
            .driverId(request.getDriverId())
            .year(request.getYear())
            .week(request.getWeek())
            .build();

    return Invoice.builder()
        .id(null)
        .number("RANDOM-1234")
        .driverId(request.getDriverId())
        .driverCallSign(null)
        .driverCompany(driver.getCompanyName())
        .driverCompanyAddress(driver.getCompanyAddress())
        .driverCompanyRegNumber(driver.getCompanyRegistrationNumber())
        .qFirmId(request.getQFirmId())
        .qFirmName("Fake name")
        .qFirmRegNumber("Fake Reg Number")
        .qFirmVatNumber("Fake VAT Number")
        .qFirmBank("Fake Bank")
        .qFirmIban("Fake IBAN")
        .items(getInvoiceItems(filter))
        .created(LocalDate.now())
        .comment(request.getComment())
        .build();
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
