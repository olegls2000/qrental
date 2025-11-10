package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.lowagie.text.PageSize.A4;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.*;

public class WeeklyReportPdfConversionStrategyFriday
    extends AbstractWeeklyReportPdfConversionStrategy {

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.FRIDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var weeklyReportPdfDoc = new Document(A4, 30f, 30f, 20f, 20f);
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(getHeaderTable(getLabelFromFriday(language, REPORT_NAME_KEY)));
    weeklyReportPdfDoc.add(getDriverMainDataTable(model));
    weeklyReportPdfDoc.add(
        getClarificationHeaderRow(language));
    weeklyReportPdfDoc.add(getPreviousWeekObligationStatusText(model));
    weeklyReportPdfDoc.add(gePreviousThursdayNetInfoText(model));
    weeklyReportPdfDoc.add(
        getClarificationHeaderRowColored(
            getLabelFromCommon(language, OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY)));
    weeklyReportPdfDoc.add(getRentClarificationTable(model));
    weeklyReportPdfDoc.add(getRentAdjustmentClarificationTable(model));
    weeklyReportPdfDoc.add(getOtherPaymentClarificationTable(model));
    weeklyReportPdfDoc.add(getDemandOnTheBeginningOfWeek(model));
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRowTable(language));
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }
}
