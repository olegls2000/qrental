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
  private final GetQWeekQuery qWeekQuery;
  private final GetObligationQuery obligationQuery;

  public WeeklyReport mapToDomain(final WeeklyReportJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    final var driverId = entity.getDriverId();
    final var qWeekId = entity.getQWeekId();
    final var driver = driverQuery.getById(driverId);
    final var qWeek = qWeekQuery.getById(qWeekId);

    return WeeklyReport.builder()
        .id(entity.getId())
        .qWeekId(qWeekId)
        .driverId(driverId)
        .callSignId(entity.getCallSignId())
        .carId(entity.getCarId())
        .qFirmId(entity.getQFirmId())
        .startDate(qWeek.getStart())
        .endDate(qWeek.getEnd())
        .deposit(driver.getDeposit())
        .paidDeposit(BigDecimal.valueOf(999999999999l))
        .status(getObligationStatus(qWeekId, driverId))
        .balanceAmount(BigDecimal.valueOf(999999999999l))
        .obligationTotal(entity.getObligationTotal())
        .obligationRent(entity.getObligationRent())
        .obligationDebt(entity.getObligationDebt())
        .obligationRepairment(entity.getObligationRepairment())
        .obligationRepairmentFranchise(entity.getObligationRepairmentFranchise())
        .obligationOthers(entity.getObligationOthers())
        .obligationFee(entity.getObligationFee())
        .bonusNewDriver(entity.getBonusNewDriver())
        .bonusReliablePartner(entity.getBonusReliablePartner())
        .bonusBolt(entity.getBonusBolt())
        .bonusFriend(entity.getBonusFriend())
        .obligationRentAdjustmentBolt(entity.getObligationRentAdjustmentBolt())
        .obligationRentAdjustmentForus(entity.getObligationRentAdjustmentForus())
        .prepayment(entity.getPrepayment())
        .comment(entity.getComment())
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

  public WeeklyReportJakartaEntity mapToEntity(final WeeklyReport domain) {

    return WeeklyReportJakartaEntity.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .callSignId(domain.getCallSignId())
        .carId(domain.getCarId())
        .qFirmId(domain.getQFirmId())
        .obligationStatus(mapToWeeklyReportObligationStatusJakarta(domain.getStatus()))
        .obligationTotal(domain.getObligationTotal())
        .obligationRent(domain.getObligationRent())
        .obligationDebt(domain.getObligationDebt())
        .obligationRepairment(domain.getObligationRepairment())
        .obligationRepairmentFranchise(domain.getObligationRepairmentFranchise())
        .obligationOthers(domain.getObligationOthers())
        .obligationFee(domain.getObligationFee())
        .bonusNewDriver(domain.getBonusNewDriver())
        .bonusReliablePartner(domain.getBonusReliablePartner())
        .bonusBolt(domain.getBonusBolt())
        .bonusFriend(domain.getBonusFriend())
        .obligationRentAdjustmentBolt(domain.getObligationRentAdjustmentBolt())
        .obligationRentAdjustmentForus(domain.getObligationRentAdjustmentForus())
        .prepayment(domain.getPrepayment())
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
