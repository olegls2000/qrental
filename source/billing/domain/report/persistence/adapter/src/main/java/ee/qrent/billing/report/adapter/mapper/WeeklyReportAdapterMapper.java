package ee.qrent.billing.report.adapter.mapper;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportInsuranceCase;
import ee.qrent.billing.report.domain.WeeklyReportObligationStatus;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportInsuranceCaseJakarta;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportObligationStatusJakarta;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTypeJakarta;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
public class WeeklyReportAdapterMapper {

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
        .currentObligationAmount(entity.getCurrentObligationAmount())
        .netAmountOnThursday(entity.getNetAmountOnThursday())
        .feeAmountSunday(entity.getFeeAmountSunday())
        .balanceAmountSunday(entity.getBalanceAmountSunday())
        .balanceAmountAtCalculationMoment(entity.getBalanceAmountAtCalculationMoment())
        .transactionTypesVsAmount(entity.getTransactionTypesVsAmount())
        .insuranceCases(mapToDomains(entity.getInsuranceCases()))
        .comment(entity.getComment())
        .build();
  }

  private List<WeeklyReportInsuranceCaseJakarta> mapToEntities(
      final List<WeeklyReportInsuranceCase> entities) {
    if (entities == null) {

      return emptyList();
    }

    return entities.stream().map(this::mapToEntityInsuranceCase).toList();
  }

  private List<WeeklyReportInsuranceCase> mapToDomains(
      final List<WeeklyReportInsuranceCaseJakarta> domains) {
    if (domains == null) {

      return emptyList();
    }

    return domains.stream().map(this::mapToDomainInsuranceCase).toList();
  }

  private WeeklyReportInsuranceCaseJakarta mapToEntityInsuranceCase(
      final WeeklyReportInsuranceCase domain) {

    return WeeklyReportInsuranceCaseJakarta.builder()
        .carRegNumber(domain.getCarRegNumber())
        .occurrenceDate(domain.getOccurrenceDate())
        .carRegNumber(domain.getCarRegNumber())
        .build();
  }

  private WeeklyReportInsuranceCase mapToDomainInsuranceCase(
      final WeeklyReportInsuranceCaseJakarta entity) {

    return WeeklyReportInsuranceCase.builder()
        .carRegNumber(entity.getCarRegNumber())
        .occurrenceDate(entity.getOccurrenceDate())
        .carRegNumber(entity.getCarRegNumber())
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

    return WeeklyReportObligationStatus.NOT_COMPLETED;
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
        .currentObligationAmount(domain.getCurrentObligationAmount())
        .balanceAmountSunday(domain.getBalanceAmountSunday())
        .feeAmountSunday(domain.getFeeAmountSunday())
        .balanceAmountAtCalculationMoment(domain.getBalanceAmountAtCalculationMoment())
        .netAmountOnThursday(domain.getNetAmountOnThursday())
        .transactionTypesVsAmount(domain.getTransactionTypesVsAmount())
        .insuranceCases(mapToEntities(domain.getInsuranceCases()))
        .comment(domain.getComment())
        .build();
  }

  private WeeklyReportObligationStatusJakarta mapToWeeklyReportObligationStatusJakarta(
      final WeeklyReportObligationStatus status) {
      return switch (status) {
          case COMPLETED -> WeeklyReportObligationStatusJakarta.COMPLETED;
          case NOT_COMPLETED -> WeeklyReportObligationStatusJakarta.NOT_COMPLETED;
      };
  }
}
