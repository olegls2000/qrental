package ee.qrent.queue.persistence.repository.spring;

import ee.qrent.queue.persistence.entity.jakarta.QueueEntryJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueEntrySpringDataRepository
    extends JpaRepository<QueueEntryJakartaEntity, Long> {

  List<QueueEntryJakartaEntity> findByProcessed(final Boolean processed);

  @Query(
      value =
          "select qq.* from q_queue qq where qq.processed = :processed and qq.processed_at < :time",
      nativeQuery = true)
  List<QueueEntryJakartaEntity> findByTypeAndProcessedAtBeforeTime(
      final boolean processed, final LocalDateTime time);
}
