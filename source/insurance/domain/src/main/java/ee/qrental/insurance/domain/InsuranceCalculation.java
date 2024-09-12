package ee.qrental.insurance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class InsuranceCalculation {
  private Long id;
  private Long qWeekId;
  private LocalDate actionDate;
  private List<InsuranceCaseBalance> insuranceCaseBalances;
  private String comment;
}
