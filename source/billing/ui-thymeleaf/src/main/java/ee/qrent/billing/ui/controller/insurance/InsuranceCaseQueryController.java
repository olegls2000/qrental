package ee.qrent.billing.ui.controller.insurance;

import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.insurance.InsuranceCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.controller.ControllerUtils.INSURANCE_ROOT_PATH;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH + "/cases")
public class InsuranceCaseQueryController extends AbstractInsuranceQueryController {

  private final GetInsuranceCaseQuery insuranceCaseQuery;

  public InsuranceCaseQueryController(
      final QDateFormatter qDateFormatter,
      final InsuranceCounterService insuranceCounterService,
      final GetInsuranceCaseQuery insuranceCaseQuery) {
    super(qDateFormatter, insuranceCounterService);
    this.insuranceCaseQuery = insuranceCaseQuery;
  }

  @GetMapping("/active")
  public String getActiveCasesView(final Model model) {
    model.addAttribute("insuranceCases", insuranceCaseQuery.getActive());
    addInsuranceCounts(model);
    addDateFormatter(model);

    return "insuranceCasesActive";
  }

  @GetMapping("/closed")
  public String geClosedCasesView(final Model model) {
    model.addAttribute("insuranceCases", insuranceCaseQuery.getClosed());
    addInsuranceCounts(model);
    addDateFormatter(model);

    return "insuranceCasesClosed";
  }

  @GetMapping("/{id}/balances")
  public String getInsuranceCaseBalancesTableView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(
        "insuranceCaseBalances", insuranceCaseQuery.getInsuranceCaseBalancesByInsuranceCase(id));
    addDateFormatter(model);

    return "forms/viewInsuranceCaseBalances";
  }
}
