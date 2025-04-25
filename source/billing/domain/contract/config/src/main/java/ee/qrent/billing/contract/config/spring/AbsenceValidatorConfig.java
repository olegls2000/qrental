package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.core.validator.AbsenceUpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceValidatorConfig {

  @Bean
  AbsenceUpdateRequestValidator getAbsenceUpdateRequestValidator(
      final GetBalanceQuery balanceQuery,
      final GetQWeekQuery qWeekQuery,
      final AbsenceLoadPort loadPort) {
    return new AbsenceUpdateRequestValidator(balanceQuery, qWeekQuery, loadPort);
  }
}
