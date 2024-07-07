package ee.qrental.bonus.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BonusProgramAddRequest extends AbstractAddRequest {
  private String code;
  private String nameEng;
  private String nameRus;
  private String nameEst;
  private String description;
  private Boolean active;
}
