package ee.qrental.ui.controller.firm;

import static ee.qrental.ui.controller.util.ControllerUtils.FIRM_ROOT_PATH;

import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.firm.api.in.request.WeekObligationAddRequest;
import ee.qrental.firm.api.in.request.FirmDeleteRequest;
import ee.qrental.firm.api.in.request.FirmUpdateRequest;
import ee.qrental.firm.api.in.usecase.WeekObligationAddUseCase;
import ee.qrental.firm.api.in.usecase.FirmDeleteUseCase;
import ee.qrental.firm.api.in.usecase.FirmUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(FIRM_ROOT_PATH)
@AllArgsConstructor
public class FirmUseCaseController {

  private final WeekObligationAddUseCase addUseCase;
  private final FirmUpdateUseCase updateUseCase;
  private final FirmDeleteUseCase deleteUseCase;
  private final GetFirmQuery firmQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new WeekObligationAddRequest());

    return "forms/addFirm";
  }

  @PostMapping(value = "/add")
  public String addFirmFirm(@ModelAttribute final WeekObligationAddRequest firmInfo) {
    addUseCase.add(firmInfo);

    return "redirect:" + FIRM_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", firmQuery.getUpdateRequestById(id));

    return "forms/updateFirm";
  }

  @PostMapping("/update")
  public String updateFirmFirm(final FirmUpdateRequest firmUpdateRequest) {
    updateUseCase.update(firmUpdateRequest);

    return "redirect:" + FIRM_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new FirmDeleteRequest(id));
    model.addAttribute("objectInfo", firmQuery.getObjectInfo(id));

    return "forms/deleteFirm";
  }

  @PostMapping("/delete")
  public String deleteForm(final FirmDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + FIRM_ROOT_PATH;
  }
}
