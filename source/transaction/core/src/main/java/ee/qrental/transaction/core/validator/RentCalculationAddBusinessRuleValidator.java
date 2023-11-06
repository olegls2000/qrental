package ee.qrental.transaction.core.validator;

import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationAddBusinessRuleValidator {

  private final RentCalculationLoadPort loadPort;
  private final BalanceLoadPort balanceLoadPort;

  public ViolationsCollector validate(final RentCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfCalculationDayIsMonday(addRequest, violationsCollector);
    checkIfWeeklyRendAlreadyCalculated(addRequest, violationsCollector);
    checkIfBalanceAlreadyCalculatedForRequestedWeek(addRequest, violationsCollector);
    return violationsCollector;
  }

  private void checkIfBalanceAlreadyCalculatedForRequestedWeek(
          final RentCalculationAddRequest addRequest, final ViolationsCollector violationsCollector) {

    final var latestBalanceCalculatedDay = balanceLoadPort.loadLatestCalculatedDateOrDefault();

    final var  year = addRequest.getYear();
    final var  weekNumber = addRequest.getWeekNumber();
    final var rentCalculationStartDate = QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber);

    if (rentCalculationStartDate.isBefore(latestBalanceCalculatedDay)) {
      final var violation = "Weekly Rent Calculation must be done only for the week without calculated Balance";
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }

  private void checkIfCalculationDayIsMonday(
      final RentCalculationAddRequest addRequest, final ViolationsCollector violationsCollector) {
    final var dayOfWeek = addRequest.getActionDate().getDayOfWeek();
    if (dayOfWeek != DayOfWeek.MONDAY) {
      final var violation = "Weekly Rent Calculation must be started on Monday only";
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }

  private void checkIfWeeklyRendAlreadyCalculated(
      final RentCalculationAddRequest addRequest, final ViolationsCollector violationsCollector) {
    final var calculationDate = addRequest.getActionDate();
    final var weekNumber = QTimeUtils.getWeekNumber(calculationDate);
    final var lastCalculationDate = loadPort.loadLastCalculationDate();
    if (calculationDate.isBefore(lastCalculationDate) || calculationDate.equals(lastCalculationDate)) {
      final var violation =
          format("Calculation for week %d already done till %s", weekNumber, lastCalculationDate);
      System.out.println("Execution time: " + LocalTime.now() + " " + violation);
      violationsCollector.collect(violation);
    }
  }
}
