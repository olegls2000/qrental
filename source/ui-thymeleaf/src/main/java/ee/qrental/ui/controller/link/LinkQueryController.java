package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.LINK_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(LINK_ROOT_PATH)
@AllArgsConstructor
public class LinkQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCarLinkQuery linkQuery;
  private final GetCarQuery carQuery;
  private final GetBalanceQuery balanceQuery;

  @GetMapping
  public String getLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLinkListToModel(model);
    addCarListToModel(model);
    addDriverListToModel(model);

    return "links";
  }

  private void addLinkListToModel(final Model model) {
    final var links = linkQuery.getAll();
    model.addAttribute("links", links);
  }

  private void addCarListToModel(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
  }

  private void addDriverListToModel(final Model model) {
    final var drivers = balanceQuery.getAllBalanceTotalsWithDriver();
    model.addAttribute("drivers", drivers);
  }
}
