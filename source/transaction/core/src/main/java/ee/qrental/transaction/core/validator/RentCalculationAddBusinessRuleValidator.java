package ee.qrental.transaction.core.validator;

import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationAddBusinessRuleValidator {

  private final RentCalculationLoadPort loadPort;
  private final BalanceLoadPort balanceLoadPort;
  private final GetQWeekQuery qWeekQuery;

  public ViolationsCollector validate(final RentCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    final var qWeek = qWeekQuery.getById(addRequest.getQWeekId());
    //checkIfCalculationDayIsMonday(violationsCollector);
    checkIfWeeklyRendAlreadyCalculated(qWeek, violationsCollector);
    checkIfBalanceAlreadyCalculatedForRequestedWeek(qWeek, violationsCollector);
    return violationsCollector;
  }

  private void checkIfBalanceAlreadyCalculatedForRequestedWeek(
      final QWeekResponse qWeek, final ViolationsCollector violationsCollector) {
    final var latestBalance = balanceLoadPort.loadLatest();
    if(latestBalance == null) {
      return;
    }
    if (latestBalance.getQWeekId() == qWeek.getId()) {
      final var violation =
          "Weekly Rent Calculation must be done only for the week without calculated Balance";
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }

  private void checkIfCalculationDayIsMonday(final ViolationsCollector violationsCollector) {
    final var dayOfWeek = LocalDate.now().getDayOfWeek();
    if (dayOfWeek != DayOfWeek.MONDAY) {
      final var violation = "Weekly Rent Calculation must be started on Monday only";
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }

  private void checkIfWeeklyRendAlreadyCalculated(
      final QWeekResponse qWeek, final ViolationsCollector violationsCollector) {
    final var year = qWeek.getYear();
    final var weekNumber = qWeek.getNumber();
    final var lastCalculationQWeekId = loadPort.loadLastCalculationQWeekId();
    if (lastCalculationQWeekId == null) {
      return;
    }
    final var lastCalculatedWeek = qWeekQuery.getById(lastCalculationQWeekId);

    if (lastCalculatedWeek.getYear() == year && lastCalculatedWeek.getNumber() == weekNumber) {
      final var violation =
          format("Calculation for the year %d and week %d already done.", year, weekNumber);
      System.out.println("Execution time: " + LocalTime.now() + " " + violation);
      violationsCollector.collect(violation);
    }
  }
}
