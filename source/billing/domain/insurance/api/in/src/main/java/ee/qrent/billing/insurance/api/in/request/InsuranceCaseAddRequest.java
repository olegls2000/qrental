package ee.qrent.billing.insurance.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class InsuranceCaseAddRequest extends AbstractAddRequest {
  private Long driverId;
  private Long carId;
  private LocalDate occurrenceDate;
  private BigDecimal damageAmount;
  private String description;
}
