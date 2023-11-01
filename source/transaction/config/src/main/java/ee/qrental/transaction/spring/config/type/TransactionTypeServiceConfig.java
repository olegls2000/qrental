package ee.qrental.transaction.spring.config.type;

import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.out.type.TransactionTypeAddPort;
import ee.qrental.transaction.api.out.type.TransactionTypeDeletePort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeUpdatePort;
import ee.qrental.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import ee.qrental.transaction.core.service.type.TransactionTypeQueryService;
import ee.qrental.transaction.core.service.type.TransactionTypeUseCaseService;
import ee.qrental.transaction.core.validator.TransactionTypeAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionTypeUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeServiceConfig {

  @Bean
  public GetTransactionTypeQuery getGetTransactionTypeQuery(
      final TransactionTypeLoadPort loadPort,
      final TransactionTypeResponseMapper mapper,
      final TransactionTypeUpdateRequestMapper updateRequestMapper) {
    return new TransactionTypeQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public TransactionTypeUseCaseService getTransactionTypeUseCaseService(
      final TransactionTypeAddPort addPort,
      final TransactionTypeUpdatePort updatePort,
      final TransactionTypeDeletePort deletePort,
      final TransactionTypeLoadPort loadPort,
      final TransactionTypeAddRequestMapper addRequestMapper,
      final TransactionTypeUpdateRequestMapper updateRequestMapper,
      final TransactionTypeAddBusinessRuleValidator addBusinessRuleValidator,
      final TransactionTypeUpdateBusinessRuleValidator updateBusinessRuleValidator) {
    return new TransactionTypeUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addBusinessRuleValidator,
        updateBusinessRuleValidator);
  }
}
