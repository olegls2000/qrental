package ee.qrental.ui.controller.transaction.assembler;

import static java.util.stream.Collectors.toList;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.ui.controller.transaction.model.DriversBalanceModel;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverBalanceAssembler {

  private final GetBalanceQuery balanceQuery;
  private final GetDriverQuery driverQuery;

  public List<DriversBalanceModel> getDriversBalanceModels() {
    return driverQuery.getAll().stream().map(this::assembleModel).collect(toList());
  }

  private DriversBalanceModel assembleModel(final DriverResponse driver) {
    final var driverId = driver.getId();
    final var rawTotal = balanceQuery.getRawBalanceTotalByDriver(driverId);
    final var fee = balanceQuery.getFeeByDriver(driverId);

    return DriversBalanceModel.builder()
        .driverId(driverId)
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .phone(driver.getPhone())
        .callSign(driver.getCallSign())
        .rawTotal(rawTotal)
        .fee(fee)
        .build();
  }
}
