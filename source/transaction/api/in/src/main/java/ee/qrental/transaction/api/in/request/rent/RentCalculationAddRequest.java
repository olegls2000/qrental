package ee.qrental.transaction.api.in.request.rent;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RentCalculationAddRequest extends AbstractAddRequest {
  private Long qWeekId;
  private String comment;
}
