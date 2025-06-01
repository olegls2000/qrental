package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
public abstract class AbstractInsuranceCalculationStrategy implements InsuranceCalculationStrategy {

    @Getter(PROTECTED)
    private final GetContractQuery contractQuery;
}
