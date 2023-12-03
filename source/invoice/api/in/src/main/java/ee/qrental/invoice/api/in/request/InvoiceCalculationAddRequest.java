package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceCalculationAddRequest extends AbstractAddRequest {

  private LocalDate actionDate = LocalDate.now();
  private Long qWeekId;
  private String comment;
}
