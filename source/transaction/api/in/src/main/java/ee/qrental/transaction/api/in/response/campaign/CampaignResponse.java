package ee.qrental.transaction.api.in.response.campaign;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CampaignResponse {
    private Long id;
    private String campaign;
    private String description;
    private Boolean active;

}
