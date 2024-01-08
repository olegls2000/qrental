package ee.qrental.transaction.domain.campaign;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Campaign {
    private Long id;
    private String campaign;
    private String description;
    private Boolean active;
}
