package ee.qrent.notification.email.core.service.messagestrategy;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.LetterBuildStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static ee.qrent.notification.email.api.in.request.EmailType.CONTRACT_EMAIL;

@AllArgsConstructor
public class ContractLetterBuildStrategy implements LetterBuildStrategy {

  private final TemplateEngine templateEngine;

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {
    return CONTRACT_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException, IOException {
    final var contractNumber = emailSendRequest.getProperties().get("contractNumber").toString();
    final var recipients = emailSendRequest.getRecipients().toArray(new String[0]);
    final var from = "billing@qrent.ee";
    final var subject = "Contract: " + contractNumber;
    final var attachmentFileName = String.format("Contract-%s.pdf", contractNumber);
    final var attachment = new ByteArrayResource(emailSendRequest.getAttachment().readAllBytes());
    final var emailText = getEmailText(contractNumber);

    final var helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setSubject(subject);
    helper.setText(emailText, true);
    helper.setTo(recipients);
    helper.setFrom(from);
    helper.addAttachment(attachmentFileName, attachment);

    return message;
  }

  private String getEmailText(final String contractNumber) {
    final var invoiceTemplate = "invoiceMailTemplate";
    final var context = new Context();
    context.setVariable("contractNumber", contractNumber);

    return templateEngine.process(invoiceTemplate, context);
  }
}
