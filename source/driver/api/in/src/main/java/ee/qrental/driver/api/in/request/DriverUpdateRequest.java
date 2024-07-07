package ee.qrental.driver.api.in.request;


import java.math.BigDecimal;
import java.time.LocalDate;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DriverUpdateRequest extends AbstractUpdateRequest {
  private Boolean active;
  private Long callSignId;
  private Long recommendedByDriverId;
  private Integer callSign;
  private String firstName;
  private String lastName;
  private Long taxNumber;
  private String phone;
  private String email;
  private Long qFirmId;
  private String companyName;
  private String companyCeoFirstName;
  private String companyCeoLastName;
  private Long companyCeoTaxNumber;
  private String regNumber;
  private String companyAddress;
  private String companyVat;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp;
  private String taxiLicense;
  private String address;
  private Boolean needInvoicesByEmail;
  private Boolean needFee;
  private Boolean hasRequiredObligation;
  private BigDecimal requiredObligation;
  private Boolean byTelegram;
  private Boolean byWhatsApp;
  private Boolean byViber;
  private Boolean byEmail;
  private Boolean bySms;
  private Boolean byPhone;
  private BigDecimal deposit;
  private String comment;
}
