package ee.qrental.transaction.api.in.request.campaign;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CampaignUpdateRequest extends AbstractUpdateRequest {
    private Long campaignId;
    private String campaign;
    private String description;
    private Boolean active;

}
