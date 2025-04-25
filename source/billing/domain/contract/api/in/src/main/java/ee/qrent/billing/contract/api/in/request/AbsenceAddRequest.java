package ee.qrent.billing.contract.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import ee.qrent.common.core.enums.AbsenceReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AbsenceAddRequest extends AbstractAddRequest {
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private Boolean withCar;
  private AbsenceReason reason;
  private String comment;
}
