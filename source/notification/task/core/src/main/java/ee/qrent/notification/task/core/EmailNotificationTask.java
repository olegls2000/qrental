package ee.qrent.notification.task.core;

import ee.qrent.common.in.usecase.QTask;
import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.api.in.request.EmailType;
import ee.qrent.notification.email.api.in.usecase.EmailSendUseCase;
import ee.qrent.queue.api.in.QueueEntryPullUseCase;
import ee.qrent.queue.api.in.QueuePullResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailNotificationTask implements QTask {

  private final EmailSendUseCase emailSendUseCase;
  private final QueueEntryPullUseCase queuePullUseCase;

  @Override
  public Runnable getRunnable() {
    return () ->
        queuePullUseCase.pull().stream()
            .map(response -> mapToEmailSendRequest(response))
            .forEach(emailSendUseCase::sendEmail);
  }

  @Override
  public String getName() {
    return "EMAIL-NOTIFICATION-TASK";
  }

  private EmailSendRequest mapToEmailSendRequest(final QueuePullResponse queuePullResponse) {

    return EmailSendRequest.builder()
        .type(EmailType.valueOf(queuePullResponse.getPayloadType().name()))
        .recipients(queuePullResponse.getPayloadRecipients())
        .attachment(queuePullResponse.getPayloadAttachment())
        .properties(queuePullResponse.getPayloadProperties())
        .build();
  }
}
