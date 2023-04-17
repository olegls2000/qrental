package ee.qrental.driver.api.in.response;

import java.math.BigDecimal;import java.time.LocalDate;
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
  private String address;
  private String companyName;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String companyRegistrationNumber;
  private String companyAddress;
  private String companyVat;
  private String taxiLicense;
  private BigDecimal deposit;
  private String comment;
  private Long qFirmId;
}
