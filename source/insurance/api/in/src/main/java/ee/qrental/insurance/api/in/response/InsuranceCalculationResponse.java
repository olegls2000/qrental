package ee.qrental.insurance.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class InsuranceCalculationResponse {
  private Long id;
  private String startWeek;
  private String endWeek;
  private LocalDate actionDate;
  private String comment;
}
