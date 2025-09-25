package ee.qrent.billing.transaction.api.in.response.type;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionTypeResponse {
  private Long id;
  private String code;
  private String nameEng;
  private String nameRus;
  private String nameEst;
  private Boolean invoiceIncluded;
  private String uiName;
  private Boolean visibleForUi;
  private String kind;
  private Boolean negative;
  private Boolean feeAble;
  private String comment;
}
