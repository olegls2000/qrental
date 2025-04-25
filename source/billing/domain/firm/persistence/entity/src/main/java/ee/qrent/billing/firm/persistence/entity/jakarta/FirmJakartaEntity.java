package ee.qrent.billing.firm.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
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
  private String name;

  @Column(name = "ceo_first_name")
  private String ceoFirstName;

  @Column(name = "ceo_last_name")
  private String ceoLastName;

  @Column(name = "ceo_deputy1_first_name")
  private String ceoDeputy1FirstName;

  @Column(name = "ceo_deputy1_last_name")
  private String ceoDeputy1LastName;

  @Column(name = "ceo_deputy2_first_name")
  private String ceoDeputy2FirstName;

  @Column(name = "ceo_deputy2_last_name")
  private String ceoDeputy2LastName;

  @Column(name = "ceo_deputy3_first_name")
  private String ceoDeputy3FirstName;

  @Column(name = "ceo_deputy3_last_name")
  private String ceoDeputy3LastName;

  @Column(name = "iban")
  private String iban;

  @Column(name = "reg_number")
  private String registrationNumber;

  @Column(name = "vat_number")
  private String vatNumber;


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

  @Column(name = "comment")
  private String comment;
}
