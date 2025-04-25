package ee.qrent.queue.api.in;

public interface QueueEntryPushUseCase {
  void push(final QueueEntryPushRequest pushRequest);
}
