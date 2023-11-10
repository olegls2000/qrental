package ee.qrental.transaction.api.in.query.balance;


import ee.qrental.transaction.api.in.response.balance.BalanceCalculationResponse;
import java.util.List;

public interface GetBalanceCalculationQuery {

  List<BalanceCalculationResponse> getAll();

  BalanceCalculationResponse getById(final Long id);

  Long getLastCalculatedQWeekId();
}