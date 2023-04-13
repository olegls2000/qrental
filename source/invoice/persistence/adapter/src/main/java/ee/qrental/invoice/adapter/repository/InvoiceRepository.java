package ee.qrental.invoice.adapter.repository;

import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import java.util.List;

public interface InvoiceRepository {
  List<InvoiceJakartaEntity> findAll();

  InvoiceJakartaEntity save(final InvoiceJakartaEntity entity);

  InvoiceJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  InvoiceJakartaEntity findByWeekAndDriverIdAndFirmId(
      final Integer weekNumber, final Long driverId, final Long firmId);
}
