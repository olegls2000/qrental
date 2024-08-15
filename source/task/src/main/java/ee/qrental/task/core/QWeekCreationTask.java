package ee.qrental.task.core;

import ee.qrental.constant.api.in.request.QWeekAddRequest;
import ee.qrental.constant.api.in.usecase.QWeekAddUseCase;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class QWeekCreationTask {

  private final QWeekAddUseCase qWeekAddUseCase;


  /**
   * +---------------- minute (0 - 59)
   * | +------------- hour (0 - 23)
   * | | +---------- day of month (1 - 31)
   * | | | +------- month (1 - 12)
   * | | | | +---- day of week (0 - 6) (Sunday=0 or 7)
   * | | | | | * * * * * command to be executed
   */

  // Scheduled task is executed at 00:02:00 AM on the MONDAY every week

  @Scheduled(cron = "0 2 * * * MON")
  public void scheduleTask() {
    final var nowDate = LocalDate.now();
    final var addRequest = new QWeekAddRequest();
    addRequest.setWeekDate(nowDate);
    qWeekAddUseCase.add(addRequest);
  }
}
