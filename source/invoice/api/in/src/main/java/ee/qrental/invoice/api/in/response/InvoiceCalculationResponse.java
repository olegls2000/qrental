package ee.qrental.invoice.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InvoiceCalculationResponse {
  private Long id;
  private Integer invoicesCount;
  private LocalDate actionDate;
  private String comment;
}
