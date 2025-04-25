package ee.qrent.billing.car.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CarUpdateRequest extends AbstractUpdateRequest {
  private Boolean active;
  private String status;
  private Boolean qRent;
  private String regNumber;
  private String vin;
  private LocalDate releaseDate;
  private String manufacturer;
  private String model;
  private Boolean appropriation;
  private Boolean elegance;
  private String gearType;
  private String fuelType;
  private Boolean lpg;
  private LocalDate dateInstallLpg;
  private String insuranceFirm;
  private LocalDate insuranceDateStart;
  private LocalDate insuranceDateEnd;
  private Boolean sCard;
  private Boolean key2;
  private Boolean gps;
  private LocalDate technicalInspectionEnd;
  private LocalDate gasInspectionEnd;
  private String comment;
  private LocalDate dateEndLpg;
  private Boolean brandingQrent;
  private Boolean brandingBolt;
  private Boolean brandingForus;
  private Boolean brandingUber;
  private Boolean brandingTallink;
}
