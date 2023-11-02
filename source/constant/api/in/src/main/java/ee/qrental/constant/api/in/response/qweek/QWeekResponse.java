package ee.qrental.constant.api.in.response.qweek;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class QWeekResponse {
  private Long id;
  private Integer year;
  private Integer number;
  private String description;
}
