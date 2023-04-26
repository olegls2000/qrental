package ee.qrental.ui.controller.link;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.transaction.api.in.query.GetDriverBalanceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.ControllerUtils.LINK_ROOT_PATH;


@Controller
@RequestMapping(LINK_ROOT_PATH)
@AllArgsConstructor
public class LinkQueryController {

  private final GetLinkQuery linkQuery;

  private final GetCarQuery carQuery;

  private final GetDriverBalanceQuery balanceQuery;

  @GetMapping
  public String getLinkView(final Model model) {
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
    final var drivers = balanceQuery.getAll();
    model.addAttribute("drivers", drivers);
  }


}
