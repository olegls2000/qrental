package ee.qrental.insurance.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InsuranceCaseResponse {
  private Long id;
  private Long driverId;
  private String driverInfo;
  private String carInfo;
  private Long carId;
  private BigDecimal damageAmount;
  private LocalDate occurrenceDate;
  private String occurrenceWeekInfo;
  private Boolean active;
  private String description;
}
