package ee.qrent.billing.invoice.api.in.usecase;

import ee.qrent.common.in.usecase.DeleteUseCase;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationDeleteRequest;

public interface InvoiceCalculationDeleteUseCase
    extends DeleteUseCase<InvoiceCalculationDeleteRequest> {}
