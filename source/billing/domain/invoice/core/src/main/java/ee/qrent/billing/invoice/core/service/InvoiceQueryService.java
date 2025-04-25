package ee.qrent.billing.invoice.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.invoice.api.in.query.GetInvoiceQuery;
import ee.qrent.billing.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrent.billing.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrent.billing.invoice.api.in.response.InvoiceResponse;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.invoice.core.mapper.InvoiceResponseMapper;
import ee.qrent.billing.invoice.core.mapper.InvoiceUpdateRequestMapper;

import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceQueryService implements GetInvoiceQuery {

  private final InvoiceLoadPort loadPort;
  private final InvoiceResponseMapper responseMapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;

  @Override
  public List<InvoiceResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(responseMapper::toResponse)
        .sorted(getInvoiceYearAndWeekComparator())
        .collect(toList());
  }

  @Override
  public InvoiceResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return responseMapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public InvoiceUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public InvoiceImmutableResponse getImmutableDataById(Long id) {
    return responseMapper.toImmutableData(loadPort.loadById(id));
  }

  @Override
  public List<InvoiceResponse> getAllByCalculationId(Long calculationId) {
    return loadPort.loadAllByCalculationId(calculationId).stream()
        .map(responseMapper::toResponse)
        .sorted(getInvoiceYearAndWeekComparator())
        .toList();
  }

  private Comparator<InvoiceResponse> getInvoiceYearAndWeekComparator() {
    return (invoice1, invoice2) -> {
      final var yearComparison = invoice2.getYear().compareTo(invoice1.getYear());
      if (yearComparison != 0) {
        return yearComparison;
      }

      return invoice2.getWeekNumber().compareTo(invoice1.getWeekNumber());
    };
  }
}
