package ee.qrental.ui.controller.campaign;

import ee.qrental.transaction.api.in.query.campaign.GetCampaignQuery;
import ee.qrental.transaction.api.in.request.campaign.CampaignAddRequest;
import ee.qrental.transaction.api.in.request.campaign.CampaignDeleteRequest;
import ee.qrental.transaction.api.in.request.campaign.CampaignUpdateRequest;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignAddUseCase;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrental.ui.controller.util.ControllerUtils.CAMPAIGN_ROOT_PATH;


@Controller
@RequestMapping(CAMPAIGN_ROOT_PATH)
@AllArgsConstructor
public class CampaignUseCaseController {

  private final CampaignAddUseCase addUseCase;
  private final CampaignUpdateUseCase updateUseCase;
  private final CampaignDeleteUseCase deleteUseCase;
  private final GetCampaignQuery campaignQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new CampaignAddRequest());

    return "forms/addCampaign";
  }

  @PostMapping(value = "/add")
  public String addCampaignCampaign(@ModelAttribute final CampaignAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addCampaign";
    }

    return "redirect:" + CAMPAIGN_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", campaignQuery.getUpdateRequestById(id));

    return "forms/updateCampaign";
  }

  @PostMapping("/update")
  public String updateCamapaignCampaign(final CampaignUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateCampaign";
    }

    return "redirect:" + CAMPAIGN_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new CampaignDeleteRequest(id));
    model.addAttribute("objectInfo", campaignQuery.getObjectInfo(id));

    return "forms/deleteCampaign";
  }

  @PostMapping("/delete")
  public String deleteForm(final CampaignDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteCampaign";
    }
    return "redirect:" + CAMPAIGN_ROOT_PATH;
  }
}
