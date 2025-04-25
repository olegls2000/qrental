package ee.qrent.billing.invoice.core.service.pdf;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class InvoicePdfModel {
  private String number;
  private Integer weekNumber;
  private Integer year;
  private String startDate;
  private String endDate;
  private String feeStartDate;
  private String feeEndDate;
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
  private BigDecimal vatPercentage;
  private BigDecimal sum;
  private BigDecimal vatAmount;
  private BigDecimal sumWithVat;
  private String driverCompanyVat;
  private Map<String, BigDecimal> items;
  private BigDecimal debt;
  private BigDecimal advancePayment;
  private BigDecimal total;
  private BigDecimal currentWeekFee;
  private BigDecimal previousWeekBalanceFee;
  private BigDecimal totalWithFee;
  private BigDecimal block2A;
  private BigDecimal block2B;
}
