package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping(CALL_SIGN_LINK_ROOT_PATH)
public class CallSignLinkQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping(value = "/active")
  public String getActiveLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var callSignLinksActive = callSignLinkQuery.getActive();
    model.addAttribute("callSignLinksActive", callSignLinksActive);
    populateLinksCounts(model);

    return "callSignLinksActive";
  }

  @GetMapping(value = "/closed")
  public String geHistoryLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var callSignLinksClosed = callSignLinkQuery.getClosed();
    model.addAttribute("callSignLinksClosed", callSignLinksClosed);
    populateLinksCounts(model);

    return "callSignLinksClosed";
  }

  private void populateLinksCounts(final Model model) {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    model.addAttribute("activeLinksCount", activeLinksCount);
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    model.addAttribute("closedLinksCount", closedLinksCount);
  }
}
