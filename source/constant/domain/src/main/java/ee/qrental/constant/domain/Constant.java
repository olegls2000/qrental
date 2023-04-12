package ee.qrental.constant.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
public class Constant {
    private Long id;
    private String constant;
    private BigDecimal value;
    private String description;
    private Boolean negative;
}
