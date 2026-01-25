package ee.qrent.billing.ui.service.car;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarCountsResponse {
  private Long activeCallSignLinkCount;
  private Long closedCallSignLinkCount;
  private Long activeContractCount;
  private Long closedContractCount;
}
