package ee.qrental.driver.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Driver {
  private Long id;
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
  private String comment;
  private Long deposit;
}
