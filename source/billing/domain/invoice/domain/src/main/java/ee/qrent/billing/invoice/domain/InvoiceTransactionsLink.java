package ee.qrent.billing.invoice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
public class InvoiceTransactionsLink {
  private Invoice invoice;
  private Set<Long> transactionIds;
}
