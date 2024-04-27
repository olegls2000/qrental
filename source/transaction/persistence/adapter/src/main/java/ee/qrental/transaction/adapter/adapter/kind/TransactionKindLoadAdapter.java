package ee.qrental.transaction.adapter.adapter.kind;

import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.adapter.mapper.kind.TransactionKindAdapterMapper;
import ee.qrental.transaction.adapter.repository.kind.TransactionKindRepository;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.domain.kind.TransactionKind;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindLoadAdapter implements TransactionKindLoadPort {

  private final TransactionKindRepository repository;
  private final TransactionKindAdapterMapper mapper;

  @Override
  public List<TransactionKind> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public TransactionKind loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public TransactionKind loadByName(final String name) {
    return mapper.mapToDomain(repository.findByName(name));
  }

  @Override
  public TransactionKind loadByCode(final String code) {
    return mapper.mapToDomain(repository.findByCode(code));
  }

  @Override
  public List<TransactionKind> loadAllByCodeIn(final List<String> codes) {
    return repository.findAllByCodeIn(codes).stream().map(mapper::mapToDomain).collect(toList());
  }
}
