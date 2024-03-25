package ee.qrental.transaction.domain.rent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class RentCalculationResult {
    private Long id;
    private Long calculationId;
    private Long carLinkId;
    private Long transactionId;
}