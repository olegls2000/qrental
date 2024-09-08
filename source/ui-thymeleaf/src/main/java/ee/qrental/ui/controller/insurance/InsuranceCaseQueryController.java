package ee.qrental.ui.controller.insurance;

import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.util.ControllerUtils.INSURANCE_ROOT_PATH;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
@AllArgsConstructor
public class InsuranceCaseQueryController {

  private final GetInsuranceCaseQuery insuranceCaseQuery;

  @GetMapping("/cases")
  public String getTableView(final Model model) {
    model.addAttribute("insuranceCases", insuranceCaseQuery.getAll());
    return "insuranceCases";
  }

  @GetMapping("/cases/{id}/balances")
  public String getInsuranceCaseBalancesTableView(@PathVariable("id") long id, final Model model) {
    model.addAttribute("insuranceCaseBalances", insuranceCaseQuery.getInsuranceCaseBalancesByInsuranceCase(id));
    return "forms/viewInsuranceCaseBalances";
  }
}
