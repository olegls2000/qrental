package ee.qrental.constant.adapter.repository;

import ee.qrental.constant.entity.jakarta.ConstantJakartaEntity;
import java.util.List;

public interface ConstantRepository {
  List<ConstantJakartaEntity> findAll();

  ConstantJakartaEntity save(final ConstantJakartaEntity entity);

  ConstantJakartaEntity getReferenceById(final Long id);
  ConstantJakartaEntity findByName(final String name);

  void deleteById(final Long id);
}
