package ee.qrental.contract.adapter.repository;

import ee.qrental.contract.entity.jakarta.AuthorizationJakartaEntity;
import java.util.List;

public interface AuthorizationRepository {
  List<AuthorizationJakartaEntity> findAll();

  AuthorizationJakartaEntity save(final AuthorizationJakartaEntity entity);

  AuthorizationJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
