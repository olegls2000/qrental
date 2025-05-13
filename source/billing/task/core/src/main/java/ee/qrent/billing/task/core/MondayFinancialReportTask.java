package ee.qrent.billing.task.core;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.QTask;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

import static ee.qrent.queue.api.in.EntryType.MONDAY_FINANCIAL_EMAIL;

@AllArgsConstructor
public class MondayFinancialReportTask implements QTask {

  private final GetDriverQuery getDriverQuery;
  private final QueueEntryPushUseCase queueEntryPushUseCase;
  private final QDateTime qDateTime;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final var drivers = getDriverQuery.getAll();
      for (var driver : drivers) {
        final var payloadProperties = new HashMap<String, Object>();
        payloadProperties.put("driverFirstName", driver.getFirstName());
        payloadProperties.put("driverLastName", driver.getLastName());
        payloadProperties.put("driverTaxNumber", driver.getTaxNumber());
        payloadProperties.put("callSign", driver.getCallSign());

        final var pushRequest =
            QueueEntryPushRequest.builder()
                .occurredAt(qDateTime.getNow())
                .payloadRecipients(Arrays.asList(driver.getEmail()))
                .type(MONDAY_FINANCIAL_EMAIL)
                .payloadAttachment(null)
                .payloadProperties(payloadProperties)
                .build();

        queueEntryPushUseCase.push(pushRequest);
      }
    };
  }

  @Override
  public String getName() {
    return "FIRST-FINANCIAL-REPORT-TASK";
  }
}
