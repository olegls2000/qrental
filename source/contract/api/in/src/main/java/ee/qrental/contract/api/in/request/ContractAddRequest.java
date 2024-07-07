package ee.qrental.contract.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContractAddRequest extends AbstractAddRequest {
  private Long driverId;
  private Long qFirmId;
  private String contractDuration;
}
