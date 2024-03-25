package ee.qrental.invoice.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InvoiceResponse {
  private Long id;
  private String number;
  private Integer weekNumber;
  private Integer year;
  private String driverCompany;
  private String driverCompanyRegNumber;
  private String driverCompanyAddress;
  private String qFirmName;
  private String qFirmRegNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmBank;
  private LocalDate created;
  private String comment;
}
