package ee.qrental.contract.core.service.pdf;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AuthorizationPdfModel {
  private Long driverIsikukood;
  private String driverFirstname;
  private String driverLastname;
  private LocalDate created;
}
