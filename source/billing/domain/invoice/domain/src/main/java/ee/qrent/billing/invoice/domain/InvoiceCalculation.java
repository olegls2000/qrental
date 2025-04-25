package ee.qrent.billing.invoice.domain;

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
  private Long startQWeekId;
  private Long endQWeekId;
  private LocalDate actionDate;
  private List<InvoiceCalculationResult> results;
  private String comment;
}