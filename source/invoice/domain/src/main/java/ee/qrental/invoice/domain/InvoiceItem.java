package ee.qrental.invoice.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@SuperBuilder
@Getter
public class InvoiceItem {

  private BigDecimal amount;
  private String type;
  private String description;
}
