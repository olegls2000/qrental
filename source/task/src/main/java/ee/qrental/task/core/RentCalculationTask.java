package ee.qrental.task.core;

import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class RentCalculationTask {

  private final RentCalculationAddUseCase rentCalculationAddUseCase;

  /**
   *
   *   +---------------- minute (0 - 59)
   *   |  +------------- hour (0 - 23)
   *   |  |  +---------- day of month (1 - 31)
   *   |  |  |  +------- month (1 - 12)
   *   |  |  |  |  +---- day of week (0 - 6) (Sunday=0 or 7)
   *   |  |  |  |  |
   *   *  *  *  *  *  command to be executed
   *
   */

  //Scheduled task is executed at 00:05 AM on the MONDAY every week
  // @Scheduled(cron = "5 0 * * * MON")

  // every 5 min
   @Scheduled(cron = "2 0 * * * MON")
  public void scheduleTask() {
        final var addRequest = new RentCalculationAddRequest();
        addRequest.setComment("Automatically triggered rent calculation");

        rentCalculationAddUseCase.add(addRequest);
    }
}
