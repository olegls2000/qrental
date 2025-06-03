package ee.qrent.billing.bolt.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "bolt_statistics")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoltStatisticsJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "created_on")
  private LocalDate createdOn;

  @Column(name = "region")
  private String region;

  @Lob private byte[] fileData;

  @Column(name = "comment")
  private String comment;
}
