package ee.qrental.balance.api.in.query;

import ee.qrental.balance.api.in.response.BalanceCalculationResponse;
import java.util.List;

public interface GetBalanceCalculationQuery {

  List<BalanceCalculationResponse> getAll();

  BalanceCalculationResponse getById(final Long id);
}
