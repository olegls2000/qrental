package ee.qrent.billing.deposit.core.mapper;

import ee.qrent.billing.deposit.api.in.response.DepositResponse;
import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.mapper.ResponseMapper;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class DepositResponseMapper implements ResponseMapper<DepositResponse, Deposit> {

  private final GetDriverQuery driverQuery;

  @Override
  public DepositResponse toResponse(final Deposit domain) {
    final var driver = driverQuery.getById(domain.getDriverId());

    return DepositResponse.builder()
        .id(domain.getId())
        .amount(domain.getAmount())
        .driverFirstName(driver.getFirstName())
        .driverLastName(driver.getLastName())
        .driverTaxNumber(driver.getTaxNumber())
        .createdOn(domain.getCreatedOn())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final Deposit domain) {
    final var driver = driverQuery.getById(domain.getDriverId());
    final var driverInfo = driver.getFirstName() + " " + driver.getLastName();
    final var driverTaxNumber = driver.getTaxNumber();

    return format("%s, %s %d", driverInfo, driverTaxNumber, domain.getAmount());
  }
}
