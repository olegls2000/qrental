package ee.qrental.transaction.api.in.request;

import ee.qrental.common.core.utils.QWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TransactionFilterRequest {
    private Integer year;
    private QWeek week;
    private Long driverId;
}
