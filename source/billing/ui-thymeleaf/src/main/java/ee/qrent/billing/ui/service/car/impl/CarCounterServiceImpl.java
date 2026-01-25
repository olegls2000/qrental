package ee.qrent.billing.ui.service.car.impl;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.ui.service.car.CarCounterService;
import ee.qrent.billing.ui.service.car.CarCountsResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarCounterServiceImpl implements CarCounterService {
  private final GetCarLinkQuery carLinkQuery;
  private final GetContractQuery contractQuery;

  @Override
  public CarCountsResponse getCarCounts() {

   /* final var activeLinksCount = callSignLinkQuery.getCountActive();
    final var closedLinksCount = callSignLinkQuery.getCountClosed();*/
    final var activeContractCount = contractQuery.getCountActive();
    final var closedContractCount = contractQuery.getCountClosed();

    return CarCountsResponse.builder()          //NoInsuranceCars , FreeCars
        .activeContractCount(activeContractCount)
        .closedContractCount(closedContractCount)
       /* .activeCallSignLinkCount(activeLinksCount)
        .closedCallSignLinkCount(closedLinksCount)*/
        .build();
  }
}
