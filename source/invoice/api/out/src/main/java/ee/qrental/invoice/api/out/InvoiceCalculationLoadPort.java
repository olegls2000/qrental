package ee.qrental.invoice.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.invoice.domain.InvoiceCalculation;

public interface InvoiceCalculationLoadPort extends LoadPort<InvoiceCalculation> {
  InvoiceCalculation loadLastCalculation();
}
