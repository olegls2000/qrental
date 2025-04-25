package ee.qrent.billing.invoice.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InvoiceCalculationResponse {
  private Long id;
  private Integer invoicesCount;
  private String startWeek;
  private String endWeek;
  private LocalDate actionDate;
  private String comment;
}
