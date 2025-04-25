package ee.qrent.billing.ui.controller.carlink;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.CAR_LINK_ROOT_PATH;
import static java.time.LocalDate.now;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CAR_LINK_ROOT_PATH)
@AllArgsConstructor
public class CarLinkQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCarLinkQuery carLinkQuery;

  @GetMapping(value = "/active")
  public String getActiveLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var carLinksActive = carLinkQuery.getAllActiveForCurrentDate();
    model.addAttribute("carLinksActive", carLinksActive);
    populatedLinksCounts(model);

    return "carLinksActive";
  }

  @GetMapping(value = "/closed")
  public String geHistoryLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var coledLinks = carLinkQuery.getClosedByDate(now());
    model.addAttribute("links", coledLinks);
    populatedLinksCounts(model);

    return "carLinksClosed";
  }

  private void populatedLinksCounts(final Model model) {
    final var activeLinksCount = carLinkQuery.getCountActiveForCurrentDate();
    model.addAttribute("carLinksActiveCount", activeLinksCount);
    final var closedLinksCount = carLinkQuery.getCountClosedForCurrentDate();
    model.addAttribute("carLinksClosedCount", closedLinksCount);
  }
}
