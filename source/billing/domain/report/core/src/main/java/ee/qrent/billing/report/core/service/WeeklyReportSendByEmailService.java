package ee.qrent.billing.report.core.service;

import static java.util.Collections.singletonList;

import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.EntryType;
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
  private final GetCallSignLinkQuery callSignLinkQuery;
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
    properties.put("driverFirstName", driver.getFirstName());
    properties.put("driverLastName", driver.getLastName());
    properties.put("driverTaxNumber", driver.getTaxNumber());
    properties.put("callSign", getCallSign(driverId, report.getQWeekId()));

    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(getQueueEntryType(report.getType()))
            .payloadRecipients(singletonList(recipient))
            .payloadAttachment(attachment)
            .payloadProperties(properties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }

  private Integer getCallSign(final Long driverId, final Long qWeekId) {
    final var activeCallSignLink =
        callSignLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);

    return activeCallSignLink.getCallSign();
  }

  private EntryType getQueueEntryType(WeeklyReportType reportType) {

    return EntryType.valueOf(reportType.name() + "_EMAIL");
  }
}
