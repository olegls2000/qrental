package ee.qrent.queue.api.out;

import ee.qrent.queue.domain.QueueEntry;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueEntryLoadPort {
  List<QueueEntry> loadByProcessed(final boolean processed);

  List<QueueEntry> loadByTypeAndProcessedAtBeforeTime(
      final boolean processed, final LocalDateTime time);
}
