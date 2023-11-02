package ee.qrental.constant.api.in.request.constant;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConstantAddRequest extends AbstractAddRequest {
    private String constant;
    private BigDecimal value;
    private String description;
    private Boolean negative;
}
