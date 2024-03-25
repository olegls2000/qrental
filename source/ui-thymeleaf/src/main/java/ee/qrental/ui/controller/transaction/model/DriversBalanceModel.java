package ee.qrental.ui.controller.transaction.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class DriversBalanceModel {
  private Long driverId;
  private String firstName;
  private String lastName;
  private Integer callSign;
  private String phone;
  private BigDecimal rawTotal;
  private BigDecimal fee;
  private BigDecimal obligationAmount;
  private Integer obligationMatchCount;
}