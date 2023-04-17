package ee.qrental.invoice.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.invoice.domain.InvoiceCalculation;
import java.time.LocalDate;

public interface InvoiceCalculationLoadPort extends LoadPort<InvoiceCalculation> {
  LocalDate loadLastCalculationDate();
}
