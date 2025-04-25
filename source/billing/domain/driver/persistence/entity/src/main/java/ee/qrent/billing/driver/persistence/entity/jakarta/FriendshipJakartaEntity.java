package ee.qrent.billing.driver.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "friendship")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FriendshipJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private DriverJakartaEntity driver;

  @ManyToOne
  @JoinColumn(name = "friend_id")
  private DriverJakartaEntity friend;

  @Column(name = "date_start")
  private LocalDate dateStart;
}
