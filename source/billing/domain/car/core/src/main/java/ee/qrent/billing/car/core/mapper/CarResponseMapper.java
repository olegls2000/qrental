package ee.qrent.billing.car.core.mapper;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.api.in.response.CarResponse;
import ee.qrent.billing.car.core.service.CarWarrantyService;
import ee.qrent.billing.car.domain.Car;
import ee.qrent.common.in.mapper.ResponseMapper;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class CarResponseMapper implements ResponseMapper<CarResponse, Car> {

  private final QDateTime qDateTime;
  private final CarWarrantyService warrantyService;

  @Override
  public CarResponse toResponse(final Car domain) {
    return CarResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .status(domain.getStatus().getLabel())
        .qRent(domain.getQRent())
        .regNumber(domain.getRegNumber())
        .vin(domain.getVin())
        .releaseDate(domain.getReleaseDate())
        .manufacturer(domain.getManufacturer())
        .model(domain.getModel())
        .appropriation(domain.getAppropriation())
        .elegance(domain.getElegance())
        .gearType(domain.getGearType())
        .fuelType(domain.getFuelType())
        .lpg(domain.getLpg())
        .dateInstallLpg(domain.getDateInstallLpg())
        .insuranceFirm(domain.getInsuranceFirm())
        .insuranceDateStart(domain.getInsuranceDateStart())
        .insuranceDateEnd(domain.getInsuranceDateEnd())
        .sCard(domain.getSCard())
        .key2(domain.getKey2())
        .gps(domain.getGps())
        .technicalInspectionEnd(domain.getTechnicalInspectionEnd())
        .gasInspectionEnd(domain.getGasInspectionEnd())
        .dateEndLpg(domain.getDateEndLpg())
        .insuranceRagStatus(domain.getInsuranceRagStatus().name())
        .technicalInspectionRagStatus(domain.getTechnicalInspectionRagStatus().name())
        .gasInspectionRagStatus(domain.getGasInspectionRagStatus().name())
        .comment(domain.getComment())
        .brandingQrent(domain.getBrandingQrent())
        .brandingBolt(domain.getBrandingBolt())
        .brandingForus(domain.getBrandingForus())
        .brandingUber(domain.getBrandingUber())
        .brandingTallink(domain.getBrandingTallink())
        .age(getCarAge(domain))
        .warrantyRagStatus(warrantyService.getWarrantyRagStatus(domain).name())
        .warrantyEndDate(warrantyService.getWarrantyEndDate(domain))
        .warrantyMonths(warrantyService.getWarrantyMonths(domain))
        .build();
  }

  private Integer getCarAge(final Car domain) {
    final var releaseYear = domain.getReleaseDate().getYear();
    final var todayYear = qDateTime.getToday().getYear();

    return todayYear - releaseYear;
  }

  @Override
  public String toObjectInfo(Car domain) {
    return format("%s %s", domain.getRegNumber(), domain.getManufacturer());
  }
}
