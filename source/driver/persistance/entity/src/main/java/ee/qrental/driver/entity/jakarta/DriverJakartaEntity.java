package ee.qrental.driver.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "driver")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DriverJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "isikukood")
  private Long isikukood;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "company")
  private String company;

  @Column(name = "reg_number")
  private String regNumber;

  @Column(name = "company_address")
  private String companyAddress;

  @Column(name = "driver_license_number")
  private String driverLicenseNumber;

  @Column(name = "driver_license_exp")
  private LocalDate driverLicenseExp;

  @Column(name = "taxi_license")
  private String taxiLicense;

  @Column(name = "address")
  private String address;

  @Column(name = "comment")
  private String comment;

  @Column(name = "deposit")
  private Long deposit;
}
