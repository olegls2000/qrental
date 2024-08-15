package ee.qrental.insurance.core.validator;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.out.InsuranceCalculationLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationAddBusinessRuleValidator {
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCalculationLoadPort loadPort;


}
