package ee.qrent.billing.ui.controller.bonus;

import static ee.qrent.billing.ui.controller.ControllerUtils.BONUS_PROGRAM_PATH;

import ee.qrent.billing.bonus.api.in.query.GetBonusProgramQuery;
import ee.qrent.billing.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrent.billing.bonus.api.in.request.BonusProgramDeleteRequest;
import ee.qrent.billing.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramAddUseCase;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramDeleteUseCase;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BONUS_PROGRAM_PATH)
@AllArgsConstructor
public class BonusProgramUseCaseController {

  private final BonusProgramAddUseCase addUseCase;
  private final BonusProgramUpdateUseCase updateUseCase;
  private final BonusProgramDeleteUseCase deleteUseCase;
  private final GetBonusProgramQuery bonusProgramQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new BonusProgramAddRequest());

    return "forms/addBonusProgram";
  }

  @PostMapping(value = "/add")
  public String addCampaignCampaign(
      @ModelAttribute final BonusProgramAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addBonusProgram";
    }

    return "redirect:" + BONUS_PROGRAM_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", bonusProgramQuery.getUpdateRequestById(id));

    return "forms/updateBonusProgram";
  }

  @PostMapping("/update")
  public String updateBonusProgram(
      final BonusProgramUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateBonusProgram";
    }

    return "redirect:" + BONUS_PROGRAM_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new BonusProgramDeleteRequest(id));
    model.addAttribute("objectInfo", bonusProgramQuery.getObjectInfo(id));

    return "forms/deleteBonusProgram";
  }

  @PostMapping("/delete")
  public String deleteForm(final BonusProgramDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteBonusProgram";
    }
    return "redirect:" + BONUS_PROGRAM_PATH;
  }
}
