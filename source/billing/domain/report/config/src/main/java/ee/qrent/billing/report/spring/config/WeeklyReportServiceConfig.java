package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.mapper.*;
import ee.qrent.billing.report.core.service.*;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfConverter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfModelMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.common.in.time.QDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class WeeklyReportServiceConfig {

  @Bean
  WeeklyReportCalculationQueryService getWeeklyReportCalculationQueryService(
      final WeeklyReportCalculationLoadPort loadPort,
      final WeeklyReportCalculationResponseMapper mapper) {

    return new WeeklyReportCalculationQueryService(loadPort, mapper);
  }

  @Bean
  GetWeeklyReportQuery getWeeklyReportQueryService(
      final WeeklyReportLoadPort loadPort, final WeeklyReportResponseMapper mapper) {

    return new WeeklyReportQueryService(loadPort, mapper);
  }

  @Bean
  WeeklyReportCalculationUseCaseService getWeeklyReportCalculationUseCaseService(
      final WeeklyReportCalculationAddRequestValidator addRequestValidator,
      final WeeklyReportCalculationAddRequestMapper addRequestMapper,
      final WeeklyReportCalculationAddPort addPort,
      final QDateTime qDateTime,
      final GetObligationQuery obligationQuery,
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery) {

    return new WeeklyReportCalculationUseCaseService(
        addRequestValidator,
        addRequestMapper,
        addPort,
        qDateTime,
        obligationQuery,
        qWeekQuery,
        driverQuery);
  }

  @Bean
  WeeklyReportToPdfConverter getWeeklyReportToPdfConverter() {

    return new WeeklyReportToPdfConverter();
  }

  @Bean
  WeeklyReportToPdfModelMapper getWeeklyReportToPdfModelMapper(
      final WeeklyReportLoadPort loadPort, final GetQWeekQuery qWeekQuery) {
    return new WeeklyReportToPdfModelMapper(loadPort, qWeekQuery);
  }

  @Bean
  WeeklyReportPdfUseCase getInvoicePdfUseCase(
      final WeeklyReportLoadPort loadPort,
      final WeeklyReportToPdfModelMapper mapper,
      final WeeklyReportToPdfConverter converter) {

    return new WeeklyReportPdfUseCaseImpl(loadPort, mapper, converter);
  }
}
