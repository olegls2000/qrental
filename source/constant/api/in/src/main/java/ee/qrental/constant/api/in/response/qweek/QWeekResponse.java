package ee.qrental.constant.api.in.response.qweek;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class QWeekResponse {
  private Long id;
  private Integer year;
  private Integer number;
  private LocalDate start;
  private LocalDate end;
  private String description;
}
