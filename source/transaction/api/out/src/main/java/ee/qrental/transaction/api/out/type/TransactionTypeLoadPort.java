package ee.qrental.transaction.api.out.type;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.type.TransactionType;
import java.util.List;

public interface TransactionTypeLoadPort extends LoadPort<TransactionType> {

  TransactionType loadByName(final String name);

  List<TransactionType> loadByNegative(final Boolean negative);
}
