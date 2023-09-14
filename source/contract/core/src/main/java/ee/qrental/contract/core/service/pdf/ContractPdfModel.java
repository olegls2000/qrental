package ee.qrental.contract.core.service.pdf;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ContractPdfModel {
  private String number;
  private String renterName;
  private String renterRegistrationNumber;
  private String renterCeoName;
  private Long renterCeoIsikukood;
  private String renterPhone;
  private String renterEmail;
  private Long driverIsikukood;
  private String driverLicenceNumber;
  private LocalDate created;
}
