package ee.qrent.billing.ui.controller.bolt;

import static ee.qrent.billing.ui.controller.ControllerUtils.BOLT_STATISTICS_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BOLT_STATISTICS_ROOT_PATH)
@AllArgsConstructor
public class BoltRidesQueryController {

  private final QDateFormatter qDateFormatter;
  private final GetBoltRidesCountQuery query;

  @GetMapping("/rides")
  public String getFilterView(final Model model) {
    model.addAttribute("filtered", false);

    return "boltRides";
  }

  @PostMapping(value = "/rides")
  public String getFilteredView(
      @RequestParam("month") Integer month, @RequestParam("year") Integer year, final Model model) {
    model.addAttribute("filtered", true);
    model.addAttribute("counters", query.getAllByYearAndMonth(year, month));
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "boltRides";
  }
}
