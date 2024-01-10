package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.request.WeekObligationAddRequest;
import ee.qrental.bonus.api.in.usecase.WeekObligationAddUseCase;
import ee.qrental.bonus.api.out.WeekObligationAddPort;
import ee.qrental.bonus.api.out.WeekObligationLoadPort;
import ee.qrental.bonus.core.mapper.WeekObligationAddRequestMapper;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeekObligationUseCaseService implements WeekObligationAddUseCase {

  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final WeekObligationAddPort addPort;
  private final WeekObligationLoadPort loadPort;
  private final WeekObligationAddRequestMapper addRequestMapper;

  @Override
  public Long add(final WeekObligationAddRequest request) {
    final var activeCarLinks = carLinkQuery.getActive();
    final var driverIds = activeCarLinks.stream().map(CarLinkResponse::getDriverId).toList();
    driverIds.stream()
        .forEach(
            driverId -> {
              final var rawBalance = balanceQuery.getRawBalanceTotalByDriver(driverId);
            });

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }
}
