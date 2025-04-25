package ee.qrent.billing.ui.service.insurance;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InsuranceCountsResponse {
  private Long activeCasesCount;
  private Long closedCasesCount;
}
