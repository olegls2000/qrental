package ee.qrent.billing.task.persistence.repository.spring;

import ee.qrent.billing.task.persistence.entity.jakarta.TaskRunResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRunResultSpringDataRepository
    extends JpaRepository<TaskRunResultJakartaEntity, Long> {

  List<TaskRunResultJakartaEntity> findAllByTaskName(final String taskName);
}
