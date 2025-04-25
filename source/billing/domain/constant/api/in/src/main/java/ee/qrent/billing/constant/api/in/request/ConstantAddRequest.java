package ee.qrent.billing.constant.api.in.request;


import java.math.BigDecimal;

import ee.qrent.common.in.request.AbstractAddRequest;
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
