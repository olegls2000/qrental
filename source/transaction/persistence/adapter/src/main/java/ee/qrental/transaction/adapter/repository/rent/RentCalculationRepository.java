package ee.qrental.transaction.adapter.repository.rent;


import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface RentCalculationRepository {
  RentCalculationJakartaEntity save(final RentCalculationJakartaEntity entity);

  List<RentCalculationJakartaEntity> findAll();

  RentCalculationJakartaEntity getReferenceById(final Long id);

  Long getLastCalculationQWeekId();
}
