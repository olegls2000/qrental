package ee.qrent.billing.report.adapter.mapper;

import static java.lang.String.format;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportDetail;
import ee.qrent.billing.report.domain.WeeklyReportObligationStatus;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportObligationStatusJakarta;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class WeeklyReportAdapterMapper {

  private final GetDriverQuery driverQuery;
  private final GetCallSignQuery callSignQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final GetCarQuery carQuery;
  private final GetFirmQuery firmQuery;
  private final GetObligationQuery obligationQuery;

  public WeeklyReport mapToDomain(final WeeklyReportJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    final var driverId = entity.getDriverId();
    final var qWeekId = entity.getQWeekId();
    final var qFirmId = entity.getQFirmId();
    final var driver = driverQuery.getById(driverId);
    final var carLink = carLinkQuery.getActiveByDriverIdAndQWeekId(qWeekId, driverId);
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var qFirm = firmQuery.getById(qFirmId);

    return WeeklyReport.builder()
        .id(entity.getId())
        .driverId(driverId)
        // TODO add callSign Id
        .callSignId(null)
        // TODO add car Id
        .carId(null)
        .qWeekId(qWeekId)
        .qFirmId(entity.getQFirmId())
        .deposit(driver.getDeposit())
        .paidDeposit(BigDecimal.valueOf(999999999999l))
        .status(getObligationStatus(qWeekId, driverId))
        .detail(WeeklyReportDetail.builder().build())
        .build();
  }

  private WeeklyReportObligationStatus getObligationStatus(
      final long qWeekId, final long driverId) {
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(qWeekId, driverId);
    if (obligation == null) {
      throw new RuntimeException(
          format(
              "Obligation for Driver.id: %d eportObligationStatus getObligationStatus(\n"
                  + "      final long qWeekId, final long driverId) {\n"
                  + "    final var obligation = obligationQuery.getByQWeekIdAndDriverId(qWeekId, driverId);\n"
                  + "    if (obligation == null) {and qWeek.id: %d was not found",
              driverId, qWeekId));
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

  public WeeklyReportDetail mapToDetailDomain(final WeeklyReportJakartaEntity entity) {
    // TODO report
    return WeeklyReportDetail.builder().build();
  }

  public WeeklyReportJakartaEntity mapToEntity(final WeeklyReport domain) {
    final var callSign = callSignQuery.getById(domain.getCallSignId());

    return WeeklyReportJakartaEntity.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .callSignId(domain.getCallSignId())
        .carId(domain.getCarId())
        .qFirmId(domain.getQFirmId())
        .obligationStatus(mapToWeeklyReportObligationStatusJakarta(domain.getStatus()))
        .obligationTotal(domain.getDetail().getObligationTotal())
        .obligationRent(domain.getDetail().getObligationRent())
        .obligationDebt(domain.getDetail().getObligationDebt())
        .obligationRepairment(domain.getDetail().getObligationRepairment())
        .obligationRepairmentFranchise(domain.getDetail().getObligationRepairmentFranchise())
        .obligationOthers(domain.getDetail().getObligationOthers())
        .obligationFee(domain.getDetail().getObligationFee())
        .bonusNewDriver(domain.getDetail().getBonusNewDriver())
        .bonusReliablePartner(domain.getDetail().getBonusReliablePartner())
        .bonusBolt(domain.getDetail().getBonusBolt())
        .bonusFriend(domain.getDetail().getBonusFriend())
        .obligationRentAdjustmentBolt(domain.getDetail().getObligationRentAdjustmentBolt())
        .obligationRentAdjustmentForus(domain.getDetail().getObligationRentAdjustmentForus())
        .prepayment(domain.getDetail().getPrepayment())
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
