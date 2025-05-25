package ee.qrent.billing.report.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationAddRequestMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportCalculationResult;
import ee.qrent.common.in.time.QDateTime;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseService implements WeeklyReportCalculationAddUseCase {

  private final WeeklyReportCalculationAddRequestValidator addRequestValidator;
  private final WeeklyReportCalculationAddRequestMapper addRequestMapper;
  private final WeeklyReportCalculationAddPort addPort;
  private final QDateTime qDateTime;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;

  @Transactional
  @Override
  public Long add(final WeeklyReportCalculationAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = request.getQWeekId();
    final var domain = addRequestMapper.toDomain(request);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);

    driverQuery.getAll().stream()
        .forEach(
            driver -> {
              final var weeklyReport = getWeeklyReport(driver, requestedQWeek);
              final var result = getWeeklyReportCalculationResult();
              domain.getResults().add(result);
            });

    final var addedDomain = addPort.add(domain);
    sendEmailNotification(addedDomain);

    return addedDomain.getId();
  }

  private WeeklyReport getWeeklyReport(
      final DriverResponse driver, final QWeekResponse requestedQWeek) {
    return WeeklyReport.builder().build();
  }

  private WeeklyReportCalculationResult getWeeklyReportCalculationResult() {
    return WeeklyReportCalculationResult.builder().build();
  }

  private void sendEmailNotification(WeeklyReportCalculation calculation) {
    // TODO add mail logic here
  }
}
