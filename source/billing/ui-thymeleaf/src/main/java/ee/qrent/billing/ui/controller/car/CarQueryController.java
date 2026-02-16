package ee.qrent.billing.ui.controller.car;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.query.filter.Availability;
import ee.qrent.billing.car.api.in.query.filter.CarFilter;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.CAR_ROOT_PATH;
@Controller
@RequestMapping(CAR_ROOT_PATH)
@AllArgsConstructor
public class CarQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCarQuery carQuery;
  private final GetCarLinkQuery carLinkQuery;

  @GetMapping
  public String getCarView(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("states", Availability.values());
    model.addAttribute("carFilterRequest", new CarFilter());
    model.addAttribute("carsCount", carQuery.getCarsCount());
    model.addAttribute("activeCarsCount", carQuery.getActiveCarsCount());
    populatedLinksCounts(model);

    return "cars";
  }

  @GetMapping("/active")
  public String getActiveCarsView(final Model model) {
    model.addAttribute("cars", carQuery.getActiveCars());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("carsCount", carQuery.getCarsCount());
    model.addAttribute("activeCarsCount", carQuery.getActiveCarsCount());
    populatedLinksCounts(model);

    return "activeCars";
  }

    @PostMapping
  public String getPageWithFilteredCars(
      @ModelAttribute final CarFilter carFilterRequest, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("states", Availability.values());
    model.addAttribute("carFilterRequest", carFilterRequest);
    model.addAttribute("cars", carQuery.getAllByFilter(carFilterRequest));
    populatedLinksCounts(model);

    return "cars";
  }

  private void populatedLinksCounts(final Model model) {
    final var activeLinksCount = carLinkQuery.getCountActiveForCurrentDate();
    model.addAttribute("carLinksActiveCount", activeLinksCount);
    final var closedLinksCount = carLinkQuery.getCountClosedForCurrentDate();
    model.addAttribute("carLinksClosedCount", closedLinksCount);
  }
}
