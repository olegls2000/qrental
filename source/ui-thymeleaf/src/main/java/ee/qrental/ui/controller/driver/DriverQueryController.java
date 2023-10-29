package ee.qrental.ui.controller.driver;

import static ee.qrental.ui.controller.util.ControllerUtils.DRIVER_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(DRIVER_ROOT_PATH)
@AllArgsConstructor
public class DriverQueryController {

  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping
  public String getDriverView(final Model model) {
    model.addAttribute("drivers", driverQuery.getAll());
    populateLinksCounts(model);

    return "drivers";
  }

  private void populateLinksCounts(final Model model) {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    model.addAttribute("activeLinksCount", activeLinksCount);
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    model.addAttribute("closedLinksCount", closedLinksCount);
  }
}
