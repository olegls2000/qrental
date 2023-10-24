package ee.qrental.task.core;

import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class RentCalculationTask {

private final RentCalculationAddUseCase rentCalculationAddUseCase;

    /**
     * Scheduled task is executed at 10:15 AM on the 15th day of every month
     */
    @Scheduled(cron = "0 15 10 15 * ?")
   // @Scheduled(cron = "0 * * * * MON-FRI")
    public void scheduleTask() {

        final var addRequest = new RentCalculationAddRequest();
        addRequest.setComment("Automatically triggered rent calculation");

        rentCalculationAddUseCase.add(addRequest);
    }
}
