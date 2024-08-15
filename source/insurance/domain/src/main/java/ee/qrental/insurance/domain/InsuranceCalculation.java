package ee.qrental.insurance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class InsuranceCalculation {
  private Long id;
  private Long startQWeekId;
  private Long endQWeekId;
  private LocalDate actionDate;
  private String comment;
}
