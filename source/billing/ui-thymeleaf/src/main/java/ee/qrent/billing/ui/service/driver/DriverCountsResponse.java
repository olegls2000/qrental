package ee.qrent.billing.ui.service.driver;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DriverCountsResponse {
  private Long activeCallSignLinkCount;
  private Long closedCallSignLinkCount;
  private Long activeContractCount;
  private Long closedContractCount;
}
