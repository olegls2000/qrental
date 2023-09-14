package ee.qrental.contract.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contract")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContractJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "number")
  private String number;

  @Column(name = "renter_name")
  private String renterName;

  @Column(name = "renter_registration_number")
  private String renterRegistrationNumber;

  @Column(name = "renter_ceo_name")
  private String renterCeoName;

  @Column(name = "renter_ceo_isikukood")
  private Long renterCeoIsikukood;

  @Column(name = "renter_phone")
  private String renterPhone;

  @Column(name = "renter_email")
  private String renterEmail;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "driver_isikukood")
  private Long driverIsikukood;

  @Column(name = "driver_licence_number")
  private String driverLicenceNumber;

  @Column(name = "q_firm_id")
  private Long qFirmId;

  @Column(name = "q_firm_name")
  private String qFirmName;

  @Column(name = "q_firm_registration_number")
  private String qFirmRegistrationNumber;

  @Column(name = "q_firm_post_address")
  private String qFirmPostAddress;

  @Column(name = "q_firm_email")
  private String qFirmEmail;

  @Column(name = "q_firm_ceo")
  private String qFirmCeo;

  @Column(name = "q_firm_ceo_deputy1")
  private String qFirmCeoDeputy1;

  @Column(name = "q_firm_ceo_deputy2")
  private String qFirmCeoDeputy2;

  @Column(name = "q_firm_ceo_deputy3")
  private String qFirmCeoDeputy3;

  @Column(name = "created")
  private LocalDate created;
}