package ee.qrent.billing.transaction.config.spring.type;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeAddPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeDeletePort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeUpdatePort;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import ee.qrent.billing.transaction.core.service.type.TransactionTypeQueryService;
import ee.qrent.billing.transaction.core.service.type.TransactionTypeUseCaseService;
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
      final AddRequestValidator<TransactionTypeAddRequest> addRequestValidator,
      final UpdateRequestValidator<TransactionTypeUpdateRequest> updateRequestValidator) {

    return new TransactionTypeUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator,
        updateRequestValidator);
  }
}
