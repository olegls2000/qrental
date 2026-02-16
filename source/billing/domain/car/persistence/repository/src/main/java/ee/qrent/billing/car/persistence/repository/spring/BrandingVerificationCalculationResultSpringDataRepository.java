package ee.qrent.billing.car.persistence.repository.spring;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationResultJakartaEntity;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultProjection;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationSummaryProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandingVerificationCalculationResultSpringDataRepository
    extends JpaRepository<BrandingVerificationCalculationResultJakartaEntity, Long> {

  @Query(
      value =
          "select c.id as calculationId, "
              + "c.action_date as actionDate, "
              + "c.comment as comment, "
              + "c.type as type, "
              + "v.id as verificationId, "
              + "v.date as verificationDate, "
              + "v.driver_id as driverId, "
              + "d.first_name as driverFirstName, "
              + "d.last_name as driverLastName, "
              + "v.car_id as carId, "
              + "car.reg_number as carRegNumber, "
              + "v.car_link_id as carLinkId, "
              + "cl.date_start as carLinkStartDate, "
              + "v.branding_expiration_date as brandingExpirationDate "
              + "from branding_verification_calculation_result r "
              + "join branding_verification_calculation c on r.calculation_id = c.id "
              + "join branding_verification v on r.branding_verification_id = v.id "
              + "left join driver d on v.driver_id = d.id "
              + "left join car car on v.car_id = car.id "
              + "left join car_link cl on v.car_link_id = cl.id "
              + "order by c.action_date desc, r.id desc",
      nativeQuery = true)
  List<BrandingVerificationCalculationResultProjection> findAllResults();

  @Query(
      value =
          "select c.id as calculationId, "
              + "c.action_date as actionDate, "
              + "c.type as type, "
              + "count(r.id) as verificationsCount "
              + "from branding_verification_calculation_result r "
              + "join branding_verification_calculation c on r.calculation_id = c.id "
              + "group by c.id, c.action_date, c.type "
              + "order by c.action_date desc, c.id desc",
      nativeQuery = true)
  List<BrandingVerificationCalculationSummaryProjection> findAllCalculations();

  @Query(
      value =
          "select c.id as calculationId, "
              + "c.action_date as actionDate, "
              + "c.comment as comment, "
              + "c.type as type, "
              + "v.id as verificationId, "
              + "v.date as verificationDate, "
              + "v.driver_id as driverId, "
              + "d.first_name as driverFirstName, "
              + "d.last_name as driverLastName, "
              + "v.car_id as carId, "
              + "car.reg_number as carRegNumber, "
              + "v.car_link_id as carLinkId, "
              + "cl.date_start as carLinkStartDate, "
              + "v.branding_expiration_date as brandingExpirationDate "
              + "from branding_verification_calculation_result r "
              + "join branding_verification_calculation c on r.calculation_id = c.id "
              + "join branding_verification v on r.branding_verification_id = v.id "
              + "left join driver d on v.driver_id = d.id "
              + "left join car car on v.car_id = car.id "
              + "left join car_link cl on v.car_link_id = cl.id "
              + "where c.id = :calculationId "
              + "order by r.id desc",
      nativeQuery = true)
  List<BrandingVerificationCalculationResultProjection> findResultsByCalculationId(
      @Param("calculationId") final Long calculationId);
}
