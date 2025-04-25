package ee.qrent.billing.contract.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import ee.qrent.common.core.enums.AbsenceReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class AbsenceUpdateRequest extends AbstractUpdateRequest {
  private Long id;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private Boolean withCar;
  private AbsenceReason reason;
  private Long driverId;
  private String comment;
}
