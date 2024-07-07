package ee.qrental.contract.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.domain.Contract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractBusinessRuleValidator implements QValidator<Contract> {

  private final ContractLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final Contract domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(Contract domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    return null;
  }

  private void checkUniqueness(final Contract domain, final ViolationsCollector violationCollector) {
    final var number = domain.getNumber();
    final var contractFromDb = loadPort.loadByNumber(number);
    if (contractFromDb == null) {

      return;
    }
    violationCollector.collect(format("Contract with number: %s already exists.", number));
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of Contract failed. No Record with id = " + id);
    }
  }
}
