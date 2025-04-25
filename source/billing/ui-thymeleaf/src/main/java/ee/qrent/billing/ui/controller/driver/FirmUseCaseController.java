package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.FIRM_ROOT_PATH;

import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.firm.api.in.request.FirmAddRequest;
import ee.qrent.billing.firm.api.in.request.FirmDeleteRequest;
import ee.qrent.billing.firm.api.in.request.FirmUpdateRequest;
import ee.qrent.billing.firm.api.in.usecase.FirmAddUseCase;
import ee.qrent.billing.firm.api.in.usecase.FirmDeleteUseCase;
import ee.qrent.billing.firm.api.in.usecase.FirmUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(FIRM_ROOT_PATH)
@AllArgsConstructor
public class FirmUseCaseController {

  private final FirmAddUseCase addUseCase;
  private final FirmUpdateUseCase updateUseCase;
  private final FirmDeleteUseCase deleteUseCase;
  private final GetFirmQuery firmQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new FirmAddRequest());

    return "forms/addFirm";
  }

  @PostMapping(value = "/add")
  public String addFirmFirm(@ModelAttribute final FirmAddRequest firmInfo) {
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
