package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverAddRequest extends AbstractAddRequest {
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
  private LocalDate driverLicenseExp = LocalDate.now();
  private String taxiLicense;
  private String address;
  private String comment;
  private BigDecimal deposit;
}
