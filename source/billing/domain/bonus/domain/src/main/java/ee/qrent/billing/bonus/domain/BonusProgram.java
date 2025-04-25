package ee.qrent.billing.bonus.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BonusProgram {
    private Long id;
    private String code;
    private String nameEng;
    private String nameRus;
    private String nameEst;
    private String description;
    private Boolean active;
}
