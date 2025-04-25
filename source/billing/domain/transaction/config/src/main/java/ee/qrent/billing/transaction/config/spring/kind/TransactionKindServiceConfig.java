package ee.qrent.billing.transaction.config.spring.kind;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindAddPort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindDeletePort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindUpdatePort;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindResponseMapper;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
import ee.qrent.billing.transaction.core.service.kind.TransactionKindQueryService;
import ee.qrent.billing.transaction.core.service.kind.TransactionKindUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindServiceConfig {

  @Bean
  public GetTransactionKindQuery getGetTransactionKindQuery(
      final TransactionKindLoadPort loadPort,
      final TransactionKindResponseMapper mapper,
      final TransactionKindUpdateRequestMapper updateRequestMapper) {
    return new TransactionKindQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public TransactionKindUseCaseService getTransactionKindUseCaseService(
      final TransactionKindAddPort addPort,
      final TransactionKindUpdatePort updatePort,
      final TransactionKindDeletePort deletePort,
      final TransactionKindLoadPort loadPort,
      final TransactionKindAddRequestMapper addRequestMapper,
      final TransactionKindUpdateRequestMapper updateRequestMapper,
      final AddRequestValidator<TransactionKindAddRequest> addRequestValidator,
      final UpdateRequestValidator<TransactionKindUpdateRequest> updateRequestValidator) {
    return new TransactionKindUseCaseService(
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
