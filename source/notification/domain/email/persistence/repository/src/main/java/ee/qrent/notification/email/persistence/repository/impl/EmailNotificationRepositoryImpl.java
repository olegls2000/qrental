package ee.qrent.notification.email.persistence.repository.impl;

import ee.qrent.notification.email.persistence.entity.jakarta.EmailNotificationJakartaEntity;
import ee.qrent.notification.email.persistence.repository.EmailNotificationRepository;
import ee.qrent.notification.email.persistence.repository.spring.EmailNotificationSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailNotificationRepositoryImpl implements EmailNotificationRepository {

  private final EmailNotificationSpringDataRepository springDataRepository;

  @Override
  public EmailNotificationJakartaEntity save(final EmailNotificationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
