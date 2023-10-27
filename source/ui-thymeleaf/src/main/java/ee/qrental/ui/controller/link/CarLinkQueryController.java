package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.CAR_LINK_ROOT_PATH;
import static java.time.LocalDate.now;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
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
  private final GetCarLinkQuery linkQuery;

  @GetMapping(value = "/active")
  public String getActiveLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var links = linkQuery.getActiveByDate(now());
    model.addAttribute("links", links);
    model.addAttribute("activeLinksCount", links.size());

    final var coledLinks = linkQuery.getClosedByDate(now());
    model.addAttribute("closedLinksCount", coledLinks.size());

    return "carLinksActive";
  }

  @GetMapping(value = "/closed")
  public String geHistoryLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var coledLinks = linkQuery.getClosedByDate(now());
    model.addAttribute("links", coledLinks);


    final var activeLinks = linkQuery.getActiveByDate(now());

    model.addAttribute("activeLinksCount", activeLinks.size());
    model.addAttribute("closedLinksCount", coledLinks.size());

    return "carLinksClosed";
  }
}
