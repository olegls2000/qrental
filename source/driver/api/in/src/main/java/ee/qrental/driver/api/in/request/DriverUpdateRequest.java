package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DriverUpdateRequest extends AbstractUpdateRequest {
  private Boolean active;
  private String firstName;
  private String lastName;
  private Long isikukood;
  private String phone;
  private String email;
  private String company;
  private String regNumber;
  private String companyAddress;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String taxiLicense;
  private String address;
  private BigDecimal deposit;
  private String comment;
}
