package ee.qrent.billing.car.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "car")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "status")
  private String status;

  @Column(name = "q_rent")
  private Boolean qRent;

  @Column(name = "reg_number")
  private String regNumber;

  @Column(name = "vin")
  private String vin;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "model")
  private String model;

  @Column(name = "appropriation")
  private Boolean appropriation;

  @Column(name = "elegance")
  private Boolean elegance;

  @Column(name = "gear_type")
  private String gearType;

  @Column(name = "fuel_type")
  private String fuelType;

  @Column(name = "lpg")
  private Boolean lpg;

  @Column(name = "date_install_lpg")
  private LocalDate dateInstallLpg;

  @Column(name = "insurance_firm")
  private String insuranceFirm;

  @Column(name = "insurance_date_start")
  private LocalDate insuranceDateStart;

  @Column(name = "insurance_date_end")
  private LocalDate insuranceDateEnd;

  @Column(name = "s_card")
  private Boolean sCard;

  @Column(name = "key2")
  private Boolean key2;

  @Column(name = "gps")
  private Boolean gps;

  @Column(name = "technical_inspection_end")
  private LocalDate technicalInspectionEnd;

  @Column(name = "gas_inspection_end")
  private LocalDate gasInspectionEnd;

  @Column(name = "comment")
  private String comment;

  @Column(name = "date_end_lpg")
  private LocalDate dateEndLpg;

  @Column(name = "by_qrent")
  private Boolean brandingQrent;

  @Column(name = "by_bolt")
  private Boolean brandingBolt;

  @Column(name = "by_forus")
  private Boolean brandingForus;

  @Column(name = "by_uber")
  private Boolean brandingUber;

  @Column(name = "by_tallink")
  private Boolean brandingTallink;
}
