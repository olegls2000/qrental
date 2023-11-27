package ee.qrental.transaction.api.out.kind;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.kind.TransactionKind;

public interface TransactionKindLoadPort extends LoadPort<TransactionKind> {

  TransactionKind loadByCode(final String code);

  TransactionKind loadByName(final String name);
}
