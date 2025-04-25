package db.migration.billing;

import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;

import java.sql.Date;
import java.time.LocalDate;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V20240302220637__UpdateInvoiceCalculationByQWeekId extends BaseJavaMigration {

  @Override
  public void migrate(final Context context) throws Exception {
    final var selectInvoiceCalculationsQuery =
        "select ic.id, ic.start_date from invoice_calculation ic";
    try (var statementSelectInvoiceCalculation = context.getConnection().createStatement()) {
      final var resultSetInvoiceCalculation =
          statementSelectInvoiceCalculation.executeQuery(selectInvoiceCalculationsQuery);
      while (resultSetInvoiceCalculation.next()) {
        final var id = resultSetInvoiceCalculation.getLong("id");
        final var startDateSql = resultSetInvoiceCalculation.getDate("start_date");
        final var startDate = startDateSql.toLocalDate();
        final var weekNumber = getWeekNumber(startDate);
        final var weekYear = startDate.getYear();
        try (final var statementSelectQWeekId =
            context
                .getConnection()
                .prepareStatement("select qw.id from q_week qw where year = ? and number = ?")) {
          statementSelectQWeekId.setInt(1, weekYear);
          statementSelectQWeekId.setInt(2, weekNumber);
          final var resultSetQWeekId = statementSelectQWeekId.executeQuery();
          resultSetQWeekId.next();
          final var qWekId = resultSetQWeekId.getLong("id");
          try (final var statementForUpdate =
              context
                  .getConnection()
                  .prepareStatement(
                      format(
                          "UPDATE invoice_calculation SET q_week_id = %d WHERE id = %d",
                          qWekId, id))) {
            final var count = statementForUpdate.executeUpdate();
            System.out.println("Were updated " + count + " invoice calculation row with id: " + id);
          }
        }
      }
    }
  }

  public LocalDate convertToLocalDate(final Date dateToConvert) {
    return dateToConvert.toLocalDate();
  }

  private String getUpdateSql(final Long qWeekId, final Long invoiceCalculationId) {
    return format(
        "UPDATE invoice_calculation SET q_week_id = %d WHERE id = %d",
        qWeekId, invoiceCalculationId);
  }
}
