package ee.qrent.notification.email.persistence.repository.spring;

import ee.qrent.notification.email.persistence.entity.jakarta.EmailNotificationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationSpringDataRepository
    extends JpaRepository<EmailNotificationJakartaEntity, Long> {}
