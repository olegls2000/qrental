package ee.qrental.transaction.api.in.request.campaign;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CampaignAddRequest extends AbstractAddRequest {
  private Long Id;
  private String campaign;
  private String description;
   private Boolean active;
  }
