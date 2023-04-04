package ee.qrental.transaction.api.in.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class TransactionUpdateRequest extends AbstractUpdateRequest {

    private Long transactionTypeId;
    private Long driverId;
    private BigDecimal amount;
    private LocalDate date;
    private String comment;
}