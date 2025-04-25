package ee.qrent.queue.persistence.repository;

import ee.qrent.queue.persistence.entity.jakarta.QueueEntryJakartaEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface QueueEntryRepository {
  QueueEntryJakartaEntity save(final QueueEntryJakartaEntity entity);

  void deleteByIds(final Set<Long> id);

  void deleteById(final Long id);

  List<QueueEntryJakartaEntity> findByProcessed(final Boolean processed);

  List<QueueEntryJakartaEntity> findByTypeAndProcessedAtBeforeTime(
      final boolean processed, final LocalDateTime time);
}
