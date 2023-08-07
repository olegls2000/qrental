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

  @GetMapping
  public String getCallSignLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("callSignLinks", callSignLinkQuery.getAll());

    return "callSignLinks";
  }
}
