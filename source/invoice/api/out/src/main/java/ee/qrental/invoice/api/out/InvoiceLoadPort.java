package ee.qrental.invoice.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.invoice.domain.Invoice;

public interface InvoiceLoadPort extends LoadPort<Invoice> {

  Invoice loadByWeekAndDriverAndFirm(
      final Integer weekNumber, final Long driverId, final Long firmId);

  Invoice loadByQWeekIdAndDriverId(final Long qWeekId, final Long driverId);
}
