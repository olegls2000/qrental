package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.util.ControllerUtils.CALL_SIGN_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetCallSignQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CALL_SIGN_ROOT_PATH)
@AllArgsConstructor
public class CallSignQueryController {
  private final GetCallSignQuery callSignQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping
  public String getCallSignView(final Model model) {
    model.addAttribute("callSigns", callSignQuery.getAll());
    populateLinksCounts(model);

    return "callSigns";
  }

  private void populateLinksCounts(final Model model) {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    model.addAttribute("activeLinksCount", activeLinksCount);
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    model.addAttribute("closedLinksCount", closedLinksCount);
  }
}
