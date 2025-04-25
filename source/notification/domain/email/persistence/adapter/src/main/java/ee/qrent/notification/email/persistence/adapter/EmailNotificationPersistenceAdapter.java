package ee.qrent.notification.email.persistence.adapter;

import ee.qrent.notification.email.api.out.EmailNotificationAddPort;
import ee.qrent.notification.email.api.out.EmailNotificationDeletePort;
import ee.qrent.notification.email.domain.EmailNotification;
import ee.qrent.notification.email.persistence.mapper.EmailNotificationAdapterMapper;
import ee.qrent.notification.email.persistence.repository.EmailNotificationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailNotificationPersistenceAdapter
    implements EmailNotificationAddPort, EmailNotificationDeletePort {

  private final EmailNotificationRepository repository;
  private final EmailNotificationAdapterMapper mapper;

  @Override
  public EmailNotification add(final EmailNotification domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
