package ee.qrent.billing.insurance.api.in.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class InsuranceCaseContextResponse {
  private Map<InsuranceCaseResponse, InsuranceCaseBalanceEntry> caseVsLatestBalance;
}
