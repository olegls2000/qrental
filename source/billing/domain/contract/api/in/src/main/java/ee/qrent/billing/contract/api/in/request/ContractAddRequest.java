package ee.qrent.billing.contract.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ContractAddRequest extends AbstractAddRequest {
  private LocalDate dateStart;
  private Long driverId;
  private Long qFirmId;
  private String contractDuration;
}
