package ee.qrental.ui.controller.insurance;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.INSURANCE_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
@AllArgsConstructor
public class InsuranceCalculationQueryController {

  private final GetInsuranceCalculationQuery calculationQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final GetRentCalculationQuery rentCalculationQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping("/calculations")
  public String getTableView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", calculationQuery.getAll());
    return "insuranceCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var calculation = calculationQuery.getById(id);
    final var transactions = transactionQuery.getAllByInsuranceCalculationId(id);

    model.addAttribute("calculation", calculation);
    model.addAttribute("transactions", transactions);

    return "detailView/insuranceCalculation";
  }

  private void addLatestDataToModel(final Model model) {
    final var latestRentCalculatedWeekId = rentCalculationQuery.getLastCalculatedQWeekId();
    if (latestRentCalculatedWeekId == null) {
      model.addAttribute("latestRentWeek", "Rent was not calculated");

      return;
    }

    final var latestRentWeek = qWeekQuery.getById(latestRentCalculatedWeekId);
    final var latestRentWeekLabel =
        String.format("%d (%s)", latestRentWeek.getNumber(), latestRentWeek.getEnd());
    model.addAttribute("latestRentWeek", latestRentWeekLabel);

    final var latestInsuranceBalanceWeekId = calculationQuery.getLastCalculatedQWeekId();
    if (latestInsuranceBalanceWeekId == null) {
      model.addAttribute("latestInsuranceBalanceWeek", "Insurance Balance was not calculated");

      return;
    }

    final var latestInsuranceBalanceWeek = qWeekQuery.getById(latestInsuranceBalanceWeekId);
    final var latestInsuranceBalanceWeekLabel =
        String.format(
            "%d (%s)", latestInsuranceBalanceWeek.getNumber(), latestInsuranceBalanceWeek.getEnd());
    model.addAttribute("latestInsuranceBalanceWeek", latestInsuranceBalanceWeekLabel);

    final var latestFinancialCalculatedWeekId = balanceCalculationQuery.getLastCalculatedQWeekId();
    if (latestFinancialCalculatedWeekId == null) {
      model.addAttribute("latestFinancialBalanceWeek", "Financial Balance was not calculated");

      return;
    }

    final var latestCalculatedWeek = qWeekQuery.getById(latestFinancialCalculatedWeekId);
    final var latestBalanceWeekLabel =
        String.format("%d (%s)", latestCalculatedWeek.getNumber(), latestCalculatedWeek.getEnd());
    model.addAttribute("latestBalanceWeek", latestBalanceWeekLabel);
  }
}
