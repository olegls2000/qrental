package ee.qrent.billing.ui.controller.insurance;

import static ee.qrent.billing.ui.controller.ControllerUtils.INSURANCE_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.insurance.InsuranceCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
public class InsuranceCalculationQueryController extends AbstractInsuranceQueryController {

  private final GetInsuranceCalculationQuery calculationQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final GetRentCalculationQuery rentCalculationQuery;

  public InsuranceCalculationQueryController(
      final QDateFormatter qDateFormatter,
      final InsuranceCounterService insuranceCounterService,
      final GetInsuranceCalculationQuery calculationQuery,
      final GetTransactionQuery transactionQuery,
      final GetQWeekQuery qWeekQuery,
      final GetBalanceCalculationQuery balanceCalculationQuery,
      final GetRentCalculationQuery rentCalculationQuery) {
    super(qDateFormatter, insuranceCounterService);
    this.calculationQuery = calculationQuery;
    this.transactionQuery = transactionQuery;
    this.qWeekQuery = qWeekQuery;
    this.balanceCalculationQuery = balanceCalculationQuery;
    this.rentCalculationQuery = rentCalculationQuery;
  }

  @GetMapping("/calculations")
  public String getTableView(final Model model) {
    addLatestDataToModel(model);
    model.addAttribute("calculations", calculationQuery.getAll());
    addDateFormatter(model);
    addInsuranceCounts(model);

    return "insuranceCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getCalculationView(@PathVariable("id") long id, final Model model) {
    final var calculation = calculationQuery.getById(id);
    final var transactions = transactionQuery.getAllByInsuranceCalculationId(id);
    model.addAttribute("calculation", calculation);
    model.addAttribute("transactions", transactions);
    addDateFormatter(model);

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
