package ee.qrental.balance.api.in.query;

import ee.qrental.balance.api.in.response.BalanceResponse;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);
}
