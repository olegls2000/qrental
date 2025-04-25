package ee.qrent.billing.contract.domain;

import ee.qrent.common.core.enums.AbsenceReason;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class Absence {
  private Long id;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private Boolean withCar;
  private AbsenceReason reason;
  private String comment;
}
