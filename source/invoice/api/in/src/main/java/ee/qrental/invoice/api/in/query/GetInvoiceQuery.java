package ee.qrental.invoice.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrental.invoice.api.in.response.InvoiceResponse;

public interface GetInvoiceQuery extends BaseGetQuery<InvoiceUpdateRequest, InvoiceResponse> {

    InvoiceImmutableResponse getImmutableDataById(final Long id);

}
