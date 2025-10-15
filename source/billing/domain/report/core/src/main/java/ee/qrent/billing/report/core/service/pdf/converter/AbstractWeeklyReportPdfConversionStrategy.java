package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import lombok.SneakyThrows;

import java.awt.*;

import static com.lowagie.text.Element.ALIGN_LEFT;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.REPORT_NAME_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.getLabel;

abstract class AbstractWeeklyReportPdfConversionStrategy
    implements WeeklyReportPdfConversionStrategy {

  static final int REPORT_FONT = Font.HELVETICA;
  static final Color REPORT_GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  static final Color REPORT_WHITE_BACKGROUND_COLOR = new Color(255, 255, 255);
  static final Color REPORT_GREEN_COLOR = new Color(0, 100, 0); // Green
  static final Color REPORT_RED_COLOR = new Color(150, 30, 0); // Red
  static final Color REPORT_DARK_BLUE_COLOR = new Color(0, 40, 120); // Dark Blue
  static final Color REPORT_DARK_GRAY_COLOR = new Color(50, 50, 50); // Dark Gray
  static final Color REPORT_PURPLE_COLOR = new Color(128, 0, 128); // Purple

  Document getA4PdfDocument() {
    return new Document(A4, 30f, 30f, 20f, 20f);
  }

  @SneakyThrows
  PdfPTable getHeaderTable(final String language) {
    final var header = getQpdfTable(2);
    header.setWidths(new int[] {20, 80});

    com.lowagie.text.Image img = Image.getInstance("Images/qRentalGroup_gorznt.png");
    img.scaleToFit(33, 14);

    PdfPCell imageCell = new PdfPCell(img, true);
    imageCell.setHorizontalAlignment(ALIGN_LEFT);
    imageCell.setVerticalAlignment(ALIGN_MIDDLE);
    imageCell.setFixedHeight(20f);
    imageCell.setBorder(NO_BORDER);
    header.addCell(imageCell);
    final var reportNameCell =
        getQpdfPCell(
            new Paragraph(
                getLabel(language, REPORT_NAME_KEY), new Font(REPORT_FONT, 14, Font.BOLD)));

    reportNameCell.setHorizontalAlignment(ALIGN_LEFT);
    reportNameCell.setPaddingLeft(65f);
    reportNameCell.setVerticalAlignment(ALIGN_MIDDLE);
    header.addCell(reportNameCell);
    header.addCell(getEmptyRow());

    return header;
  }

  PdfPTable getQpdfTable(int numColumns) {
    final var table = new PdfPTable(numColumns);
    table.setWidthPercentage(100f);

    return table;
  }

  PdfPCell getQpdfPCell(final Paragraph paragraph) {
    final var qCell = new PdfPCell(paragraph);
    qCell.setBorder(NO_BORDER);

    return qCell;
  }

  PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setFixedHeight((float) 8.0);

    return emptyRow;
  }
}
