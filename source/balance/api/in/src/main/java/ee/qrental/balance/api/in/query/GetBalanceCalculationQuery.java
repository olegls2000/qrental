package ee.qrental.balance.api.in.query;

import ee.qrental.balance.api.in.response.BalanceCalculationResponse;

import java.time.LocalDate;
import java.util.List;

public interface GetBalanceCalculationQuery {

  List<BalanceCalculationResponse> getAll();
  BalanceCalculationResponse getOneByActionDate(final LocalDate actionDate);

  BalanceCalculationResponse getById(final Long id);
}
