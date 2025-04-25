package ee.qrent.billing.bonus.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BonusProgramResponse {
  private Long id;
  private String code;
  private String nameEng;
  private String nameRus;
  private String nameEst;
  private String description;
  private Boolean active;
}
