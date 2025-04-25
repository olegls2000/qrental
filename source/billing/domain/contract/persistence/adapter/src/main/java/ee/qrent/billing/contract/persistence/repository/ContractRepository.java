package ee.qrent.billing.contract.persistence.repository;

import ee.qrent.billing.contract.persistence.entity.jakarta.ContractJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository {
  List<ContractJakartaEntity> findAll();

  ContractJakartaEntity save(final ContractJakartaEntity entity);

  ContractJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  ContractJakartaEntity getByNumber(final String number);

  ContractJakartaEntity findLatestByDriverId(final Long driverId);

  List<ContractJakartaEntity> findActiveByDate(final LocalDate date);

  ContractJakartaEntity findActiveByDateAndDriverId(final LocalDate date, final Long driverId);

  Long findCountActiveByDate(final LocalDate date);

  List<ContractJakartaEntity> findClosedByDate(final LocalDate date);

  Long findCountClosedByDate(final LocalDate date);

  List<ContractJakartaEntity> findAllByDriverId(final Long driverId);
}
