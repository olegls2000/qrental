package ee.qrental.insurance.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class InsuranceCaseUpdateRequest extends AbstractUpdateRequest {
  private Long driverId;
  private Long carId;
  private LocalDate occurrenceDate;
  private BigDecimal damageAmount;
  private String description;
}
