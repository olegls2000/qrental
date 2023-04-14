package ee.qrental.invoice.core.service;

import ee.qrental.invoice.api.in.query.GetInvoiceCalculationQuery;
import ee.qrental.invoice.api.in.request.InvoiceCalculationUpdateRequest;
import ee.qrental.invoice.api.in.response.InvoiceCalculationResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationQueryService implements GetInvoiceCalculationQuery {

  @Override
  public List<InvoiceCalculationResponse> getAll() {
    // return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
    return Collections.singletonList(
        InvoiceCalculationResponse.builder()
            .id(55L)
            .invoicesCount(6)
            .actionDate(LocalDate.now())
            .comment("Mock data")
            .build());
  }

  @Override
  public InvoiceCalculationResponse getById(final Long id) {
    return InvoiceCalculationResponse.builder()
        .id(id)
        .invoicesCount(6)
        .actionDate(LocalDate.now())
        .comment("Mock data")
        .build();
    // return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return "Mock String";
    // return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public InvoiceCalculationUpdateRequest getUpdateRequestById(final Long id) {
    return InvoiceCalculationUpdateRequest.builder().id(id).comment("Mock data").build();
    // return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
