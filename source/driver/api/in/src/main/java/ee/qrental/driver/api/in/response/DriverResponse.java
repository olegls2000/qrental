package ee.qrental.driver.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class DriverResponse {
  private Long id;
  private Boolean active;
  private String firstName;
  private String lastName;
  private Long isikukood;
  private String phone;
  private String email;
  private String iban1;
  private String iban2;
  private String iban3;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String taxiLicense;
  private String address;
  private String comment;
  private Long deposit;
}
