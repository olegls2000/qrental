package ee.qrent.notification.email.persistence.repository;

import ee.qrent.notification.email.persistence.entity.jakarta.EmailNotificationJakartaEntity;

public interface EmailNotificationRepository {

  EmailNotificationJakartaEntity save(final EmailNotificationJakartaEntity entity);

  void deleteById(final Long id);
}
