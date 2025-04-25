package ee.qrent.billing.insurance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrent.billing.insurance.api.in.response.InsuranceCalculationResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrent.billing.insurance.core.mapper.InsuranceCalculationResponseMapper;
import java.util.List;

import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationQueryService implements GetInsuranceCalculationQuery {

  private final InsuranceCalculationLoadPort calculationLoadPort;
  private final InsuranceCalculationResponseMapper responseMapper;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;

  @Override
  public List<InsuranceCalculationResponse> getAll() {
    return calculationLoadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public InsuranceCalculationResponse getById(Long id) {
    final var domain = calculationLoadPort.loadById(id);
    return responseMapper.toResponse(domain);
  }

  @Override
  public Long getStartQWeekId() {
    final var latestCalculatedBalanceQWeek = getOneAfterLatestBalanceCalculatedQWeek();
    final var lastInsuranceCalculatedQWeek = getOneAfterLatestInsuranceCalculatedQWeek();

    final var startWeek =
        getLatestQWeek(latestCalculatedBalanceQWeek, lastInsuranceCalculatedQWeek);

    return startWeek.getId();
  }

  @Override
  public Long getLastCalculatedQWeekId() {
    final var latestCalculatedBalanceQWeek = getLatestBalanceCalculatedQWeek();
    final var lastInsuranceCalculatedQWeek = getLatestInsuranceCalculatedQWeek();

    final var result = getLatestQWeek(latestCalculatedBalanceQWeek, lastInsuranceCalculatedQWeek);

    return result.getId();
  }

  private QWeekResponse getLatestInsuranceCalculatedQWeek() {
    final var lastCalculatedQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      return qWeekQuery.getFirstWeek();
    }
    return qWeekQuery.getById(lastCalculatedQWeekId);
  }

  private QWeekResponse getLatestBalanceCalculatedQWeek() {
    final var latestCalculatedBalance = balanceQuery.getLatest();
    if (latestCalculatedBalance == null) {
      return null;
    }
    return qWeekQuery.getById(latestCalculatedBalance.getQWeekId());
  }

  private QWeekResponse getOneAfterLatestInsuranceCalculatedQWeek() {
    final var lastCalculatedQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      return qWeekQuery.getFirstWeek();
    }
    return qWeekQuery.getOneAfterById(lastCalculatedQWeekId);
  }

  private QWeekResponse getOneAfterLatestBalanceCalculatedQWeek() {
    final var latestCalculatedBalance = balanceQuery.getLatest();
    if (latestCalculatedBalance == null) {
      return null;
    }
    return qWeekQuery.getOneAfterById(latestCalculatedBalance.getQWeekId());
  }

  private QWeekResponse getLatestQWeek(final QWeekResponse qWeek1, final QWeekResponse qWeek2) {
    if (qWeek1 == null) {
      return qWeek2;
    }
    if (qWeek2 == null) {
      return qWeek1;
    }

    if (qWeek1.compareTo(qWeek2) >= 0) {
      return qWeek1;
    }
    return qWeek2;
  }
}
