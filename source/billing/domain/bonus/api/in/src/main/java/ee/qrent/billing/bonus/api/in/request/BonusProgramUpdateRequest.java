package ee.qrent.billing.bonus.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BonusProgramUpdateRequest extends AbstractUpdateRequest {
  private Long id;
  private String code;
  private String nameEng;
  private String nameRus;
  private String nameEst;
  private String description;
  private Boolean active;
}
