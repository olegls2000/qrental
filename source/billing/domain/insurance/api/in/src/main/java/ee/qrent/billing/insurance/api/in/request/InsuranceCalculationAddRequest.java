package ee.qrent.billing.insurance.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class InsuranceCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate;
  private Long qWeekId;
  private String comment;
}
