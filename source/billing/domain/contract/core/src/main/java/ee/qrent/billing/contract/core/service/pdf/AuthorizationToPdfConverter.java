package ee.qrent.billing.contract.core.service.pdf;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static ee.qrent.common.utils.QTimeUtils.Q_DATE_FORMATTER;
import static java.awt.Color.white;
import static java.lang.String.format;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class AuthorizationToPdfConverter {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  private static String getTextOrEmpty(final String text) {
    if (text == null || text.isBlank()) {
      return "---";
    }
    return text;
  }

  @SneakyThrows
  public InputStream getPdfInputStream(final AuthorizationPdfModel model) {
    final var contractPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var contractPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(contractPdfDoc, contractPdfOutputStream);

    contractPdfDoc.open();

    //     final var header =
    //              getHeader(model.getNumber(), model.getCreated());

    /*       final var  =requisitesQfirm
            getRequisitesQfirm(
                    model.getQFirmName(),
                    model.getQFirmPostAddress(),
                    model.getQFirmRegNumber(),
                    model.getQFirmVatNumber(),
                    model.getQFirmIban(),
                    model.getQFirmEmail(),
                    model.getQFirmVatPhone(),
                    model.getQFirmCeo());
    /*

            final var requisitesRentnik =
                    getRequisitesRentnik(
                            model.getRenterName(),
                            model.getRenterRegistrationNumber(),
                            model.getRenterAddress(),
                            model.getRenterCeoName(),
                            model.getRenterCeoIsikukood(),
                            model.getDriverLicenceNumber(),
                            model.getDriverAddress(),
                            model.getRenterPhone(),
                            model.getRenterEmail()
                    );

            private Table getHeader(
            final String contractNumber,
            final String startDate,
            final String endDate) {
      */

    final var header1 = new Table(2);
    header1.setWidths(new float[] {1, 7});
    header1.setPadding(0f);
    header1.setSpacing(0f);
    header1.setWidth(100f);
    header1.setBorderColor(white);
    header1.setHorizontalAlignment(RIGHT);
    header1.setBorder(NO_BORDER);
    header1.setBorder(NO_BORDER);

    final var logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
    logo.scaleAbsolute(50f, 20f);
    final var cell = new Cell(logo);
    cell.setRowspan(3);
    cell.setBorder(NO_BORDER);
    header1.addCell(cell);

    final var cell1 =
        new Cell(new Paragraph("AUTHORIZATION", new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell1.setBorder(NO_BORDER);
    cell1.setHorizontalAlignment(CENTER);
    header1.addCell(cell1);

    contractPdfDoc.add(header1);

    final var rendileandja = new Table(1);
    rendileandja.setPadding(0f);
    rendileandja.setSpacing(0f);
    rendileandja.setWidth(100f);
    rendileandja.setBorderColor(white);
    rendileandja.setHorizontalAlignment(LEFT);
    rendileandja.setBorder(NO_BORDER);
    rendileandja.setBorder(NO_BORDER);

    final var rendileandjacell1 =
        new Cell(new Paragraph(" \n\n\n HEREBY I,", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    rendileandjacell1.setBorder(NO_BORDER);
    rendileandjacell1.setHorizontalAlignment(LEFT);
    rendileandja.addCell(rendileandjacell1);

    final var rendileandjacell2 =
        new Cell(
            new Paragraph(
                format(
                    "\n%s %s, isikukood: %d,",
                    model.getDriverFirstname(),
                    model.getDriverLastname(),
                    model.getDriverIsikukood()),
                new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    rendileandjacell2.setBorder(NO_BORDER);
    rendileandjacell2.setHorizontalAlignment(LEFT);
    rendileandja.addCell(rendileandjacell2);

    final var rendileandjacell3 =
        new Cell(
            new Paragraph(
                "\n EFFECTIVE ON THE DATE THIS DOCUMENT IS SIGNED, EXPRESSLY AUTHORISE "
                    + "THE FOLLOWING PERSONS: ",
                new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    rendileandjacell3.setBorder(NO_BORDER);
    rendileandjacell3.setHorizontalAlignment(LEFT);
    rendileandja.addCell(rendileandjacell3);

    contractPdfDoc.add(rendileandja);

    final var body1 = new Table(2);
    body1.setWidths(new float[] {1, 20});
    body1.setPadding(0f);
    body1.setSpacing(0f);
    body1.setWidth(100f);
    body1.setBorderColor(white);
    body1.setHorizontalAlignment(LEFT);
    body1.setBorder(NO_BORDER);
    body1.setBorder(NO_BORDER);

    final var body1cell1 = new Cell(new Paragraph("1.", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body1cell1.setBorder(NO_BORDER);
    body1cell1.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell1);

    final var body1cell2 =
        new Cell(new Paragraph("Bolt Operations OÜ ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body1cell2.setBorder(NO_BORDER);
    body1cell2.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell2);

    final var body1cell3 = new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell3.setBorder(NO_BORDER);
    body1cell3.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell3);

    final var body1cell4 =
        new Cell(
            new Paragraph(
                "a company incorporated in the Republic of Estonia with company"
                    + "number 14532901 and registered office at Vana-Lõuna 15, 10134 Tallinn, Estonia"
                    + "(including its Affiliates) (hereinafter jointly referred to as “Bolt”),"
                    + "‘Affiliates’ means an entity that owns or controls; is owned by; or is owned by an entity"
                    + "that owns or controls Bolt Operations OÜ. ",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body1cell4.setBorder(NO_BORDER);
    body1cell4.setHorizontalAlignment(JUSTIFIED);
    body1.addCell(body1cell4);

    final var body1cell5 = new Cell(new Paragraph("", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell5.setBorder(NO_BORDER);
    body1cell5.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell5);

    final var body1cell6 =
        new Cell(new Paragraph("CONSIDERING THAT: ", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell6.setBorder(NO_BORDER);
    body1cell6.setHorizontalAlignment(JUSTIFIED);
    body1.addCell(body1cell6);

    final var body1cell7 = new Cell(new Paragraph("●", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell7.setBorder(NO_BORDER);
    body1cell7.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell7);

    final var body1cell8 =
        new Cell(
            new Paragraph(
                "I have entered into a [car rental/credit] agreement with Q RENTAL GROUP OÜ a company"
                    + "incorporated in the Republic of Estonia with company number 16599536 and registered"
                    + "office at Lasnamäe tn 30a Lasnamäe linnaosa, Tallinn Harju maakond 11413 (hereinafter"
                    + "the “Company”);",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body1cell8.setBorder(NO_BORDER);
    body1cell8.setHorizontalAlignment(JUSTIFIED);
    body1.addCell(body1cell8);

    final var body1cell9 = new Cell(new Paragraph("●", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell9.setBorder(NO_BORDER);
    body1cell9.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell9);

    final var body1cell10 =
        new Cell(
            new Paragraph(
                "I have accepted the Terms and Conditions for Drivers and I am using the Bolt Platform to"
                    + "provide transportation services to passengers;",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body1cell10.setBorder(NO_BORDER);
    body1cell10.setHorizontalAlignment(JUSTIFIED);
    body1.addCell(body1cell10);

    final var body1cell11 =
        new Cell(new Paragraph("●", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body1cell11.setBorder(NO_BORDER);
    body1cell11.setHorizontalAlignment(LEFT);
    body1.addCell(body1cell11);

    final var body1cell12 =
        new Cell(
            new Paragraph(
                "I have undertaken under the terms of my agreement with the Company to authorise Bolt"
                    + "to deduct a specific portion of my weekly revenue generated through Bolt Platform and"
                    + "pay it directly to the Company; ",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body1cell12.setBorder(NO_BORDER);
    body1cell12.setHorizontalAlignment(JUSTIFIED);

    body1.addCell(body1cell12);

    contractPdfDoc.add(body1);

    final var body2 = new Table(2);
    body2.setWidths(new float[] {1, 20});
    body2.setPadding(0f);
    body2.setSpacing(0f);
    body2.setWidth(100f);
    body2.setBorderColor(white);
    body2.setHorizontalAlignment(LEFT);
    body2.setBorder(NO_BORDER);
    body2.setBorder(NO_BORDER);

    final var body2cell1 = new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body2cell1.setBorder(NO_BORDER);
    body2cell1.setHorizontalAlignment(LEFT);
    body2.addCell(body2cell1);

    final var body2cell2 =
        new Cell(
            new Paragraph(
                "TO PERFORM THE FOLLOWING ACTIONS:", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body2cell2.setBorder(NO_BORDER);
    body2cell2.setHorizontalAlignment(LEFT);
    body2.addCell(body2cell2);

    final var body2cell3 = new Cell(new Paragraph("A.", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body2cell3.setBorder(NO_BORDER);
    body2cell3.setHorizontalAlignment(LEFT);
    body2.addCell(body2cell3);

    final var body2cell4 =
        new Cell(
            new Paragraph(
                "to deduct on a weekly basis my debt owed to the Company from my revenue earned"
                    + "through the Bolt Platform, if I have not disputed the debt owed to the Company for the"
                    + "car rental services 24 hours prior to the deduction where the deduction will be made on"
                    + "the same date as the weekly payouts to my account.",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body2cell4.setBorder(NO_BORDER);
    body2cell4.setHorizontalAlignment(JUSTIFIED);
    body2.addCell(body2cell4);

    final var body2cell5 = new Cell(new Paragraph("B.", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body2cell5.setBorder(NO_BORDER);
    body2cell5.setHorizontalAlignment(LEFT);
    body2.addCell(body2cell5);

    final var body2cell6 =
        new Cell(
            new Paragraph(
                "release to the Company on a weekly basis a file with the following personal details:",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body2cell6.setBorder(NO_BORDER);
    body2cell6.setHorizontalAlignment(JUSTIFIED);
    body2.addCell(body2cell6);

    contractPdfDoc.add(body2);

    final var body3 = new Table(2);
    body3.setWidths(new float[] {1, 20});
    body3.setPadding(0f);
    body3.setSpacing(0f);
    body3.setWidth(100f);
    body3.setBorderColor(white);
    body3.setHorizontalAlignment(LEFT);
    body3.setBorder(NO_BORDER);
    body3.setBorder(NO_BORDER);

    final var body3cell1 =
        new Cell(new Paragraph("a.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell1.setBorder(NO_BORDER);
    body3cell1.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell1);

    final var body3cell2 =
        new Cell(new Paragraph(" Bolt UUID; ", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell2.setBorder(NO_BORDER);
    body3cell2.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell2);

    final var body3cell3 =
        new Cell(new Paragraph("b.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell3.setBorder(NO_BORDER);
    body3cell3.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell3);

    final var body3cell4 =
        new Cell(new Paragraph("Name and surname;", new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell4.setBorder(NO_BORDER);
    body3cell4.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell4);

    final var body3cell5 =
        new Cell(new Paragraph("c.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell5.setBorder(NO_BORDER);
    body3cell5.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell5);

    final var body3cell6 =
        new Cell(new Paragraph("Phone number;", new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell6.setBorder(NO_BORDER);
    body3cell6.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell6);

    final var body3cell7 =
        new Cell(new Paragraph("d.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell7.setBorder(NO_BORDER);
    body3cell7.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell7);

    final var body3cell8 =
        new Cell(
            new Paragraph(
                "Transaction details, including reason for the failed transactions.",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell8.setBorder(NO_BORDER);
    body3cell8.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell8);

    final var body3cell9 = new Cell(new Paragraph("C.", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body3cell9.setBorder(NO_BORDER);
    body3cell9.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell9);

    final var body3cell10 =
        new Cell(
            new Paragraph(
                "upon request, provide access to the Company to personal details regarding my use of"
                    + "Bolt Services such as:",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell10.setBorder(NO_BORDER);
    body3cell10.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell10);

    final var body3cell11 =
        new Cell(new Paragraph("a.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell11.setBorder(NO_BORDER);
    body3cell11.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell11);

    final var body3cell12 =
        new Cell(
            new Paragraph(
                "my revenue earned via the Bolt Platform;",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell12.setBorder(NO_BORDER);
    body3cell12.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell12);

    final var body3cell13 =
        new Cell(new Paragraph("b.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell13.setBorder(NO_BORDER);
    body3cell13.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell13);

    final var body3cell14 =
        new Cell(
            new Paragraph(
                "my location during the provision of transportation services via the Bolt Platform;",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell14.setBorder(NO_BORDER);
    body3cell14.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell14);

    final var body3cell15 =
        new Cell(new Paragraph("c.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell15.setBorder(NO_BORDER);
    body3cell15.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell15);

    final var body3cell16 =
        new Cell(
            new Paragraph(
                "invoices issued to the customers on my behalf;",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell16.setBorder(NO_BORDER);
    body3cell16.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell16);

    final var body3cell17 =
        new Cell(new Paragraph("d.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell17.setBorder(NO_BORDER);
    body3cell17.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell17);

    final var body3cell18 =
        new Cell(
            new Paragraph("my Bolt profile details;", new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell18.setBorder(NO_BORDER);
    body3cell18.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell18);

    final var body3cell19 =
        new Cell(new Paragraph("e.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell19.setBorder(NO_BORDER);
    body3cell19.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell19);

    final var body3cell20 =
        new Cell(
            new Paragraph(
                "statistics of my trips made via Bolt Platform, including driver score, percentage of"
                    + "completed trips, acceptance rate, time spent on the Bolt Platform;",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell20.setBorder(NO_BORDER);
    body3cell20.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell20);

    final var body3cell21 =
        new Cell(new Paragraph("f.", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body3cell21.setBorder(NO_BORDER);
    body3cell21.setHorizontalAlignment(LEFT);
    body3.addCell(body3cell21);

    final var body3cell22 =
        new Cell(
            new Paragraph(
                "invoices issued to me by Bolt for the use of the Bolt platform.",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body3cell22.setBorder(NO_BORDER);
    body3cell22.setHorizontalAlignment(JUSTIFIED);
    body3.addCell(body3cell22);

    contractPdfDoc.add(body3);

    final var body4 = new Table(2);
    body4.setWidths(new float[] {1, 20});
    body4.setPadding(0f);
    body4.setSpacing(0f);
    body4.setWidth(100f);
    body4.setBorderColor(white);
    body4.setHorizontalAlignment(LEFT);
    body4.setBorder(NO_BORDER);
    body4.setBorder(NO_BORDER);

    final var body4cell1 = new Cell(new Paragraph("", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body4cell1.setBorder(NO_BORDER);
    body4cell1.setHorizontalAlignment(LEFT);
    body4.addCell(body4cell1);

    final var body4cell2 =
        new Cell(
            new Paragraph(
                " Once I revoke this authorisation, Bolt will  ",
                new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body4cell2.setBorder(NO_BORDER);
    body4cell2.setHorizontalAlignment(LEFT);
    body4.addCell(body4cell2);

    final var body4cell3 =
        new Cell(new Paragraph("(i)", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body4cell3.setBorder(NO_BORDER);
    body4cell3.setHorizontalAlignment(LEFT);
    body4.addCell(body4cell3);

    final var body4cell4 =
        new Cell(
            new Paragraph(
                "stop making any deductions from my revenue earned through the Bolt Platform in favor of the Company and ",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body4cell4.setBorder(NO_BORDER);
    body4cell4.setHorizontalAlignment(JUSTIFIED);
    body4.addCell(body4cell4);

    final var body4cell5 =
        new Cell(new Paragraph("(ii)", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body4cell5.setBorder(NO_BORDER);
    body4cell5.setHorizontalAlignment(LEFT);
    body4.addCell(body4cell5);

    final var body4cell6 =
        new Cell(
            new Paragraph(
                "stop releasing my personal details to the Company. Nevertheless, revoking"
                    + "this authorisation does not terminate my agreement with the Company.",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body4cell6.setBorder(NO_BORDER);
    body4cell6.setHorizontalAlignment(JUSTIFIED);
    body4.addCell(body4cell6);

    contractPdfDoc.add(body4);

    final var body5 = new Table(2);
    body5.setWidths(new float[] {1, 20});
    body5.setPadding(0f);
    body5.setSpacing(0f);
    body5.setWidth(100f);
    body5.setBorderColor(white);
    body5.setHorizontalAlignment(LEFT);
    body5.setBorder(NO_BORDER);
    body5.setBorder(NO_BORDER);

    final var body5cell1 = new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body5cell1.setBorder(NO_BORDER);
    body5cell1.setHorizontalAlignment(LEFT);
    body5.addCell(body5cell1);

    final var body5cell2 =
        new Cell(
            new Paragraph(
                "By signing this authorisation, I confirm and understand that I am solely responsible for"
                    + "the payment of the debt owed to the Company under my agreement with the Company. ",
                new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    body5cell2.setBorder(NO_BORDER);
    body5cell2.setHorizontalAlignment(LEFT);
    body5.addCell(body5cell2);

    final var body5cell3 = new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    body5cell3.setBorder(NO_BORDER);
    body5cell3.setHorizontalAlignment(LEFT);
    body5.addCell(body5cell3);

    final var body5cell4 =
        new Cell(
            new Paragraph(
                "Bolt is not liable for the debt owed by me to the Company or any other obligations I have"
                    + "undertaken under the agreement with the Company. If I have any claims or conflicts between"
                    + "me and the Company, I undertake to solve them exclusively with the Company without Bolt’s"
                    + "involvement.",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    body5cell4.setBorder(NO_BORDER);
    body5cell4.setHorizontalAlignment(JUSTIFIED);
    body5.addCell(body5cell4);

    final var signature = new Table(2);
    signature.setWidths(new float[] {1, 10});
    signature.setPadding(0f);
    signature.setSpacing(0f);
    signature.setWidth(100f);
    signature.setBorderColor(white);
    signature.setHorizontalAlignment(LEFT);
    signature.setBorder(NO_BORDER);
    signature.setBorder(NO_BORDER);

    final var signaturecell3 =
        new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell3.setBorder(NO_BORDER);
    signaturecell3.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell3);

    final var signaturecell4 =
        new Cell(
            new Paragraph(
                " \n ___________________________________________________________________________________________  ",
                new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell4.setBorder(NO_BORDER);
    signaturecell4.setHorizontalAlignment(JUSTIFIED);
    signature.addCell(signaturecell4);

    final var signaturecell5 =
        new Cell(new Paragraph("  ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell5.setBorder(NO_BORDER);
    signaturecell5.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell5);

    final var signaturecell6 =
        new Cell(
            new Paragraph(
                " Driver’s name and surname (and if applicable, company name)",
                new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
    // signaturecell6.setColspan(2);
    signaturecell6.setBorder(NO_BORDER);
    signaturecell6.setHorizontalAlignment(JUSTIFIED);
    signature.addCell(signaturecell6);

    final var signaturecell7 =
        new Cell(new Paragraph("  ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell7.setBorder(NO_BORDER);
    signaturecell7.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell7);
    final var createdDateString = Q_DATE_FORMATTER.format(model.getCreated());
    final var signaturecell8 =
        new Cell(
            new Paragraph(
                "__________________________________________________________________________________        "
                    + createdDateString,
                new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell8.setBorder(NO_BORDER);
    signaturecell8.setHorizontalAlignment(JUSTIFIED);
    signature.addCell(signaturecell8);

    final var signaturecell9 =
        new Cell(new Paragraph("  ", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
    signaturecell9.setBorder(NO_BORDER);
    signaturecell9.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell9);

    final var signaturecell10 =
        new Cell(
            new Paragraph(
                "Signature                                                                               Date                     ",
                new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
    // signaturecell10.setColspan(2);
    signaturecell10.setBorder(NO_BORDER);
    signaturecell10.setHorizontalAlignment(RIGHT);
    signature.addCell(signaturecell10);

    contractPdfDoc.add(signature);

    contractPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(contractPdfOutputStream.toByteArray());
  }

  /*
  @SneakyThrows
  private Table getHeader(
          final String number,
          final String created) {
      final var header = new Table(2);
      header.setWidths(new float[]{1, 7});
      header.setPadding(0f);
      header.setSpacing(0f);
      header.setWidth(100f);
      header.setBorderColor(white);
      header.setHorizontalAlignment(RIGHT);
      header.setBorder(NO_BORDER);
      header.setBorder(NO_BORDER);

      final var logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
      logo.scaleAbsolute(50f, 20f);
      final var cell = new Cell(logo);
      cell.setRowspan(3);
      cell.setBorder(NO_BORDER);
      header.addCell(cell);

      final var cell1 =
              new Cell(
                      new Paragraph("KOOSTÖÖLEPING   Nr. " + number, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
      cell1.setBorder(NO_BORDER);
      cell1.setHorizontalAlignment(CENTER);
      header.addCell(cell1);

      final var cell2 =
              new Cell(new Paragraph("Data : " + created, new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
      cell2.setBorder(NO_BORDER);
      cell2.setHorizontalAlignment(CENTER);
      header.addCell(cell2);

      final var cell3 =
              new Cell(new Paragraph(format("Koostöölepingu pooled on RENDILEANDJA ja RENTNIK."), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
      cell3.setBorder(NO_BORDER);
      cell3.setHorizontalAlignment(CENTER);
      header.addCell(cell3);

      return header;
  }
  */

}
