package ee.qrent.billing.ui.controller.bolt;

import static ee.qrent.billing.ui.controller.ControllerUtils.BOLT_STATISTICS_ROOT_PATH;
import static ee.qrent.billing.ui.controller.ControllerUtils.FIRM_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BOLT_STATISTICS_ROOT_PATH)
@AllArgsConstructor
public class BoltStatisticsQueryController {

  private final QDateFormatter qDateFormatter;
  private final GetBoltStatisticsQuery query;

  @GetMapping("/reports")
  public String getView(final Model model) {
    model.addAttribute("reports", query.getAll());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "boltStatistics";
  }
}
