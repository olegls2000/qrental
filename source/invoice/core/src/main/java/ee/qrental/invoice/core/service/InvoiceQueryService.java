package ee.qrental.invoice.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrental.invoice.api.in.response.InvoiceResponse;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.core.mapper.InvoiceResponseMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceQueryService implements GetInvoiceQuery {

  private final InvoiceLoadPort loadPort;
  private final InvoiceResponseMapper mapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;

  @Override
  public List<InvoiceResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public InvoiceResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public InvoiceUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public InvoiceImmutableResponse getImmutableDataById(Long id) {
    return mapper.toImmutableData(loadPort.loadById(id));
  }
}
