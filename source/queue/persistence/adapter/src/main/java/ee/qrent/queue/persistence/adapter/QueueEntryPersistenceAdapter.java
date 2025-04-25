package ee.qrent.queue.persistence.adapter;

import ee.qrent.queue.api.out.QueueEntryAddPort;
import ee.qrent.queue.api.out.QueueEntryDeletePort;
import ee.qrent.queue.api.out.QueueEntryUpdatePort;
import ee.qrent.queue.domain.QueueEntry;
import ee.qrent.queue.persistence.mapper.QueueEntryAdapterMapper;
import ee.qrent.queue.persistence.repository.QueueEntryRepository;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class QueueEntryPersistenceAdapter
    implements QueueEntryAddPort, QueueEntryUpdatePort, QueueEntryDeletePort {

  private final QueueEntryRepository repository;
  private final QueueEntryAdapterMapper mapper;

  @Override
  public void deleteAll(final Set<Long> ids) {
    repository.deleteByIds(ids);
  }

  @Override
  public QueueEntry add(final QueueEntry domain) {
    final var entity = mapper.mapToEntity(domain);
    final var savedEntity = repository.save(entity);

    return mapper.mapToDomain(savedEntity);
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }

  @Override
  public QueueEntry update(final QueueEntry domain) {
    final var entity = mapper.mapToEntity(domain);
    final var savedEntity = repository.save(entity);

    return mapper.mapToDomain(savedEntity);
  }
}
