package ee.qrental.transaction.core.mapper.balance;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.domain.balance.Balance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceResponseMapper implements ResponseMapper<BalanceResponse, Balance> {
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;

  @Override
  public BalanceResponse toResponse(final Balance domain) {
    if (domain == null) {
      return null;
    }
    final var qWeekId = domain.getQWeekId();
    final var qWeek = qWeekQuery.getById(qWeekId);
    return BalanceResponse.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .year(qWeek.getYear())
        .weekNumber(qWeek.getNumber())
        .created(domain.getCreated())
        .feeAbleAmount(domain.getFeeAbleAmount().negate())
        .nonFeeAbleAmount(domain.getNonFeeAbleAmount().negate())
        .positiveAmount(domain.getPositiveAmount())
        .feeAmount(domain.getFeeAmount().negate())
        .amount(domain.getAmountsSumWithoutRepairment())
        .driverInfo(driverQuery.getObjectInfo(domain.getDriverId()))
        .build();
  }

  @Override
  public String toObjectInfo(final Balance domain) {
    final var qWeek = qWeekQuery.getById(domain.getQWeekId());
    final var driver = driverQuery.getById(domain.getDriverId());

    return format(
        "Balance for driver: %s %s, Year: %s, Week: %d",
        driver.getFirstName(), driver.getLastName(), qWeek.getYear(), qWeek.getNumber());
  }
}
