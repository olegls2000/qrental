package ee.qrental.invoice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Invoice {
  private Long id;
  private String number;
  private Integer weekNumber;
  private Long driverId;
  private String driverCompany;
  private String driverInfo;
  private String driverCompanyRegNumber;
  private String driverCompanyAddress;
  private String driverCompanyVat;
  private Long qFirmId;
  private String qFirmName;
  private String qFirmRegNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmBank;
  private String qFirmEmail;
  private String qFirmPostAddress;
  private String qFirmPhone;
  private LocalDate created;
  private BigDecimal balance;
  private BigDecimal currentWeekFee;
  private BigDecimal previousWeekBalanceFee;
  private String comment;
  private List<InvoiceItem> items;

  public Boolean withVat() {
    return qFirmVatNumber != null && !qFirmVatNumber.isBlank();
  }
}
