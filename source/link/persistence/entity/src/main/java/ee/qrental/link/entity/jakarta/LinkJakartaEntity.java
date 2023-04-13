package ee.qrental.link.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "link")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LinkJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

   @Column(name = "car_id")
  private Long carId;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "date_start")
  private LocalDate dateStart;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "comment")
  private String comment;

}
