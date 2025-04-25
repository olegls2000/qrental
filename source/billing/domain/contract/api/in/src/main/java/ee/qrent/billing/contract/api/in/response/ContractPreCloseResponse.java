package ee.qrent.billing.contract.api.in.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ContractPreCloseResponse {
  private List<InsuranceCaseForContractCloseResponse> insuranceCasesToClose = new ArrayList<>();
  private String driverInfo;
  private String contractDuration;
}
