package ee.qrent.billing.contract.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
public class ContractResponse {
  private Long id;
  private boolean active;
  private String number;
  private Integer duration;
  private String renterName;
  private String renterRegistrationNumber;
  private String renterAddress;
  private String renterCeoName;
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
  private Long weeksToEnd;
}
