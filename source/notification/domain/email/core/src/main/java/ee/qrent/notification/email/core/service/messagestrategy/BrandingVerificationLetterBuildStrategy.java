package ee.qrent.notification.email.core.service.messagestrategy;

import static ee.qrent.notification.email.api.in.request.EmailType.BRANDING_VERIFICATION_EMAIL;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.LetterBuildStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@AllArgsConstructor
public class BrandingVerificationLetterBuildStrategy implements LetterBuildStrategy {

  private final TemplateEngine templateEngine;

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {
    return BRANDING_VERIFICATION_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException {
    final var recipients = emailSendRequest.getRecipients().toArray(new String[0]);
    final var from = "operation@qrent.ee";
    final var subject = "BOLT-kleebiste kontrollimine | Верификация БОЛТ наклеек | Verification of BOLT stickers";
    final var emailText = getEmailText(emailSendRequest);

    final var helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setSubject(subject);
    helper.setText(emailText, true);
    helper.setTo(recipients);
    helper.setFrom(from);

    return message;
  }

  private String getEmailText(final EmailSendRequest emailSendRequest) {
    final var template = "brandingVerificationReminderMailTemplate";
    final var context = new Context();
    context.setVariable("message", "BOLT-kleebiste kinnitusperiood on lõppemas | Заканчивается срок действия верификации БОЛТ наклеек | The verification period for BOLT stickers is expiring");
    context.setVariable("driverFirstName", emailSendRequest.getProperties().get("driverFirstName"));
    context.setVariable("driverLastName", emailSendRequest.getProperties().get("driverLastName"));
    context.setVariable("carRegNumber", emailSendRequest.getProperties().get("carRegNumber"));
    context.setVariable(
        "brandingExpirationDate", emailSendRequest.getProperties().get("brandingExpirationDate"));

    return templateEngine.process(template, context);
  }
}
