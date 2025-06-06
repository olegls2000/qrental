package ee.qrent.billing.bolt.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bolt_orders_count")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoltOrdersCountJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "bolt_id")
  private String boltId;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "month")
  private Integer month;

  @Column(name = "year")
  private Integer year;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "month_orders_count")
  private Integer monthOrdersCount;
}
