package ee.qrent.billing.report.adapter.mapper;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportObligationStatus;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportObligationStatusJakarta;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTypeJakarta;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class WeeklyReportAdapterMapper {

  private final GetDriverQuery driverQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetObligationQuery obligationQuery;

  public WeeklyReport mapToDomain(final WeeklyReportJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    final var driverId = entity.getDriverId();
    final var qWeekId = entity.getQWeekId();
    final var qWeek = qWeekQuery.getById(qWeekId);

    return WeeklyReport.builder()
        .id(entity.getId())
        .type(WeeklyReportType.valueOf(entity.getType().name()))
        .qWeekId(qWeekId)
        .driverId(driverId)
        .callSignId(entity.getCallSignId())
        .carId(entity.getCarId())
        .startDate(qWeek.getStart())
        .endDate(qWeek.getEnd())
        .weeksCountTillEnd(entity.getWeeksCountTillEnd())
        .depositObligation(entity.getDepositObligation())
        .depositPaid(entity.getDepositPaid())
        .obligationStatus(getObligationStatus(driverId, qWeekId))
        // TODO ?
        .balanceAmountSunday(entity.getBalanceAmountSunday())
        .balanceAmountAtCalculationMoment(entity.getBalanceAmountAtCalculationMoment())
        .comment(entity.getComment())
        .build();
  }

  private WeeklyReportObligationStatus getObligationStatus(
      final long driverId, final long qWeekId) {
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(driverId, qWeekId);
    if (obligation == null) {

      return WeeklyReportObligationStatus.NOT_COMPLETED;
    }
    if (obligation.getMatchCount() > 0) {

      return WeeklyReportObligationStatus.COMPLETED;
    }
    if (obligation.getMatchCount() == 0) {

      return obligation.getAmount().compareTo(BigDecimal.ZERO) > 0
          ? WeeklyReportObligationStatus.COMPLETED_WITH_DELAY
          : WeeklyReportObligationStatus.NOT_COMPLETED;
    }
    throw new RuntimeException(
        "Obligation match count has unexpected negative value: " + obligation.getMatchCount());
  }

  public WeeklyReportJakartaEntity mapToEntity(final WeeklyReport domain) {

    return WeeklyReportJakartaEntity.builder()
        .id(domain.getId())
        .type(WeeklyReportTypeJakarta.valueOf(domain.getType().name()))
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .callSignId(domain.getCallSignId())
        .carId(domain.getCarId())
        .weeksCountTillEnd(domain.getWeeksCountTillEnd())
        .depositObligation(domain.getDepositObligation())
        .depositPaid(domain.getDepositPaid())
        .obligationStatus(mapToWeeklyReportObligationStatusJakarta(domain.getObligationStatus()))
        .balanceAmountSunday(domain.getBalanceAmountSunday())
        .balanceAmountAtCalculationMoment(domain.getBalanceAmountAtCalculationMoment())
        .comment(domain.getComment())
        .build();
  }

  private WeeklyReportObligationStatusJakarta mapToWeeklyReportObligationStatusJakarta(
      final WeeklyReportObligationStatus status) {
    switch (status) {
      case COMPLETED:
        return WeeklyReportObligationStatusJakarta.COMPLETED;
      case COMPLETED_WITH_DELAY:
        return WeeklyReportObligationStatusJakarta.COMPLETED_WITH_DELAY;
      case NOT_COMPLETED:
        return WeeklyReportObligationStatusJakarta.NOT_COMPLETED;
    }
    throw new RuntimeException("Unknown Weekly Report ObligationStatus: " + status);
  }
}
