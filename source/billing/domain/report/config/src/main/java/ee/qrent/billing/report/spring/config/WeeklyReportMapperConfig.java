package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportCalculationAdapterMapper;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.mapper.*;
import ee.qrent.common.in.time.QDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportMapperConfig {
  @Bean
  WeeklyReportCalculationAddRequestMapper getWeeklyReportCalculationAddRequestMapper(
      final QDateTime qDateTime) {

    return new WeeklyReportCalculationAddRequestMapper(qDateTime);
  }

  @Bean
  WeeklyReportResponseMapper getWeeklyReportResponseMapper(
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery,
      final GetCarLinkQuery carLinkQuery) {

    return new WeeklyReportResponseMapper(qWeekQuery, driverQuery, carLinkQuery);
  }

  @Bean
  WeeklyReportCalculationResponseMapper getWeeklyReportCalculationResponseMapper(
      final GetQWeekQuery qWeekQuery, final WeeklyReportLoadPort weeklyReportLoadPort) {

    return new WeeklyReportCalculationResponseMapper(qWeekQuery, weeklyReportLoadPort);
  }

  @Bean
  WeeklyReportAdapterMapper getWeeklyReportAdapterMapper(
      final GetQWeekQuery qWeekQuery, final GetObligationQuery obligationQuery) {

    return new WeeklyReportAdapterMapper(qWeekQuery, obligationQuery);
  }

  @Bean
  WeeklyReportCalculationAdapterMapper getWeeklyReportCalculationAdapterMapper() {

    return new WeeklyReportCalculationAdapterMapper();
  }
}
