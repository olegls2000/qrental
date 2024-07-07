package ee.qrental.invoice.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.invoice.api.in.request.InvoiceCalculationUpdateRequest;
import ee.qrental.invoice.api.in.response.InvoiceCalculationResponse;

public interface GetInvoiceCalculationQuery
    extends BaseGetQuery<InvoiceCalculationUpdateRequest, InvoiceCalculationResponse> {

  Long getLastCalculatedQWeekId();
}
