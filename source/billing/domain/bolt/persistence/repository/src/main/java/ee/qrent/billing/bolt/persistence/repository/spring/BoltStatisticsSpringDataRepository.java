package ee.qrent.billing.bolt.persistence.repository.spring;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;
import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoltStatisticsSpringDataRepository
    extends JpaRepository<BoltStatisticsJakartaEntity, Long> {

  @Modifying
  @Query(
      value =
          "INSERT INTO bolt_statistics(file_name,created_on,region, year, month, data) VALUES "
                  + "( :#{#entity.fileName}, :#{#entity.createdOn}, :#{#entity.region}, :#{#entity.year}, :#{#entity.month}, :#{#entity.data})",
      nativeQuery = true)
  Integer saveNatively(
      final @Param("entity") BoltStatisticsJakartaEntity entity);
}
