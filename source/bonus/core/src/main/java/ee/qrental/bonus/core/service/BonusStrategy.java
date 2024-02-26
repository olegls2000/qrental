package ee.qrental.bonus.core.service;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.util.Optional;

public interface BonusStrategy {

  String STRATEGY_2_WEEKS_PREPAYMENT_CODE = "2W";
  String BONUS_TRANSACTION_TYPE_NAME = "bonus";

  boolean canApply(final BonusProgram bonusProgram);

  Optional<TransactionAddRequest> calculateBonus(final Obligation obligation, final BigDecimal weekPositiveAmount);

  String getBonusCode();
}