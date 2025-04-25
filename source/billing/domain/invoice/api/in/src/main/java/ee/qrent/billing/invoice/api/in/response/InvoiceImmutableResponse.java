package ee.qrent.billing.invoice.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InvoiceImmutableResponse {
    private String number;
    private LocalDate created;
    private Map<String, BigDecimal> items;
}
