package ee.qrent.billing.contract.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.in.response.AbsenceResponse;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.core.mapper.AbsenceResponseMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceUpdateRequestMapper;
import java.time.LocalDate;
import java.util.List;

import ee.qrent.billing.contract.domain.Absence;
import lombok.AllArgsConstructor;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.*;

@AllArgsConstructor
public class AbsenceQueryService implements GetAbsenceQuery {

  private static final Long DEFAULT_PERIOD_IN_WEEKS = 10L;

  private final GetContractQuery contractQuery;
  private final GetQWeekQuery qWeekQuery;
  private final AbsenceLoadPort loadPort;
  private final AbsenceResponseMapper mapper;
  private final AbsenceUpdateRequestMapper updateRequestMapper;
  private final QDateTime qDateTime;

  @Override
  public List<AbsenceResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public AbsenceResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public AbsenceUpdateRequest getUpdateRequestById(Long id) {

    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public List<AbsenceResponse> getActualAbsencesByDriverId(final Long driverId) {
    final var activeContract = contractQuery.getCurrentActiveByDriverId(driverId);
    final var startDate = getContractLastProlongationStartDate(activeContract);

    return loadPort.loadByDriverIdAndDateStart(driverId, startDate).stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public Long getAbsencesDayCountsByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return loadPort
        .loadByDriverIdAndDateStartAndDateEnd(driverId, qWeek.getStart(), qWeek.getEnd())
        .stream()
        .map(absence -> getDaysCount(absence, qWeek))
        .collect(summingLong(Long::longValue));
  }

  private Long getDaysCount(final Absence absence, final QWeekResponse qWeek) {
    var startDate = getDaysCountStartDate(absence, qWeek);
    var endDate = getDaysCountEndDate(absence, qWeek);

    return DAYS.between(startDate, endDate) + 1;
  }

  private LocalDate getDaysCountStartDate(final Absence absence, final QWeekResponse qWeek) {
    final var weekStartDate = qWeek.getStart();
    final var absenceStartDate = absence.getDateStart();
    if (absenceStartDate.isBefore(weekStartDate)) {

      return weekStartDate;
    }
    return absenceStartDate;
  }

  private LocalDate getDaysCountEndDate(final Absence absence, final QWeekResponse qWeek) {
    final var weekEndDate = qWeek.getEnd();
    final var absenceEndDate = absence.getDateEnd();
    if (absenceEndDate == null) {

      return weekEndDate;
    }
    if (absenceEndDate.isAfter(weekEndDate)) {

      return weekEndDate;
    }

    return absenceEndDate;
  }

  private LocalDate getContractLastProlongationStartDate(final ContractResponse activeContract) {
    if (activeContract == null) {
      final var today = qDateTime.getToday();

      return today.minusWeeks(DEFAULT_PERIOD_IN_WEEKS);
    }

    final var contractEndDate = activeContract.getDateEnd();
    final var contractDuration = activeContract.getDuration();

    return contractEndDate.minusWeeks(contractDuration);
  }
}
