package ee.qrent.billing.transaction.persistence.repository.impl.kind;

import ee.qrent.billing.transaction.persistence.repository.kind.TransactionKindRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.kind.TransactionKindJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.kind.TransactionKindSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindRepositoryImpl implements TransactionKindRepository {

  private final TransactionKindSpringDataRepository springDataRepository;

  @Override
  public List<TransactionKindJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public TransactionKindJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public TransactionKindJakartaEntity findByName(final String name) {
    return springDataRepository.findByName(name);
  }

  @Override
  public TransactionKindJakartaEntity findByCode(final String code) {
    return springDataRepository.findByCode(code);
  }

  @Override
  public List<TransactionKindJakartaEntity> findAllByCodeIn(final List<String> codes) {
    return springDataRepository.findByCodeIn(codes);
  }

  @Override
  public TransactionKindJakartaEntity save(final TransactionKindJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
