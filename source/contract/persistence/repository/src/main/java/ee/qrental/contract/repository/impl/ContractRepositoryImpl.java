package ee.qrental.contract.repository.impl;

import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;
import ee.qrental.contract.repository.spring.ContractSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

  private final ContractSpringDataRepository springDataRepository;

  @Override
  public List<ContractJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public ContractJakartaEntity save(final ContractJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public ContractJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public ContractJakartaEntity getByNumber(final String number) {
    return springDataRepository.findByNumber(number);
  }
}
