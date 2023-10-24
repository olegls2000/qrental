package ee.qrental.ui.controller.transaction.calculation.rent;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.*;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class RentCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetRentCalculationQuery rentCalculationQuery;

  @GetMapping("/rent-calculations")
  public String getRentCalculationView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("calculations", rentCalculationQuery.getAll());

    return "rentCalculations";
  }

  @GetMapping(value = "/rent-calculations/{id}")
  public String getRentCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute("rentTransactions", new ArrayList<>());

    return "detailView/rentCalculation";
  }
}
