package ee.qrent.billing.contract.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceAddRequest;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.domain.Absence;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbsenceAddRequestValidator implements AddRequestValidator<AbsenceAddRequest> {
  private final GetBalanceQuery balanceQuery;
  private final GetQWeekQuery qWeekQuery;
  private final AbsenceLoadPort loadPort;

  public ViolationsCollector validate(final AbsenceAddRequest addRequest) {
    final var driverId = addRequest.getDriverId();
    final var dateStart = addRequest.getDateStart();
    final var dateEnd = addRequest.getDateEnd();
    final var violationsCollector = new ViolationsCollector();

    final var overlappedAbsences = getOverlappedAbsences(driverId, dateStart, dateEnd);
    if (!overlappedAbsences.isEmpty()) {
      final var dateStartString = dateStart.toString();
      final var dateEndString = dateEnd == null ? "empty" : dateEnd.toString();

      violationsCollector.collect(
          format(
              "Absences can not be added for the driver id: %d and time interval: [%s ... %s ] already exists.",
              driverId, dateStartString, dateEndString));
    }

    return collectViolationsForOverlappingWithBalanceCalculation(dateStart, violationsCollector);
  }

  private List<Absence> getOverlappedAbsences(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {
    if (dateEnd == null) {
      return loadPort.loadByDriverIdAndDateStart(driverId, dateStart);
    }
    return loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, dateStart, dateEnd);
  }

  private ViolationsCollector collectViolationsForOverlappingWithBalanceCalculation(
      final LocalDate dateStart, final ViolationsCollector violationCollector) {
    final var latestBalance = balanceQuery.getLatest();
    if (latestBalance == null) {
      return violationCollector;
    }

    final var latestBalanceQWeekId = latestBalance.getQWeekId();
    final var latestQWeek = qWeekQuery.getById(latestBalanceQWeekId);
    if (latestQWeek.getEnd().equals(dateStart) || latestQWeek.getEnd().isAfter(dateStart)) {
      violationCollector.collect(
          "Absences can not be modified. Balance has been calculated for the related time period");
    }
    return violationCollector;
  }
}
