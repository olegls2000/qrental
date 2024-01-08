package ee.qrental.ui.controller.campaign;

import ee.qrental.transaction.api.in.query.campaign.GetCampaignQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.util.ControllerUtils.CAMPAIGN_ROOT_PATH;


@Controller
@RequestMapping(CAMPAIGN_ROOT_PATH)
@AllArgsConstructor
public class CampaignQueryController {

  private final GetCampaignQuery campaignQuery;

  @GetMapping
  public String getCampaignView(final Model model) {
    model.addAttribute("campaigns", campaignQuery.getAll());

    return "campaigns";
  }
}
