package ee.qrental.driver.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverAddRequest extends AbstractAddRequest {
  private Boolean active;
  private Long callSignId;
  private Long recommendedByDriverId;
  private String firstName;
  private String lastName;
  private Long taxNumber;
  private String phone;
  private String email;
  private String companyName;
  private String companyCeoFirstName;
  private String companyCeoLastName;
  private Long companyCeoTaxNumber;
  private String regNumber;
  private String companyAddress;
  private String companyVat;
  private String driverLicenseNumber;
  private LocalDate driverLicenseExp = LocalDate.now();
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
  private Long qFirmId;
  private String comment;
}
