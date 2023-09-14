package ee.qrental.contract.domain;

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
  private String renterRegistrationNumber;
  private String renterCeoName;
  private Long renterCeoIsikukood;
  private String renterPhone;
  private String renterEmail;
  private Long driverId;
  private Long driverIsikukood;
  private String driverLicenceNumber;
  private Long qFirmId;
  private String qFirmName;
  private String qFirmRegistrationNumber;
  private String qFirmPostAddress;
  private String qFirmEmail;
  private String qFirmCeo;
  private List<String> qFirmCeoDeputies;
  private LocalDate created;
}
