package ee.qrent.queue.core;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.out.QueueEntryDeletePort;
import ee.qrent.queue.api.out.QueueEntryLoadPort;
import ee.qrent.queue.api.out.QueueEntryAddPort;
import ee.qrent.queue.api.in.*;
import ee.qrent.queue.api.out.QueueEntryUpdatePort;
import ee.qrent.queue.domain.QueueEntry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.util.List;

import static jakarta.transaction.Transactional.TxType.MANDATORY;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class QueueUseCaseService
    implements QueueEntryPushUseCase, QueueEntryPullUseCase, QueueEvictionUseCase {

  private final QueueEntryLoadPort loadPort;
  private final QueueEntryAddPort addPort;
  private final QueueEntryUpdatePort updatePort;
  private final QueueEntryDeletePort deletePort;
  private final QueueEntryPushRequestMapper pushRequestMapper;
  private final QueueEntryPullResponseMapper pullResponseMapper;
  private final QDateTime qDateTime;


  @Override
  public void push(final QueueEntryPushRequest pushRequest) {
    final var queueEntry = pushRequestMapper.toDomain(pushRequest);
    addPort.add(queueEntry);
  }


  @Override
  public List<QueuePullResponse> pull() {
    final var processed = false;
    return loadPort.loadByProcessed(processed).stream()
        .peek(this::processPulledEntry)
        .map(pullResponseMapper::toResponse)
        .toList();
  }

  private QueueEntry processPulledEntry(final QueueEntry domain) {
    domain.setProcessed(true);
    domain.setProcessedAt(qDateTime.getNow());

    return updatePort.update(domain);
  }


  @Override
  public int evict(final Duration expirationDuration) {
    final var expirationDurationInSeconds = expirationDuration.toSeconds();
    final var currentTime = qDateTime.getNow();
    final var expirationTime = currentTime.minusSeconds(expirationDurationInSeconds);
    final var processed = true;
    final var evictionCandidateIds =
        loadPort.loadByTypeAndProcessedAtBeforeTime(processed, expirationTime).stream()
            .map(entry -> entry.getId())
            .collect(toSet());
    deletePort.deleteAll(evictionCandidateIds);
    final var evictedEntriesCount = evictionCandidateIds.size();

    return evictedEntriesCount;
  }
}
