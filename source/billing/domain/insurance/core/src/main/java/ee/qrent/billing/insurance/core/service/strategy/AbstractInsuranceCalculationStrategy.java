package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
public abstract class AbstractInsuranceCalculationStrategy implements InsuranceCalculationStrategy {

    @Getter(PROTECTED)
    private final GetContractQuery contractQuery;
    private final InsuranceCaseUpdatePort caseUpdatePort;


    protected void checkAndDeactivateIfNecessary(
            final InsuranceCaseBalance balance, final InsuranceCase insuranceCase) {
        if (balance.getDamageRemaining().compareTo(ZERO) == 0
                && balance.getSelfResponsibilityRemaining().compareTo(ZERO) == 0) {
            insuranceCase.setActive(false);
            caseUpdatePort.update(insuranceCase);
        }
    }
}
