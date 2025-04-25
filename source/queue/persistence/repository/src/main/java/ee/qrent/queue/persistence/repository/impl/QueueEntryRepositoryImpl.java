package ee.qrent.queue.persistence.repository.impl;

import ee.qrent.queue.persistence.entity.jakarta.QueueEntryJakartaEntity;
import ee.qrent.queue.persistence.repository.QueueEntryRepository;
import ee.qrent.queue.persistence.repository.spring.QueueEntrySpringDataRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class QueueEntryRepositoryImpl implements QueueEntryRepository {
  private final QueueEntrySpringDataRepository springDataRepository;

  @Override
  public QueueEntryJakartaEntity save(final QueueEntryJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public void deleteByIds(final Set<Long> ids) {
    springDataRepository.deleteAllById(ids);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<QueueEntryJakartaEntity> findByProcessed(final Boolean processed) {

    return springDataRepository.findByProcessed(processed);
  }

  @Override
  public List<QueueEntryJakartaEntity> findByTypeAndProcessedAtBeforeTime(
      final boolean processed, final LocalDateTime time) {

    return springDataRepository.findByTypeAndProcessedAtBeforeTime(processed, time);
  }
}
