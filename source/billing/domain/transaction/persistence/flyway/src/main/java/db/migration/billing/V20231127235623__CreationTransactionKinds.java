package db.migration.billing;

import static java.lang.String.format;

import ee.qrent.billing.transaction.domain.kind.TransactionKindsCode;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V20231127235623__CreationTransactionKinds extends BaseJavaMigration {
  @Override
  public void migrate(final Context context) throws Exception {
    try (var statement = context.getConnection().createStatement()) {
      for (final TransactionKindsCode code : TransactionKindsCode.values()) {
        final var insertTransactionKindSql = getInsertSql(code);
        final var result = statement.executeUpdate(insertTransactionKindSql);
        if (result > 0) {
          System.out.println(insertTransactionKindSql + " -> successfully inserted");
        } else {
          System.out.println(insertTransactionKindSql + " -> unsuccessfully executed");
        }
      }
    }
  }

  private String getInsertSql(final TransactionKindsCode kind) {
    return format(
        "insert into transaction_kind (code, name, comment) values ('%s', '%s', '%s')",
        kind.name(), kind.getName(), "Initially created Transaction Kind");
  }
}
