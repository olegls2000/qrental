package ee.qrental.ui.controller.transaction;

import ee.qrental.common.core.utils.QWeek;

import java.util.List;
import ee.qrental.transaction.api.in.request.TransactionFilterRequest;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

@UtilityClass
public class TransactionFilterRequestUtils {

    static void addFilterOptionsToModel(final Model model) {
        model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", QWeek.values());
    }

    static void addCleanFilterRequestToModel(final Long driverId, final Model model) {
    final var transactionFilterRequest = new TransactionFilterRequest();
        transactionFilterRequest.setDriverId(driverId);
        model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    }

    static void addCleanFilterRequestToModel(final Model model) {
        final var transactionFilterRequest = new TransactionFilterRequest();
        model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    }

}
