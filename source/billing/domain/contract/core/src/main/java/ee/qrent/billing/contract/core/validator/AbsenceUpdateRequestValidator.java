package ee.qrent.billing.contract.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceAddRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceDeleteRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.domain.Absence;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class AbsenceUpdateRequestValidator
    implements AddRequestValidator<AbsenceAddRequest>,
        UpdateRequestValidator<AbsenceUpdateRequest>,
        DeleteRequestValidator<AbsenceDeleteRequest> {

  private final GetBalanceQuery balanceQuery;
  private final GetQWeekQuery qWeekQuery;
  private final AbsenceLoadPort loadPort;

  @Override
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

  @Override
  public ViolationsCollector validate(final AbsenceUpdateRequest updateRequest) {
    final var id = updateRequest.getId();
    final var driverId = updateRequest.getDriverId();
    final var dateStart = updateRequest.getDateStart();
    final var dateEnd = updateRequest.getDateEnd();
    final var violationsCollector = new ViolationsCollector();
    final var overlappedAbsences = getOverlappedAbsences(driverId, dateStart, dateEnd);
    final var overlappedAbsencesExceptUpdatedCount =
        overlappedAbsences.stream().filter(absence -> !absence.getId().equals(id)).count();

    if (overlappedAbsencesExceptUpdatedCount > 0L) {
      violationsCollector.collect(
          format(
              "Absences can not be updated for the driver id: %d  and time interval: [%s ... %s ], because it overlaps with existing.",
              driverId, dateStart.toString(), dateEnd == null ? "empty" : dateEnd.toString()));
    }

    return collectViolationsForOverlappingWithBalanceCalculation(dateStart, violationsCollector);
  }

  @Override
  public ViolationsCollector validate(final AbsenceDeleteRequest deleteRequest) {
    final var violationsCollector = new ViolationsCollector();
    final var absenceToDelete = loadPort.loadById(deleteRequest.getId());
    final var absenceToDeleteDateStart = absenceToDelete.getDateStart();

    return collectViolationsForOverlappingWithBalanceCalculation(
        absenceToDeleteDateStart, violationsCollector);
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
