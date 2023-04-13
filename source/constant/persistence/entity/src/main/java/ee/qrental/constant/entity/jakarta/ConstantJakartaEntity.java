package ee.qrental.constant.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "constant")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConstantJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

   @Column(name = "constant")
  private String constant;

  @Column(name = "value")
  private BigDecimal value;

  @Column(name = "description")
  private String description;

  @Column(name = "negative")
  private Boolean negative;
}
