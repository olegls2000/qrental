package ee.qrent.billing.contract.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.domain.Contract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractEndDateCalculator {

  private final QDateTime qDateTime;

  public void setEndDate(final Contract contract) {
    if (contract == null) {
      return;
    }

    if (contract.getDateEnd() != null) {
      System.out.println(
          "Contract with id = "
              + contract.getId()
              + " already closed. End date does not need calculation");

      return;
    }
    final var duration = contract.getContractDuration();
    var endDate = contract.getDateStart().plusWeeks(duration.getWeeksCount());

    while (endDate.isBefore(qDateTime.getToday())) {
      endDate = endDate.plusWeeks(duration.getWeeksCount());
    }

    contract.setDateEnd(endDate);
  }
}
