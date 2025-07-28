package ee.qrent.notification.email.core.service.messagestrategy.report;

import static ee.qrent.notification.email.api.in.request.EmailType.TUESDAY_REPORT_EMAIL;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;

public class TuesdayReportLetterBuildStrategy extends AbstractReportLetterBuildStrategy {
  public TuesdayReportLetterBuildStrategy(final TemplateEngine templateEngine) {
    super(templateEngine);
  }

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {
    
    return TUESDAY_REPORT_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException, IOException {
    useMimeMessageHelper(
        message,
        "Tuesday financial Report",
        emailSendRequest,
        "Tuesday-report.pdf",
        "tuesdayReportMailTemplate",
        emailSendRequest.getProperties());

    return message;
  }
}
