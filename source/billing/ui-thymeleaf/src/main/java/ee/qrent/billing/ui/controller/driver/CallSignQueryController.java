package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.CALL_SIGN_ROOT_PATH;

import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.driver.DriverCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CALL_SIGN_ROOT_PATH)
public class CallSignQueryController extends AbstractDriverQueryController {
  private final GetCallSignQuery callSignQuery;

  public CallSignQueryController(
      final DriverCounterService driverCounterService,
      final QDateFormatter qDateFormatter,
      final GetCallSignQuery callSignQuery) {
    super(driverCounterService, qDateFormatter);
    this.callSignQuery = callSignQuery;
  }

  @GetMapping
  public String getCallSignView(final Model model) {
    model.addAttribute("callSigns", callSignQuery.getAll());
    addDriverCounts(model);

    return "callSigns";
  }
}
