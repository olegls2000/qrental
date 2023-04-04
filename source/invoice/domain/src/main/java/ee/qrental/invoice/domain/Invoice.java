package ee.qrental.invoice.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Invoice {
  private Long id;

  private String number;
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

  private List<InvoiceItem> items;
}
