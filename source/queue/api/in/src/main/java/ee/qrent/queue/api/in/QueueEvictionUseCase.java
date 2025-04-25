package ee.qrent.queue.api.in;

import java.time.Duration;

public interface QueueEvictionUseCase {
  int evict(final Duration timeout);
}
