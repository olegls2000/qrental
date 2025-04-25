package ee.qrent.billing.contract.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Contract {
  private Long id;
  private String number;
  private ContractDuration contractDuration;
  private String renterName;
  private String renterRegistrationNumber;
  private String renterAddress;
  private String renterCeoName;
  private String renterLhvAccount;
  private Long renterCeoIsikukood;
  private String renterPhone;
  private String renterEmail;
  private Long driverId;
  private Long driverIsikukood;
  private String driverLicenceNumber;
  private String driverAddress;
  private Long qFirmId;
  private String qFirmName;
  private String qFirmRegistrationNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmPostAddress;
  private String qFirmEmail;
  private String qFirmVatPhone;
  private String qFirmCeo;
  private List<String> qFirmCeoDeputies = new ArrayList<>();
  private String carVin;
  private String carManufacturer;
  private String carModel;
  private LocalDate created;
  private LocalDate dateStart;
  private LocalDate dateEnd;
}
