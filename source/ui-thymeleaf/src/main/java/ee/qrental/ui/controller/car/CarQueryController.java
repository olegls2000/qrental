package ee.qrental.ui.controller.car;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.CAR_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CAR_ROOT_PATH)
@AllArgsConstructor
public class CarQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCarQuery carQuery;
  private final GetCarLinkQuery carLinkQuery;

  @GetMapping
  public String getCarView(final Model model) {
    model.addAttribute("cars", carQuery.getAll());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    populatedLinksCounts(model);

    return "cars";
  }

  private void populatedLinksCounts(final Model model) {
    final var activeLinksCount = carLinkQuery.getCountActive();
    model.addAttribute("carLinksActiveCount", activeLinksCount);
    final var closedLinksCount = carLinkQuery.getCountClosed();
    model.addAttribute("carLinksClosedCount", closedLinksCount);
  }
}
