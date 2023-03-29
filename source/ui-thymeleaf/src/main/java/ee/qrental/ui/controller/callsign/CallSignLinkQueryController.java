package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping(CALL_SIGN_LINK_ROOT_PATH)
public class CallSignLinkQueryController {
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping
  public String getCallSignLinkView(final Model model) {
    model.addAttribute("callSignLinks", callSignLinkQuery.getAll());
    return "callSignLinks";
  }
}
