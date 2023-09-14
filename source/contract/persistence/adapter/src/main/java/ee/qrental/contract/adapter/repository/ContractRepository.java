package ee.qrental.contract.adapter.repository;

import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;
import java.util.List;

public interface ContractRepository {
  List<ContractJakartaEntity> findAll();

  ContractJakartaEntity save(final ContractJakartaEntity entity);

  ContractJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  ContractJakartaEntity getByNumber(final String number);
}
