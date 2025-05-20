package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.report.adapter.adapter.WeeklyReportPersistenceAdapter;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportAdapterConfig {
  @Bean
  WeeklyReportAdapterMapper getInvoiceAdapterMapper(
      final GetDriverQuery driverQuery,
      final GetCallSignQuery callSignQuery,
      final GetQWeekQuery qWeekQuery,
      final GetCarLinkQuery carLinkQuery,
      final GetCarQuery carQuery,
      final GetFirmQuery firmQuery,
      final GetObligationQuery obligationQuery) {

    return new WeeklyReportAdapterMapper(
        driverQuery, callSignQuery, qWeekQuery, carLinkQuery, carQuery, firmQuery, obligationQuery);
  }

  @Bean
  WeeklyReportPersistenceAdapter getInvoicePersistenceAdapter(
      final WeeklyReportRepository repository, final WeeklyReportAdapterMapper mapper) {

    return new WeeklyReportPersistenceAdapter(repository, mapper);
  }
}
