package ee.qrent.queue.persistence.entity.jakarta;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "q_queue")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QueueEntryJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "occurred_at")
  private LocalDateTime occurredAt;

  @Column(name = "published_at")
  private LocalDateTime publishedAt;

  @Column(name = "processed")
  private Boolean processed;

  @Column(name = "processed_at")
  private LocalDateTime processedAt;

  @Type(JsonBinaryType.class)
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", columnDefinition = "jsonb")
  private NotificationPayloadJson payload;
}
