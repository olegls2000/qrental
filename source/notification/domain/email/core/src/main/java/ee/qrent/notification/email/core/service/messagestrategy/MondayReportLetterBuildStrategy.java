package ee.qrent.notification.email.core.service.messagestrategy;

import static ee.qrent.notification.email.api.in.request.EmailType.MONDAY_REPORT_EMAIL;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.LetterBuildStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@AllArgsConstructor
public class MondayReportLetterBuildStrategy implements LetterBuildStrategy {

  private final TemplateEngine templateEngine;

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {
    return MONDAY_REPORT_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException {
    final var recipients = emailSendRequest.getRecipients().toArray(new String[0]);
    final var from = "operation@qrent.ee";
    final var subject = "Monday financial Report";
    final var emailText = getEmailText(emailSendRequest.getProperties());

    final var helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setSubject(subject);
    helper.setText(emailText, true);
    helper.setTo(recipients);
    helper.setFrom(from);

    return message;
  }

  private String getEmailText(Map<String, Object> properties) {
    final var mondayFinancialReportTemplate = "mondayReportMailTemplate";
    final var context = new Context();
    context.setVariable("driverFirstName", properties.get("driverFirstName"));
    context.setVariable("driverLastName", properties.get("driverLastName"));
    context.setVariable("driverTaxNumber", properties.get("driverTaxNumber"));
    context.setVariable("callSign", properties.get("callSign"));

    return templateEngine.process(mondayFinancialReportTemplate, context);
  }
}
