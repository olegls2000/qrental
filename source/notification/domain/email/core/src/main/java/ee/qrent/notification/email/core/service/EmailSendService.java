package ee.qrent.notification.email.core.service;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.out.port.AddPort;
import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.api.in.usecase.EmailSendUseCase;

import java.util.List;

import ee.qrent.notification.email.domain.EmailNotification;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
public class EmailSendService implements EmailSendUseCase {

  private final JavaMailSender mailSender;
  private final List<LetterBuildStrategy> letterBuildStrategies;
  private final AddPort<EmailNotification> addPort;
  private final AddRequestMapper<EmailSendRequest, EmailNotification> addRequestMapper;

  @SneakyThrows
  @Override
  public void sendEmail(final EmailSendRequest request) {
    final var message =
        letterBuildStrategies.stream()
            .filter(letterBuildStrategy -> letterBuildStrategy.canApply(request))
            .findFirst()
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "No Email Letter build Strategy were found for " + request.getType()))
            .process(request, mailSender.createMimeMessage());
    mailSender.send(message);
    final var domain = addRequestMapper.toDomain(request);
    addPort.add(domain);
  }
}
