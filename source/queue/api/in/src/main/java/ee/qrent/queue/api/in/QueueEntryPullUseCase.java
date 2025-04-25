package ee.qrent.queue.api.in;

import java.util.List;

public interface QueueEntryPullUseCase {
  List<QueuePullResponse> pull();
}
