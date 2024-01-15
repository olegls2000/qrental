package ee.qrental.bonus.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
public class Obligation {
    private Long id;
    private Long qWeekId;
    private Long driverId;
    private BigDecimal obligationAmount;
    private BigDecimal positiveAmount;
    private Integer matchCount;
}
