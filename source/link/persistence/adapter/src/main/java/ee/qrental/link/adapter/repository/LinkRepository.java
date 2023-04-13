package ee.qrental.link.adapter.repository;

import ee.qrental.link.entity.jakarta.LinkJakartaEntity;
import java.util.List;

public interface LinkRepository {
  List<LinkJakartaEntity> findAll();

  LinkJakartaEntity save(final LinkJakartaEntity entity);

  LinkJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
