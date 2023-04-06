package ee.qrental.firm.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "firm")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FirmJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

   @Column(name = "firm_name")
  private String firmName;

  @Column(name = "iban")
  private String iban;

  @Column(name = "reg_number")
  private String regNumber;

  @Column(name = "vat_number")
  private String vatNumber;

  @Column(name = "comment")
  private String comment;

  @Column(name = "email")
  private String email;

  @Column(name = "post_address")
  private String postAddress;

  @Column(name = "phone")
  private String phone;

  @Column(name = "bank")
  private String bank;

  @Column(name = "q_group")
  private Boolean qGroup;
}
