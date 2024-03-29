package ee.qrental.ui.controller.firm;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.FIRM_LINK_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetFirmLinkQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping(FIRM_LINK_ROOT_PATH)
public class FirmLinkQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetFirmLinkQuery firmLinkQuery;

  @GetMapping
  public String getFirmLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("firmLinks", firmLinkQuery.getAll());

    return "firmLinks";
  }
}
