package ee.qrent.billing.ui.controller;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ee.qrent.queue.api.in.EntryType.ERROR_EMAIL;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionController {

  private final GetUserAccountQuery userAccountQuery;
  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final QDateTime qDateTime;

  @ExceptionHandler(Exception.class)
  public String handleException(final Exception exception, final Model model) {
    // TODO move to some service
    final var uuid = UUID.randomUUID().toString();
    sndEmailNotification(uuid, exception);
    exception.printStackTrace();
    model.addAttribute("uuid", uuid);
    model.addAttribute("errorCause", exception.getMessage());

    return "error";
  }

  private void sndEmailNotification(final String uuid, final Exception exception) {
    final var operators = userAccountQuery.getAllOperators();
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).toList();
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("uuid", uuid);

    emailProperties.put("errorMessage", exception.getMessage());

    final var stringWriter = new StringWriter();
    exception.printStackTrace(new PrintWriter(stringWriter));
    final var stackTrace = stringWriter.toString();
    emailProperties.put("stackTrace", stackTrace);

    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(ERROR_EMAIL)
            .payloadRecipients(recipients)
            .payloadProperties(emailProperties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
