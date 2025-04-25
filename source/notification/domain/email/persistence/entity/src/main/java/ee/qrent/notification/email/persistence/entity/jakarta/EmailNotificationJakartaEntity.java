package ee.qrent.notification.email.persistence.entity.jakarta;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;


import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.JSON;

@Entity
@Table(name = "q_email_notification")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailNotificationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "type")
  private String type;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  @Type(JsonBinaryType.class)
  @JdbcTypeCode(JSON)
  @Column(name = "payload", columnDefinition = "jsonb")
  private EmailNotificationPayloadJson payload;
}
