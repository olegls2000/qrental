package ee.qrent.billing.ui.controller.deposit;

import static ee.qrent.billing.ui.controller.ControllerUtils.DEPOSIT_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(DEPOSIT_ROOT_PATH)
@AllArgsConstructor
public class DepositQueryController {

  private final GetDepositQuery depositQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping
  public String getFirmView(final Model model) {
    model.addAttribute("deposits", depositQuery.getAll());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "deposits";
  }
}
