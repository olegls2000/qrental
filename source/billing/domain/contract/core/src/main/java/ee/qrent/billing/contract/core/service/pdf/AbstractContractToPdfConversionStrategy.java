package ee.qrent.billing.contract.core.service.pdf;

import com.lowagie.text.*;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.Month;

import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.Font.TIMES_ROMAN;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.awt.Color.white;

public abstract class AbstractContractToPdfConversionStrategy
    implements ContractToPdfConversionStrategy {
  protected static final LocalDate NEW_CONTRACTS_START_DATE = LocalDate.of(2025, Month.JANUARY, 1);

  protected void addLhvChapterIfNecessary(
      final ContractPdfModel model, final Document pdfDocument) {
    if (model.getRenterLhvAccount() != null) {
      final var chapterLhv = getChapterTable();
      final var lhvAccount = model.getRenterLhvAccount();
      chapterLhv.addCell(
          getSubChapterText(
              "Lepingu allkirjastamisega kinnitab rentnik, et kavatseb rendileandja rendiautot kasutades teostada oma äritegevust ning on ta selleks loonud LHV ettevõtluskonto järgmise numbriga: "
                  + lhvAccount
                  + ". Eeltooduga kinnitab rentnik, et käesoleva rendilepingu vormistamise ja sellealuse auto rentimese eesmärgiks, on tema äritegevuse teostamine LHV ettevõtluskonto kaudu lihtsustatud eraettevõtluse registreerimise viisil."));
      pdfDocument.add(chapterLhv);
    }
  }

  protected static Cell getChapterNumber(final String chapterNumber) {
    final var chapterCell =
        new Cell(new Paragraph(chapterNumber + ".", new Font(TIMES_ROMAN, 9, BOLD)));
    chapterCell.setBorder(NO_BORDER);
    chapterCell.setHorizontalAlignment(LEFT);

    return chapterCell;
  }

  protected static Cell getSubChapterNumber(final String subChapterNumber) {
    final var subChapterCell =
        new Cell(new Paragraph(subChapterNumber, new Font(TIMES_ROMAN, 9, NORMAL)));
    subChapterCell.setBorder(NO_BORDER);
    subChapterCell.setHorizontalAlignment(LEFT);

    return subChapterCell;
  }

  protected static Cell getChapterSummary(final String chapterName) {
    final var chapterSummaryCell =
        new Cell(new Paragraph(chapterName, new Font(TIMES_ROMAN, 9, BOLD)));
    chapterSummaryCell.setBorder(NO_BORDER);
    chapterSummaryCell.setHorizontalAlignment(LEFT);

    return chapterSummaryCell;
  }

  @SneakyThrows
  protected static Table getHeaderTable(final ContractPdfModel model) {
    final var header = new Table(2);
    header.setWidths(new float[] {1, 7});
    header.setPadding(0f);
    header.setSpacing(0f);
    header.setWidth(100f);
    header.setBorderColor(white);
    header.setHorizontalAlignment(RIGHT);
    header.setBorder(NO_BORDER);

    final var logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
    logo.scaleAbsolute(50f, 20f);
    final var logoCell = new Cell(logo);
    logoCell.setRowspan(3);
    logoCell.setBorder(NO_BORDER);
    header.addCell(logoCell);

    final var headlineCell =
        new Cell(
            new Paragraph(
                "Rendileping  sõiduauto taksoteenuse ja majandustegevuse kasutamiseks   Nr. "
                    + model.getNumber(),
                new Font(TIMES_ROMAN, 10, BOLD)));
    headlineCell.setBorder(NO_BORDER);
    headlineCell.setHorizontalAlignment(CENTER);
    header.addCell(headlineCell);

    final var dateCell =
        new Cell(new Paragraph("Data : " + model.getCreated(), new Font(TIMES_ROMAN, 10, BOLD)));
    dateCell.setBorder(NO_BORDER);
    dateCell.setHorizontalAlignment(CENTER);
    header.addCell(dateCell);

    return header;
  }

  protected static Table getRenterTable(final ContractPdfModel model) {
    final var renterTable = new Table(1);
    renterTable.setPadding(0f);
    renterTable.setSpacing(0f);
    renterTable.setWidth(100f);
    renterTable.setBorderColor(white);
    renterTable.setHorizontalAlignment(LEFT);
    renterTable.setBorder(NO_BORDER);

    final var labelValue = "RENDILEANDJA ANDMED:";
    renterTable.addCell(getQCell(labelValue));
    final var qFirmNameValue = model.getQFirmName();
    renterTable.addCell(getQCell(qFirmNameValue));
    final var hereinafterLabel = "edaspidi Rendileandja.";
    renterTable.addCell(getQCellBold(hereinafterLabel));
    final var qFirmAddressValue = "Asukoht:  " + getTextOrEmpty(model.getQFirmPostAddress());
    renterTable.addCell(getQCell(qFirmAddressValue));
    final var qFirmRegNumberValue = "Registrinumber:  " + getTextOrEmpty(model.getQFirmRegNumber());
    renterTable.addCell(getQCell(qFirmRegNumberValue));
    final var qFirmVatNumberValue = "KMKR:  " + getTextOrEmpty(model.getQFirmVatNumber());
    renterTable.addCell(getQCell(qFirmVatNumberValue));
    final var qFirmIbanValue = "Pangakonto number:  " + getTextOrEmpty(model.getQFirmIban());
    renterTable.addCell(getQCell(qFirmIbanValue));
    final var qFirmEmailValue = "E-post:  " + getTextOrEmpty(model.getQFirmEmail());
    renterTable.addCell(getQCell(qFirmEmailValue));
    final var qFirmPhoneValue = "Kontakttelefon:  " + getTextOrEmpty(model.getQFirmPhone());
    renterTable.addCell(getQCell(qFirmPhoneValue));
    final var qFirmCeoValue =
        "Rendileandja esindaja:  "
            + getTextOrEmpty(model.getQFirmCeo() + " või volitatud isik Nadežda Tarraste");
    renterTable.addCell(getQCell(qFirmCeoValue));

    return renterTable;
  }

  protected static Table getTenantTable(final ContractPdfModel model) {
    final var tenantTable = new Table(1);
    tenantTable.setPadding(0f);
    tenantTable.setSpacing(0f);
    tenantTable.setWidth(100f);
    tenantTable.setBorderColor(white);
    tenantTable.setHorizontalAlignment(LEFT);
    tenantTable.setBorder(NO_BORDER);
    final var tenantDataLabel = "RENTNIKU ANDMED: ";
    tenantTable.addCell(getQCellBold(tenantDataLabel));
    final var tenantNameValue = "Rentniku nimi: " + getTextOrEmpty(model.getRenterName());
    tenantTable.addCell(getQCell(tenantNameValue));
    final var tenantRegNumberValue =
        "Rentniku reg. nr. või isikukood: " + getTextOrEmpty(model.getRenterRegistrationNumber());
    tenantTable.addCell(getQCell(tenantRegNumberValue));
    final var tenantAddressValue = "Rentniku aadress: " + getTextOrEmpty(model.getRenterAddress());
    tenantTable.addCell(getQCell(tenantAddressValue));
    final var tenantCeoNameValue =
        "Rentniku seadusliku või volitatud esindaja nimi:  "
            + getTextOrEmpty(model.getRenterCeoName());
    tenantTable.addCell(getQCell(tenantCeoNameValue));
    final var tenantCeoTaxNumberValue =
        "Rentniku seadusliku või volitatud esindaja isikukood:  " + model.getRenterCeoTaxNumber();
    tenantTable.addCell(getQCell(tenantCeoTaxNumberValue));
    final var tenantDriverLicenceNumberValue =
        "Rentniku või selle seadusliku ega volitatud esindaja juhiloa number:  "
            + getTextOrEmpty(model.getDriverLicenceNumber());
    tenantTable.addCell(getQCell(tenantDriverLicenceNumberValue));
    final var tenantDriverAddressValue =
        "Rentniku või selle seadusliku ega volitatud esindaja kontaktaadress:   "
            + getTextOrEmpty(model.getDriverAddress());
    tenantTable.addCell(getQCell(tenantDriverAddressValue));
    final var tenantPhoneValue =
        "Rentniku või selle seadusliku ega volitatud esindaja kontakttelefon:   "
            + getTextOrEmpty(model.getRenterPhone());
    tenantTable.addCell(getQCell(tenantPhoneValue));
    final var tenantEmailValue =
        "Rentniku või selle seadusliku ega volitatud esindaja e-post:   "
            + getTextOrEmpty(model.getRenterEmail());
    tenantTable.addCell(getQCell(tenantEmailValue));

    return tenantTable;
  }

  protected static Table getChapterTable() {
    final var chapter = new Table(2);
    chapter.setWidths(new float[] {1, 20});
    chapter.setPadding(0f);
    chapter.setSpacing(0f);
    chapter.setWidth(100f);
    chapter.setBorderColor(white);
    chapter.setHorizontalAlignment(LEFT);
    chapter.setBorder(NO_BORDER);

    return chapter;
  }

  protected static Cell getQCell(final String value) {
    final var cell = new Cell(new Paragraph(value, new Font(TIMES_ROMAN, 9, NORMAL)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(LEFT);

    return cell;
  }

  protected static Cell getSubChapterText(final String value) {
    final var cell = new Cell(new Paragraph(value, new Font(TIMES_ROMAN, 8, NORMAL)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(JUSTIFIED);

    return cell;
  }

  protected static Cell getQCellBold(final String value) {
    final var cell = new Cell(new Paragraph(value, new Font(TIMES_ROMAN, 10, BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(LEFT);

    return cell;
  }

  protected static String getTextOrEmpty(final String text) {
    if (text == null || text.isBlank()) {
      return "---";
    }
    return text;
  }
}
