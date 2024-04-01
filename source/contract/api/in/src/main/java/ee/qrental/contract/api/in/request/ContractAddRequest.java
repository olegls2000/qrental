package ee.qrental.contract.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import ee.qrental.common.core.utils.QWeek;
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
