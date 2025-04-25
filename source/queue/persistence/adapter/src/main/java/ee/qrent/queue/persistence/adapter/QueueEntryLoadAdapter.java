package ee.qrent.queue.persistence.adapter;

import ee.qrent.queue.api.out.QueueEntryLoadPort;
import ee.qrent.queue.domain.QueueEntry;
import ee.qrent.queue.persistence.mapper.QueueEntryAdapterMapper;
import ee.qrent.queue.persistence.repository.QueueEntryRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class QueueEntryLoadAdapter implements QueueEntryLoadPort {
  private final QueueEntryRepository repository;
  private final QueueEntryAdapterMapper mapper;

  @Override
  public List<QueueEntry> loadByProcessed(final boolean processed) {

    return repository.findByProcessed(processed).stream()
        .map(queueEntry -> mapper.mapToDomain(queueEntry))
        .toList();
  }

  @Override
  public List<QueueEntry> loadByTypeAndProcessedAtBeforeTime(
      final boolean processed, final LocalDateTime time) {

    return repository.findByTypeAndProcessedAtBeforeTime(processed, time).stream()
        .map(queueEntry -> mapper.mapToDomain(queueEntry))
        .toList();
  }
}
