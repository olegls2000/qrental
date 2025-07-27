package ee.qrent.notification.task.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task_run_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRunResultJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "task_name")
  private String taskName;

  @Column(name = "status")
  private String status;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "duration_in_millis")
  private Long durationInMillis;
}
