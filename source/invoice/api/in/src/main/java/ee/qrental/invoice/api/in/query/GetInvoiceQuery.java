package ee.qrental.invoice.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrental.invoice.api.in.response.InvoiceResponse;

import java.util.List;

public interface GetInvoiceQuery extends BaseGetQuery<InvoiceUpdateRequest, InvoiceResponse> {
  InvoiceImmutableResponse getImmutableDataById(final Long id);

  List<InvoiceResponse> getAllByCalculationId(final Long calculationId);
}
