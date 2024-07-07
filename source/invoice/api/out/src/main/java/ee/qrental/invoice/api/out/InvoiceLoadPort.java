package ee.qrental.invoice.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.invoice.domain.Invoice;

import java.util.List;

public interface InvoiceLoadPort extends LoadPort<Invoice> {

  Invoice loadByWeekAndDriverAndFirm(
      final Long qWeekId, final Long driverId, final Long firmId);

  Invoice loadByQWeekIdAndDriverId(final Long qWeekId, final Long driverId);

    List<Invoice> loadAllByCalculationId(final Long calculationId);
}
