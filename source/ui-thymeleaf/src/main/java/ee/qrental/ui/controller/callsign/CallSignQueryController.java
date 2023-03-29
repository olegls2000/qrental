package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.ControllerUtils.CALL_SIGN_ROOT_PATH;

import ee.qrental.callsign.api.in.query.GetCallSignQuery;
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

  @GetMapping
  public String getCallSignView(final Model model) {
    model.addAttribute("callSigns", callSignQuery.getAll());

    return "callSigns";
  }
}
