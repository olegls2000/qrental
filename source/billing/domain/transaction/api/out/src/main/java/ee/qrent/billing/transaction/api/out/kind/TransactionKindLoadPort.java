package ee.qrent.billing.transaction.api.out.kind;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.transaction.domain.kind.TransactionKind;

import java.util.List;

public interface TransactionKindLoadPort extends LoadPort<TransactionKind> {

  TransactionKind loadByCode(final String code);

  List<TransactionKind> loadAllByCodeIn(final List<String> codes);

  TransactionKind loadByName(final String name);
}
