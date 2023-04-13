package ee.qrental.link.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LinkUpdateRequest extends AbstractUpdateRequest {
  private Long carId;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
