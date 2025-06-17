package ee.qrent.billing.bolt.persistence.repository.spring;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoltStatisticsSpringDataRepository
    extends JpaRepository<BoltStatisticsJakartaEntity, Long> {

  @Modifying
  @Query(
      value =
          "INSERT INTO bolt_statistics(file_name,created_on,region, year, month, data) VALUES "
              + "( :#{#entity.fileName}, :#{#entity.createdOn}, :#{#entity.region}, :#{#entity.year}, :#{#entity.month}, :#{#entity.data})",
      nativeQuery = true)
  Integer saveNatively(final @Param("entity") BoltStatisticsJakartaEntity entity);

  @Query(
      value =
          "SELECT bs.id, bs.file_name, bs.created_on, bs.region, bs.year, bs.month, null as data FROM bolt_statistics bs WHERE id = :id",
      nativeQuery = true)
  BoltStatisticsJakartaEntity findOneWithoutData(@Param("id") Long id);

  BoltStatisticsJakartaEntity findByRegionAndYearAndMonth(
      final String region, final Integer year, final Integer month);

  @Query(
      value =
          "SELECT bs.id, bs.file_name, bs.created_on, bs.region, bs.year, bs.month, null as data FROM bolt_statistics bs",
      nativeQuery = true)
  List<BoltStatisticsJakartaEntity> findAllWithoutData();

  @Query(value = "SELECT data FROM bolt_statistics WHERE id = :id", nativeQuery = true)
  byte[] findDataById(@Param("id") Long id);
}
