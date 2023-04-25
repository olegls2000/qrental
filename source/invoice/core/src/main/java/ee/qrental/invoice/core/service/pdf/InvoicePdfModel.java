package ee.qrental.invoice.core.service.pdf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InvoicePdfModel {
  private String number;
  private String creationDate;
  private Integer weekNumber;
  private Integer year;
  private String startDate;
  private String endDate;
  private String driverCompany;
  private String driverInfo;
  private String driverCompanyRegNumber;
  private String driverCompanyAddress;
  private String qFirmName;
  private String qFirmEmail;
  private String qFirmPhone;
  private String qFirmPostAddress;
  private String qFirmRegNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmBank;
  private BigDecimal totalAmount;
  private BigDecimal vatPercentage;
  private BigDecimal vatAmount;
  private String driverCompanyVat;
  private BigDecimal summ;
  private Map<String, BigDecimal> items;
}
