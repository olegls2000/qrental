package ee.qrent.billing.car.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class CarResponse {
  private Long id;
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
  private LocalDate dateEndLpg;
  private String insuranceRagStatus;
  private String technicalInspectionRagStatus;
  private String gasInspectionRagStatus;
  private String comment;
  private Boolean brandingQrent;
  private Boolean brandingBolt;
  private Boolean brandingForus;
  private Boolean brandingUber;
  private Boolean brandingTallink;
  private Integer age;
  private String warrantyRagStatus;
  private LocalDate warrantyEndDate;
  private Long warrantyMonths;
}
