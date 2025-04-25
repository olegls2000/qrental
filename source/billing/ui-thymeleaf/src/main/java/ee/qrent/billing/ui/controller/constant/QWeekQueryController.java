package ee.qrent.billing.ui.controller.constant;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.WEEK_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(WEEK_ROOT_PATH)
@AllArgsConstructor
public class QWeekQueryController {

  private final GetQWeekQuery qWeekQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping
  public String getConstantView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("weeks", qWeekQuery.getAll());

    return "qWeeks";
  }
}
