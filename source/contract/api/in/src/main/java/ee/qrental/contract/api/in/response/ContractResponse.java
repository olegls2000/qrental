package ee.qrental.contract.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class ContractResponse {
  private Long id;
  private String number;
  private String renterName;
  private Integer renterRegistrationNumber;
  private String renterCeoName;
  private String renterCeoIsikukood;
  private String renterPhone;
  private String renterEmail;
  private String driverLicence;
  private Integer driverIsikukood;
  private LocalDate created;
}
