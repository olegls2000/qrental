package ee.qrent.billing.transaction.persistence.repository.impl.type;

import ee.qrent.billing.transaction.persistence.repository.TransactionTypeRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.type.TransactionTypeJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.type.TransactionTypeSpringDataRepository;
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
  public TransactionTypeJakartaEntity findByCode(final String code) {
    return springDataRepository.findByCode(code);
  }

  @Override
  public List<TransactionTypeJakartaEntity> findAllByCodeIn(List<String> codes) {
    return springDataRepository.findAllByNameIn(codes);
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
