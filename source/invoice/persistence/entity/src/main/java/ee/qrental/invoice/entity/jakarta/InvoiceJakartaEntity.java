package ee.qrental.invoice.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "invoice")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "number")
  private String number;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "driver_company")
  private String driverCompany;

  @Column(name = "driver_info")
  private String driverInfo;

  @Column(name = "driver_company_reg_number")
  private String driverCompanyRegNumber;

  @Column(name = "driver_company_vat_number")
  private String driverCompanyVatNumber;

  @Column(name = "driver_company_address")
  private String driverCompanyAddress;

  @Column(name = "q_firm_id")
  private Long qFirmId;

  @Column(name = "q_firm_name")
  private String qFirmName;

  @Column(name = "q_firm_reg_number")
  private String qFirmRegNumber;

  @Column(name = "q_firm_vat_number")
  private String qFirmVatNumber;

  @Column(name = "q_firm_iban")
  private String qFirmIban;

  @Column(name = "q_firm_bank")
  private String qFirmBank;

  @Column(name = "q_firm_email")
  private String qFirmEmail;

  @Column(name = "q_firm_post_address")
  private String qFirmPostAddress;

  @Column(name = "q_firm_phone")
  private String qFirmPhone;

  @Column(name = "created")
  private LocalDate created;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "current_week_fee")
  private BigDecimal currentWeekFee;

  @Column(name = "previous_week_balance_fee")
  private BigDecimal previousWeekBalanceFee;

  @Column(name = "previous_week_positive_transactions_sum")
  private BigDecimal previousWeekPositiveTxSum;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "invoice")
  private List<InvoiceItemJakartaEntity> items = new ArrayList<>();
}
