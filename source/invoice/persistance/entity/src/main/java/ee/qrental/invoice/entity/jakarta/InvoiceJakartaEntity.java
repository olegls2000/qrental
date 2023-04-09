package ee.qrental.invoice.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.List;

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

  @Column(name = "week_number")
  private Integer weekNumber;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "driver_call_sign")
  private Integer driverCallSign;

  @Column(name = "driver_company")
  private String driverCompany;

  @Column(name = "driver_company_reg_number")
  private String driverCompanyRegNumber;

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

  @Column(name = "created")
  private LocalDate created;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "invoice")
  private List<InvoiceItemJakartaEntity> items;
}
