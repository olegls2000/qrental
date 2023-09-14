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
  private String renterRegistrationNumber;
  private String renterCeoName;
  private Long driverIsikukood;
  private String qFirmName;
  private String qFirmCeo;
  private LocalDate created;
}
