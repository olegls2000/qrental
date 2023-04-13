package ee.qrental.invoice.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class InvoiceCalculation {
  private Long id;
  private LocalDate actionDate;
  private List<Invoice> results;
  private String comment;
}
