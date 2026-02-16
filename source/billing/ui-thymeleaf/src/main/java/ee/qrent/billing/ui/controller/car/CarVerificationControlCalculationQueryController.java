package ee.qrent.billing.ui.controller.car;

import static ee.qrent.billing.ui.controller.ControllerUtils.CAR_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.car.api.in.query.GetBrandingVerificationCalculationQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CAR_ROOT_PATH)
@AllArgsConstructor
public class CarVerificationControlCalculationQueryController {

  private final QDateFormatter qDateFormatter;
  private final GetCarQuery carQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final GetBrandingVerificationCalculationQuery calculationQuery;

  @GetMapping("/verification-control-calculations")
  public String getVerificationControlCalculations(final Model model) {
    model.addAttribute("calculations", calculationQuery.getAllCalculations());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    populatedCounts(model);

    return "brandingVerificationCalculations";
  }

  @GetMapping("/verification-control-calculations/{id}")
  public String getVerificationControlCalculation(final Model model, @PathVariable("id") long id) {
    model.addAttribute("calculationResults", calculationQuery.getResultsByCalculationId(id));
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "detailView/brandingVerificationCalculation";
  }

  private void populatedCounts(final Model model) {
    model.addAttribute("carsCount", carQuery.getCarsCount());
    model.addAttribute("activeCarsCount", carQuery.getActiveCarsCount());
    final var activeLinksCount = carLinkQuery.getCountActiveForCurrentDate();
    model.addAttribute("carLinksActiveCount", activeLinksCount);
    final var closedLinksCount = carLinkQuery.getCountClosedForCurrentDate();
    model.addAttribute("carLinksClosedCount", closedLinksCount);
  }
}
