package ee.qrental.bonus.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "bonus_program")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class BonusProgramJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name_eng")
  private String nameEng;

  @Column(name = "name_rus")
  private String nameRus;

  @Column(name = "name_est")
  private String nameEst;

  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private Boolean active;
}
