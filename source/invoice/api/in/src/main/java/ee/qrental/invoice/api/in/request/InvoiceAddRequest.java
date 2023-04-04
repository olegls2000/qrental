package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceAddRequest extends AbstractAddRequest {

  private Long driverId;
  private Integer driverCallSign;
  private String driverCompany;
  private String driverCompanyRegNumber;
  private String driverCompanyAddress;
  private Long qFirmId;
  private String qFirmName;
  private String qFirmRegNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmBank;
  private LocalDate created;
  private String comment;
  private LocalDate startDate;
  private LocalDate endDate;
}
