package ee.qrent.notification.email.core.service.messagestrategy.report;

import static ee.qrent.notification.email.api.in.request.EmailType.FRIDAY_REPORT_EMAIL;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import org.thymeleaf.TemplateEngine;

public class FridayReportLetterBuildStrategy extends AbstractReportLetterBuildStrategy {

  public FridayReportLetterBuildStrategy(final TemplateEngine templateEngine) {
    super(templateEngine);
  }

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {

    return FRIDAY_REPORT_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException, IOException {

    useMimeMessageHelper(
        message,
        "Friday financial Report",
        emailSendRequest,
        "Friday-report.pdf",
        "fridayReportMailTemplate",
        emailSendRequest.getProperties());

    return message;
  }
}
