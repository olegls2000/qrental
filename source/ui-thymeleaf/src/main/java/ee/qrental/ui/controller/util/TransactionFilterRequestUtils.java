package ee.qrental.ui.controller.util;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.transaction.api.in.query.filter.FeeOption;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

@UtilityClass
public class TransactionFilterRequestUtils {

  public static void addWeekOptionsToModel(final Model model) {
    model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", QWeek.values());
  }

  public static void addCleanFilterRequestToModel(final Long driverId, final Model model) {
    final var transactionFilterRequest = new YearAndWeekAndDriverAndFeeFilter();
    transactionFilterRequest.setDriverId(driverId);
    transactionFilterRequest.setWeek(QWeek.ALL);
    transactionFilterRequest.setYear(2023);
    transactionFilterRequest.setFeeOption(FeeOption.WITH_FEE);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
  }

  public static void addCleanFilterRequestToModel(final Model model) {
    final var transactionFilterRequest = new YearAndWeekAndDriverAndFeeFilter();
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
  }
}
