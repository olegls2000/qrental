package ee.qrent.billing.invoice.adapter.repository;

import ee.qrent.billing.invoice.persistence.entity.jakarta.InvoiceJakartaEntity;
import java.util.List;

public interface InvoiceRepository {
  List<InvoiceJakartaEntity> findAll();

  InvoiceJakartaEntity save(final InvoiceJakartaEntity entity);

  InvoiceJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  InvoiceJakartaEntity findByWeekAndDriverIdAndFirmId(
      final Long qWeekId, final Long driverId, final Long firmId);

  InvoiceJakartaEntity findByByQWeekIdAndDriverId(final Long qWeekId, final Long driverId);

    List<InvoiceJakartaEntity> findByCalculationId(final Long calculationId);
}
