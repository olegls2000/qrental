package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.driver.DriverCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CALL_SIGN_LINK_ROOT_PATH)
public class CallSignLinkQueryController extends AbstractDriverQueryController {

  private final GetCallSignLinkQuery callSignLinkQuery;

  public CallSignLinkQueryController(
      final DriverCounterService driverCounterService,
      final QDateFormatter qDateFormatter,
      final GetCallSignLinkQuery callSignLinkQuery) {
    super(driverCounterService, qDateFormatter);
    this.callSignLinkQuery = callSignLinkQuery;
  }

  @GetMapping(value = "/active")
  public String getActiveLinkView(final Model model) {
    final var callSignLinksActive = callSignLinkQuery.getActive();
    model.addAttribute("callSignLinksActive", callSignLinksActive);
    addDriverCounts(model);
    addDateFormatter(model);

    return "callSignLinksActive";
  }

  @GetMapping(value = "/closed")
  public String geClosedLinkView(final Model model) {
    final var callSignLinksClosed = callSignLinkQuery.getClosed();
    model.addAttribute("callSignLinksClosed", callSignLinksClosed);
    addDriverCounts(model);
    addDateFormatter(model);

    return "callSignLinksClosed";
  }
}
