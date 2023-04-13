package ee.qrental.invoice.api.in.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceCalculationRequest {

  private LocalDate actionDate = LocalDate.now();
  private String comment;
}
