package ee.qrental.transaction.api.in.response.balance;

import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceDetailResponse {
  private Long total;
  private String driverInfo;
  private List<TransactionResponse> transactions;
}
