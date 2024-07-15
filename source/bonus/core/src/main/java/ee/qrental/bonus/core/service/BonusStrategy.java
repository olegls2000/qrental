package ee.qrental.bonus.core.service;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.util.List;

public interface BonusStrategy {

  String STRATEGY_2_WEEKS_PREPAYMENT_CODE = "2W";
  String STRATEGY_4_WEEKS_PREPAYMENT_CODE = "4W";
  String STRATEGY_RELIABLE_PARTNER_CODE = "RP";
  String STRATEGY_NEW_DRIVER_CODE = "ND";
  String STRATEGY_FRIENDSHIP_REWARD_CODE = "BF";


  boolean canApply(final BonusProgram bonusProgram);

  List<TransactionAddRequest> calculateBonus(final Obligation obligation, final BigDecimal weekPositiveAmount);

  String getBonusCode();
}
