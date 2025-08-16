package ee.qrent.billing.driver.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class DriverResponse {
  private Long id;
  private Boolean active;
  private String firstName;
  private String lastName;
  private String communicationLanguage;
  private LocalDate dateOfBirth;
  private LocalDate dateOfDeath;
  private Long taxNumber;
  private Integer callSign;
  private String phone;
  private String email;
  private String address;
  private String companyName;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String legalEntityType;
  private String legalEntityTypeLabel;
  private String lhvAccount;
  private String companyRegistrationNumber;
  private String companyCeoName;
  private Long companyCeoTaxNumber;
  private String companyAddress;
  private String companyVat;
  private String taxiLicense;
  private Boolean needInvoicesByEmail;
  private Boolean needFee;
  private Boolean needReport;
  private Boolean byTelegram;
  private Boolean byWhatsApp;
  private Boolean byViber;
  private Boolean byEmail;
  private Boolean bySms;
  private Boolean byPhone;
  private BigDecimal deposit;
  private Boolean hasRequiredObligation;
  private BigDecimal requiredObligation;
  private Boolean hasQKasko;
  private Long qFirmId;
  private String qFirmName;
  private String comment;
  private String boltDriverIdentifier;
  private String boltId;
  private LocalDate createdDate;
}
