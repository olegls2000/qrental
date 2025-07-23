package ee.qrent.billing.report.core.service;

import static ee.qrent.queue.api.in.EntryType.MONDAY_REPORT_EMAIL;
import static java.util.Collections.singletonList;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportSendByEmailService implements WeeklyReportSendByEmailUseCase {

  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final WeeklyReportLoadPort weeklyReportLoadPort;
  private final WeeklyReportPdfUseCase weeklyReportPdfUseCase;
  private final GetDriverQuery driverQuery;
  private final QDateTime qDateTime;

  @SneakyThrows
  @Override
  public void sendByEmail(final WeeklyReportSendByEmailRequest request) {
    final var requestId = request.getId();
    final var report = weeklyReportLoadPort.loadById(requestId);
    final var driverId = report.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var recipient = driver.getEmail();
    final var attachment = weeklyReportPdfUseCase.getPdfInputStreamById(requestId);
    final var properties = new HashMap<String, Object>();
    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(MONDAY_REPORT_EMAIL)
            .payloadRecipients(singletonList(recipient))
            .payloadAttachment(attachment)
            .payloadProperties(properties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
