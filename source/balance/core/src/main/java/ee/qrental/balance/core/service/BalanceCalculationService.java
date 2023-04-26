package ee.qrental.balance.core.service;

import static java.util.stream.Collectors.*;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.api.in.usecase.BalanceCalculationAddUseCase;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.core.mapper.BalanceCalculationAddRequestMapper;
import ee.qrental.balance.core.validator.BalanceCalculationBusinessRuleValidator;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final BalanceCalculationBusinessRuleValidator businessRuleValidator;
  private final BalanceCalculationPeriodService datesCalculationService;
  private final BalanceCalculationAddPort addPort;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;

  @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var domain = addRequestMapper.toDomain(addRequest);
    addPort.add(domain);
  }
}
