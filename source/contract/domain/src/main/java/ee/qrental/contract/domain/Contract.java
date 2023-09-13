package ee.qrental.contract.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Contract {
  private Long id;
  private String number;
  private String renterName;
  private Integer renterRegistrationNumber;
  private String renterCeoName;
  private String renterCeoIsikukood;
  private String renterPhone;
  private String renterEmail;
  private String driverLicence;
  private Integer driverIsikukood;
  private LocalDate created;
}
