package ee.qrent.billing.invoice.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.invoice.domain.InvoiceCalculation;

public interface InvoiceCalculationLoadPort extends LoadPort<InvoiceCalculation> {
  InvoiceCalculation loadLastCalculation();
}
