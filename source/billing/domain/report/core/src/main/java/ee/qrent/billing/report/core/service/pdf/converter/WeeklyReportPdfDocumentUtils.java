package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.math.BigDecimal;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static java.math.BigDecimal.ZERO;

@UtilityClass
public class WeeklyReportPdfDocumentUtils {

  static final int REPORT_FONT = Font.HELVETICA;
  static final Color REPORT_GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  static final Color REPORT_WHITE_BACKGROUND_COLOR = new Color(255, 255, 255);
  static final Color REPORT_GREEN_COLOR = new Color(0, 100, 0);
  static final Color REPORT_RED_COLOR = new Color(150, 30, 0);
  static final Color REPORT_DARK_BLUE_COLOR = new Color(0, 40, 120);
  static final Color REPORT_DARK_GRAY_COLOR = new Color(50, 50, 50);
  static final Color REPORT_PURPLE_COLOR = new Color(128, 0, 128);

  static Document getA4PdfDocument() {
    return new Document(A4, 30f, 30f, 20f, 20f);
  }

  static PdfPTable getQpdfTable(int numColumns) {
    final var table = new PdfPTable(numColumns);
    table.setWidthPercentage(100f);

    return table;
  }

  static PdfPCell getQpdfPCell(final Paragraph paragraph) {
    final var qCell = new PdfPCell(paragraph);
    qCell.setBorder(NO_BORDER);

    return qCell;
  }

  static PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setFixedHeight((float) 8.0);

    return emptyRow;
  }

  static Chunk getNormalChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, Font.NORMAL));
  }

  static Chunk getBoldChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, Font.BOLD));
  }

  static void addRowIfValueIsNonZero(
      final BigDecimal value,
      final PdfPCell labelCell,
      final PdfPCell valueCell,
      final PdfPTable table) {
    if (value != null && value.compareTo(ZERO) != 0) {
      table.addCell(labelCell);
      table.addCell(valueCell);
    }
  }
}
