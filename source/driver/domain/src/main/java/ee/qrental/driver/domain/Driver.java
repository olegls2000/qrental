package ee.qrental.driver.domain;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Driver {
  private Long id;
  private Boolean active;
  private String firstName;
  private String lastName;
  private Long taxNumber;
  private String phone;
  private String email;
  private String address;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String companyName;
  private String companyCeoFirstName;
  private String companyCeoLastName;
  private Long companyCeoTaxNumber;
  private String companyRegistrationNumber;
  private String companyAddress;
  private String companyVat;
  private String taxiLicense;
  private Boolean needInvoicesByEmail;
  private Boolean needFee;
  private Boolean byTelegram;
  private Boolean byWhatsApp;
  private Boolean byViber;
  private Boolean byEmail;
  private Boolean bySms;
  private Boolean byPhone;
  private BigDecimal deposit;
  private BigDecimal requiredObligation;
  private Long qFirmId;
  private String comment;
  private LocalDate createdDate;
  private CallSign callSign;
  private Friendship friendship;

  public boolean hasRequiredObligation() {
    return requiredObligation.compareTo(ZERO) > 0;
  }

  public boolean withCallSign() {
    return callSign != null;
  }

  public Integer getCallSignValue() {
    return callSign == null ? null : callSign.getCallSign();
  }

  public Long getCallSignId() {
    return callSign == null ? null : callSign.getId();
  }

  public BigDecimal getDeposit() {
    return deposit == null ? ZERO : deposit;
  }
}
