package ee.qrent.billing.transaction.api.in.query.balance;


import ee.qrent.billing.transaction.api.in.response.balance.BalanceCalculationResponse;
import java.util.List;

public interface GetBalanceCalculationQuery {

  List<BalanceCalculationResponse> getAll();

  BalanceCalculationResponse getById(final Long id);

  Long getLastCalculatedQWeekId();
}