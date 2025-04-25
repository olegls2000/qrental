package ee.qrent.billing.ui.service.driver.impl;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.ui.service.driver.DriverCounterService;
import ee.qrent.billing.ui.service.driver.DriverCountsResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverCounterServiceImpl implements DriverCounterService {
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetContractQuery contractQuery;

  @Override
  public DriverCountsResponse getDriverCounts() {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    final var activeContractCount = contractQuery.getCountActive();
    final var closedContractCount = contractQuery.getCountClosed();

    return DriverCountsResponse.builder()
        .activeContractCount(activeContractCount)
        .closedContractCount(closedContractCount)
        .activeCallSignLinkCount(activeLinksCount)
        .closedCallSignLinkCount(closedLinksCount)
        .build();
  }
}
