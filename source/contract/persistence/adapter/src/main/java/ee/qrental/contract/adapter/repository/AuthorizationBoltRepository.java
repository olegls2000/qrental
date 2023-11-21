package ee.qrental.contract.adapter.repository;

import ee.qrental.contract.entity.jakarta.AuthorizationBoltJakartaEntity;
import java.util.List;

public interface AuthorizationBoltRepository {
  List<AuthorizationBoltJakartaEntity> findAll();

  AuthorizationBoltJakartaEntity save(final AuthorizationBoltJakartaEntity entity);

  AuthorizationBoltJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
