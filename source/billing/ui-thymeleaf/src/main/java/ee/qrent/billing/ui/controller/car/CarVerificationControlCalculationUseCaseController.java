package ee.qrent.billing.ui.controller.car;

import static ee.qrent.billing.ui.controller.ControllerUtils.CAR_ROOT_PATH;

import ee.qrent.billing.car.api.in.request.BrandingVerificationCalculationAddRequest;
import ee.qrent.billing.car.api.in.usecase.BrandingVerificationCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CAR_ROOT_PATH)
@AllArgsConstructor
public class CarVerificationControlCalculationUseCaseController {

  private final BrandingVerificationCalculationAddUseCase addUseCase;

  @GetMapping("/verification-control-calculations/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new BrandingVerificationCalculationAddRequest());

    return "forms/addBrandingVerificationCalculation";
  }

  @PostMapping("/verification-control-calculations/add")
  public String addCalculation(
      @ModelAttribute final BrandingVerificationCalculationAddRequest addRequest,
      final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addBrandingVerificationCalculation";
    }

    return "redirect:" + CAR_ROOT_PATH + "/verification-control-calculations";
  }
}
