package ee.qrental.transaction.repository.impl.type;

import ee.qrental.transaction.adapter.repository.TransactionTypeRepository;
import ee.qrental.transaction.entity.jakarta.type.TransactionTypeJakartaEntity;
import ee.qrental.transaction.repository.spring.type.TransactionTypeSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeRepositoryImpl implements TransactionTypeRepository {

  private final TransactionTypeSpringDataRepository springDataRepository;

  @Override
  public List<TransactionTypeJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public TransactionTypeJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public TransactionTypeJakartaEntity findByName(final String name) {
    return springDataRepository.findByName(name);
  }

  @Override
  public List<TransactionTypeJakartaEntity> findAllByNameIn(List<String> names) {
    return springDataRepository.findAllByNameIn(names);
  }

  @Override
  public List<TransactionTypeJakartaEntity> findAllByKindCodesIn(final List<String> kindCodes) {
    return springDataRepository.findAllByKindCodesIn(kindCodes);
  }

  @Override
  public TransactionTypeJakartaEntity save(final TransactionTypeJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
