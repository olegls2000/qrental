package ee.qrental.contract.core.service.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static java.awt.Color.white;
import static java.lang.String.format;

@AllArgsConstructor
public class ContractLisa1ToPdfConverter {

    private static String getTextOrEmpty(final String text) {
        if (text == null || text.isBlank()) {
            return "---";
        }
        return text;
    }

    @SneakyThrows
    public InputStream getPdfInputStream(final ContractPdfModel model) {
        final var contractPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
        final var contractPdfOutputStream = new ByteArrayOutputStream();
        final var writer = PdfWriter.getInstance(contractPdfDoc, contractPdfOutputStream);

        contractPdfDoc.open();


        final var header1 = new Table(2);
        header1.setWidths(new float[]{1, 7});
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
        cell.setRowspan(2);
        cell.setBorder(NO_BORDER);
        header1.addCell(cell);

        final var cell1 =
                new Cell(
                        new Paragraph("Seade omastamise õiguse Koostöölepingu " + model.getNumber() + "   lisa 1 ", new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
        cell1.setBorder(NO_BORDER);
        cell1.setHorizontalAlignment(CENTER);
        header1.addCell(cell1);

        final var cell2 =
                new Cell(new Paragraph("\n ________________________________________________________________________ ",
                        new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
        cell2.setBorder(NO_BORDER);
        cell2.setHorizontalAlignment(CENTER);
        header1.addCell(cell2);


        final var cell3 =
                new Cell(new Paragraph(format("Pooled : "), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        cell3.setBorder(NO_BORDER);
        cell3.setHorizontalAlignment(LEFT);
        header1.addCell(cell3);

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
                new Cell(new Paragraph("RENDILEANDJA ANDMED: ", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        rendileandjacell1.setBorder(NO_BORDER);
        rendileandjacell1.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell1);

        final var rendileandjacell2 =
                new Cell(new Paragraph(model.getQFirmName(), new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        rendileandjacell2.setBorder(NO_BORDER);
        rendileandjacell2.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell2);

        final var rendileandjacell3 =
                new Cell(new Paragraph("edaspidi Rendileandja.", new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        rendileandjacell3.setBorder(NO_BORDER);
        rendileandjacell3.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell3);

        final var rendileandjacell4 =
                new Cell(new Paragraph("Asukoht:" + getTextOrEmpty(model.getQFirmPostAddress()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell4.setBorder(NO_BORDER);
        rendileandjacell4.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell4);

        final var rendileandjacell5 =
                new Cell(new Paragraph("Registrinumber: " + getTextOrEmpty(model.getQFirmRegNumber()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell5.setBorder(NO_BORDER);
        rendileandjacell5.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell5);

        final var rendileandjacell6 =
                new Cell(new Paragraph("KMKR: " + getTextOrEmpty(model.getQFirmVatNumber()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell6.setBorder(NO_BORDER);
        rendileandjacell6.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell6);

        final var rendileandjacell7 =
                new Cell(new Paragraph("Pangakonto number:  " + getTextOrEmpty(model.getQFirmIban()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell7.setBorder(NO_BORDER);
        rendileandjacell7.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell7);

        final var rendileandjacell8 =
                new Cell(new Paragraph(" E-post:  " + getTextOrEmpty(model.getQFirmEmail()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell8.setBorder(NO_BORDER);
        rendileandjacell8.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell8);

        final var rendileandjacell9 =
                new Cell(new Paragraph(" Kontakttelefon:  " + getTextOrEmpty(model.getQFirmVatPhone()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell9.setBorder(NO_BORDER);
        rendileandjacell9.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell9);

        final var rendileandjacell10 =
                new Cell(new Paragraph(" keda esindab juhatuse liige  " + getTextOrEmpty(model.getQFirmCeo()) + "  i.k. 38701060283, või Nadežda Tarraste i.k. 48211107018, ja ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell10.setBorder(NO_BORDER);
        rendileandjacell10.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell10);

        contractPdfDoc.add(rendileandja);


        final var rentnik = new Table(1);
        rentnik.setPadding(0f);
        rentnik.setSpacing(0f);
        rentnik.setWidth(100f);
        rentnik.setBorderColor(white);
        rentnik.setHorizontalAlignment(LEFT);
        rentnik.setBorder(NO_BORDER);
        rentnik.setBorder(NO_BORDER);

        final var rentnikcell1 =
                new Cell(new Paragraph(" ", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        rentnikcell1.setBorder(NO_BORDER);
        rentnikcell1.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell1);

        final var rentnikcell2 =
                new Cell(new Paragraph("Rentniku nimi: " + getTextOrEmpty(model.getRenterName()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell2.setBorder(NO_BORDER);
        rentnikcell2.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell2);


        final var rentnikcell3 =
                new Cell(new Paragraph("Rentniku reg. nr. või isikukood: " + getTextOrEmpty(model.getRenterRegistrationNumber()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell3.setBorder(NO_BORDER);
        rentnikcell3.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell3);

        final var rentnikcell4 =
                new Cell(new Paragraph("Rentniku aadress:  " + getTextOrEmpty(model.getRenterAddress()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell4.setBorder(NO_BORDER);
        rentnikcell4.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell4);

        final var rentnikcell5 =
                new Cell(new Paragraph("Rentniku juhatuse liige või seadusliku esindaja nimi: " + getTextOrEmpty(model.getRenterCeoName()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell5.setBorder(NO_BORDER);
        rentnikcell5.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell5);

        final var rentnikcell6 =
                new Cell(new Paragraph("Rentniku juhatuse liige või seadusliku esindaja isikukood: " + model.getRenterCeoIsikukood(),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell6.setBorder(NO_BORDER);
        rentnikcell6.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell6);

        final var rentnikcell7 =
                new Cell(new Paragraph("Rentniku juhatuse liige või seadusliku esindaja juhiloa number:  " + getTextOrEmpty(model.getDriverLicenceNumber()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell7.setBorder(NO_BORDER);
        rentnikcell7.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell7);

        final var rentnikcell8 =
                new Cell(new Paragraph("Rentniku juhatuse liige või seadusliku esindaja elukoht:  " + getTextOrEmpty(model.getDriverAddress()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell8.setBorder(NO_BORDER);
        rentnikcell8.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell8);

        final var rentnikcell9 =
                new Cell(new Paragraph("Kontakttelefon:  " + getTextOrEmpty(model.getRenterPhone()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell9.setBorder(NO_BORDER);
        rentnikcell9.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell9);

        final var rentnikcell10 =
                new Cell(new Paragraph("E-post:   " + getTextOrEmpty(model.getRenterEmail()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell10.setBorder(NO_BORDER);
        rentnikcell10.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell10);

        final var rentnikcell11 =
                new Cell(new Paragraph("edaspidi Rentnik." + getTextOrEmpty(model.getRenterEmail()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell11.setBorder(NO_BORDER);
        rentnikcell11.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell11);

        final var rentnikcell12 =
                new Cell(new Paragraph(" Pooled, sõlmisid käesoleva rendilepingu, edaspidi Leping, alljärgnevas: ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell12.setBorder(NO_BORDER);
        rentnikcell12.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell12);

        contractPdfDoc.add(rentnik);


        final var body1 = new Table(2);
        body1.setWidths(new float[]{1, 20});
        body1.setPadding(0f);
        body1.setSpacing(0f);
        body1.setWidth(100f);
        body1.setBorderColor(white);
        body1.setHorizontalAlignment(LEFT);
        body1.setBorder(NO_BORDER);
        body1.setBorder(NO_BORDER);

        final var body1cell1 =
                new Cell(new Paragraph("I.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body1cell1.setBorder(NO_BORDER);
        body1cell1.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell1);

        final var body1cell2 =
                new Cell(new Paragraph("ÜLDOSA",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body1cell2.setBorder(NO_BORDER);
        body1cell2.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell2);

        final var body1cell3 =
                new Cell(new Paragraph("I.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell3.setBorder(NO_BORDER);
        body1cell3.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell3);

        final var body1cell4 =
                new Cell(new Paragraph("Lepinguga kohustub Rendileandja andma Rentnikule õiguse kasutada sõiduauto (Seade) " +
                        "taksoteenuste osutamiseks ning Rentnik kohustub selle eest maksma Rendileandjale tasu, edaspidi Rent. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell4.setBorder(NO_BORDER);
        body1cell4.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell4);

        final var body1cell5 =
                new Cell(new Paragraph("I.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell5.setBorder(NO_BORDER);
        body1cell5.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell5);

        final var body1cell6 =
                new Cell(new Paragraph("Rendileandja kinnitab, et talle kuulub õigus Seadet rentida ning et kellelgi teisel seda õigust ei ole." +
                        " Samuti kinnitab Rendileandja, et Seade ei ole samaaegselt renditud välja ühelegi teisele isikule.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell6.setBorder(NO_BORDER);
        body1cell6.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell6);

        final var body1cell7 =
                new Cell(new Paragraph("I.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell7.setBorder(NO_BORDER);
        body1cell7.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell7);

        final var body1cell8 =
                new Cell(new Paragraph("Käesolevale lepingule kohaldatakse VÕS rendilepingu sätestatud. " +
                        "Kõik käesolevas lepingus erikokkulepped poolte vahel on läbiräägitud, arusaadavad ja täitmisele kohustuslikud. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell8.setBorder(NO_BORDER);
        body1cell8.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell8);

        final var body1cell9 =
                new Cell(new Paragraph("I.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell9.setBorder(NO_BORDER);
        body1cell9.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell9);

        final var body1cell10 =
                new Cell(new Paragraph("Käesoleva lepingu aluseks on sõlmitud koostööleping nr." + model.getNumber() + "," +
                        " mis on käesoleva lepingu aluseks ning küsimused, mis on jäetud reguleerimata ja/või mainimata käesoleva " +
                        "lepinguga tuleb reguleerida eelnevalt käesolevas punktis nimetatud koostöölepinguga. " +
                        "Käesolev leping on Koostöölepingu lisa, millega reguleeritakse Rentniku ja Rendileandja kokkulepped," +
                        " kasutuskord, summad, trahvid ning kõik muud mis on seotud auto kasutamisega, mille tulemusel rentnik saab renditava auto enda omandisse. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell10.setBorder(NO_BORDER);
        body1cell10.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell10);

        contractPdfDoc.add(body1);


        final var body2 = new Table(2);
        body2.setWidths(new float[]{1, 20});
        body2.setPadding(0f);
        body2.setSpacing(0f);
        body2.setWidth(100f);
        body2.setBorderColor(white);
        body2.setHorizontalAlignment(LEFT);
        body2.setBorder(NO_BORDER);
        body2.setBorder(NO_BORDER);

        final var body2cell1 =
                new Cell(new Paragraph("II.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body2cell1.setBorder(NO_BORDER);
        body2cell1.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell1);

        final var body2cell2 =
                new Cell(new Paragraph("Objekt ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body2cell2.setBorder(NO_BORDER);
        body2cell2.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell2);

        final var body2cell3 =
                new Cell(new Paragraph("II.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell3.setBorder(NO_BORDER);
        body2cell3.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell3);

        final var body2cell4 =
                new Cell(new Paragraph("Lepingu objektiks on allolevate andmetega sõiduauto, edaspidi Seade:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell4.setBorder(NO_BORDER);
        body2cell4.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell4);

        final var body2cell5 =
                new Cell(new Paragraph(" ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell5.setBorder(NO_BORDER);
        body2cell5.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell5);

        final var body2cell6 =
                new Cell(new Paragraph("VIN kood: TMBAH8NX6PY017012 ",
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body2cell6.setBorder(NO_BORDER);
        body2cell6.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell6);

        final var body2cell7 =
                new Cell(new Paragraph(" ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell7.setBorder(NO_BORDER);
        body2cell7.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell7);

        final var body2cell8 =
                new Cell(new Paragraph("Märk / Muudel: SKODA OCTAVIA ",
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body2cell8.setBorder(NO_BORDER);
        body2cell8.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell8);


        final var body2cell9 =
                new Cell(new Paragraph("II.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell9.setBorder(NO_BORDER);
        body2cell9.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell9);

        final var body2cell10 =
                new Cell(new Paragraph("Seadme sihtotstarbeks on takso ja sõiduteenuste osutamine.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell10.setBorder(NO_BORDER);
        body2cell10.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell10);

        final var body2cell11 =
                new Cell(new Paragraph("II.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell11.setBorder(NO_BORDER);
        body2cell11.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell11);

        final var body2cell12 =
                new Cell(new Paragraph("Rendileandja annab avalduse Rentnikule üle Seadme üleandmise-vastuvõtmise aktiga. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell12.setBorder(NO_BORDER);
        body2cell12.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell12);

        contractPdfDoc.add(body2);


        final var body3 = new Table(2);
        body3.setWidths(new float[]{1, 20});
        body3.setPadding(0f);
        body3.setSpacing(0f);
        body3.setWidth(100f);
        body3.setBorderColor(white);
        body3.setHorizontalAlignment(LEFT);
        body3.setBorder(NO_BORDER);
        body3.setBorder(NO_BORDER);

        final var body3cell1 =
                new Cell(new Paragraph("III.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body3cell1.setBorder(NO_BORDER);
        body3cell1.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell1);

        final var body3cell2 =
                new Cell(new Paragraph(" Lepingu tähtaeg ja Seade rendihind  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body3cell2.setBorder(NO_BORDER);
        body3cell2.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell2);

        final var body3cell3 =
                new Cell(new Paragraph("III.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell3.setBorder(NO_BORDER);
        body3cell3.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell3);

        final var body3cell4 =
                new Cell(new Paragraph("Leping on sõlmitud tähtajaga 5 (viis) aastat, ehk 60 kuud.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell4.setBorder(NO_BORDER);
        body3cell4.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell4);

        final var body3cell5 =
                new Cell(new Paragraph("III.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell5.setBorder(NO_BORDER);
        body3cell5.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell5);

        final var body3cell6 =
                new Cell(new Paragraph("Lepingu alguskuupäev on " + model.getCreated() + " , ja lepingu kehtivuse viimaseks kuupäevaks on  2028-11-20 " + " .",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell6.setBorder(NO_BORDER);
        body3cell6.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell6);

        final var body3cell7 =
                new Cell(new Paragraph("III.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell7.setBorder(NO_BORDER);
        body3cell7.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell7);

        final var body3cell8 =
                new Cell(new Paragraph("Rentnik maksab Rendileandjale Seadme kasutamise eest renti 240 EUR ühe nädala eest." +
                        "  (nädalaks loetakse 7 päevane nädal Esmaspäevast-Pühapäevani).",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell8.setBorder(NO_BORDER);
        body3cell8.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell8);

        final var body3cell9 =
                new Cell(new Paragraph("III.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell9.setBorder(NO_BORDER);
        body3cell9.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell9);

        final var body3cell10 =
                new Cell(new Paragraph("Üür sisaldab Seadme renti, liikluskindlustus ning vajalikud hooldustööd (kohustuslik iga 15 000 hooldus," +
                        " remont mida on tulnud igapäevasest ja tavapärasest Seadme kasutusest)",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell10.setBorder(NO_BORDER);
        body3cell10.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell10);

        final var body3cell11 =
                new Cell(new Paragraph("III.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell11.setBorder(NO_BORDER);
        body3cell11.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell11);

        final var body3cell12 =
                new Cell(new Paragraph("Üür makstakse iga nädal hiljemalt iga nädala teisipäevaks ja iga nädala ette. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell12.setBorder(NO_BORDER);
        body3cell12.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell12);

        final var body3cell13 =
                new Cell(new Paragraph("III.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell13.setBorder(NO_BORDER);
        body3cell13.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell13);

        final var body3cell14 =
                new Cell(new Paragraph("Rendileandjal on õigus muuta rendi suurust üks kord aastas. Rendileandjal on õigus tõsta rendihinda kuni 5 % aastas",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell14.setBorder(NO_BORDER);
        body3cell14.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell14);

        final var body3cell15 =
                new Cell(new Paragraph("III.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell15.setBorder(NO_BORDER);
        body3cell15.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell15);

        final var body3cell16 =
                new Cell(new Paragraph("Rentnik tasub rendileandjale järgmisel viisil:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell16.setBorder(NO_BORDER);
        body3cell16.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell16);

        final var body3cell17 =
                new Cell(new Paragraph("III.7.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell17.setBorder(NO_BORDER);
        body3cell17.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell17);

        final var body3cell18 =
                new Cell(new Paragraph("Sularahas kontoris aadressil Lasnamäe 30a, Tallinn (E-R 10:00-17:00 ja L 10:00-15:00)",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell18.setBorder(NO_BORDER);
        body3cell18.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell18);

        final var body3cell19 =
                new Cell(new Paragraph("III.7.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell19.setBorder(NO_BORDER);
        body3cell19.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell19);

        final var body3cell20 =
                new Cell(new Paragraph("Rendileandja klientide poolt väljastatud sõidutšekid, kontoris aadressil Lasnamäe 30a, Tallinn (E-R 10:00-17:00 ja L 10:00-15:00)",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell20.setBorder(NO_BORDER);
        body3cell20.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell20);

        final var body3cell21 =
                new Cell(new Paragraph("III.7.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell21.setBorder(NO_BORDER);
        body3cell21.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell21);

        final var body3cell22 =
                new Cell(new Paragraph("Ülekandega mille saaja on:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell22.setBorder(NO_BORDER);
        body3cell22.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell22);

        final var body3cell23 =
                new Cell(new Paragraph("●",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell23.setBorder(NO_BORDER);
        body3cell23.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell23);

        final var body3cell24 =
                new Cell(new Paragraph("Konto nimi: " + model.getQFirmName(),
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body3cell24.setBorder(NO_BORDER);
        body3cell24.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell24);

        final var body3cell25 =
                new Cell(new Paragraph("●",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell25.setBorder(NO_BORDER);
        body3cell25.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell25);

        final var body3cell26 =
                new Cell(new Paragraph("IBAN: " + model.getQFirmIban(),
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body3cell26.setBorder(NO_BORDER);
        body3cell26.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell26);

        final var body3cell27 =
                new Cell(new Paragraph("●",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell27.setBorder(NO_BORDER);
        body3cell27.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell27);

        final var body3cell28 =
                new Cell(new Paragraph("Selgitus: Autorent + auto number ",
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body3cell28.setBorder(NO_BORDER);
        body3cell28.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell28);

        final var body3cell29 =
                new Cell(new Paragraph(" ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell29.setBorder(NO_BORDER);
        body3cell29.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell29);

        final var body3cell30 =
                new Cell(new Paragraph("Või muudele kooskõlastatud rekvisiitidele, kuid iga muudatus olema teostatud taasesitamist võimaldavas vormis. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell30.setBorder(NO_BORDER);
        body3cell30.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell30);

        final var body3cell31 =
                new Cell(new Paragraph("III.7.4 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell31.setBorder(NO_BORDER);
        body3cell31.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell31);

        final var body3cell32 =
                new Cell(new Paragraph("Pangaülekandega taksoteenuste APPi kaudu (Bolt. Forex, Uber jne), mille kohta pooled teevad saldo kontrolli vähemalt 1 kord kuus. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell32.setBorder(NO_BORDER);
        body3cell32.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell32);

        contractPdfDoc.add(body3);


        final var body4 = new Table(2);
        body4.setWidths(new float[]{1, 19});
        body4.setPadding(0f);
        body4.setSpacing(0f);
        body4.setWidth(100f);
        body4.setBorderColor(white);
        body4.setHorizontalAlignment(LEFT);
        body4.setBorder(NO_BORDER);
        body4.setBorder(NO_BORDER);

        final var body4cell1 =
                new Cell(new Paragraph("IV.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body4cell1.setBorder(NO_BORDER);
        body4cell1.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell1);

        final var body4cell2 =
                new Cell(new Paragraph(" Poolte õigused ja kohustused ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body4cell2.setBorder(NO_BORDER);
        body4cell2.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell2);

        final var body4cell3 =
                new Cell(new Paragraph("IV.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell3.setBorder(NO_BORDER);
        body4cell3.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell3);

        final var body4cell4 =
                new Cell(new Paragraph("Rentniku õigused ja kohustused: ",
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body4cell4.setBorder(NO_BORDER);
        body4cell4.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell4);

        final var body4cell5 =
                new Cell(new Paragraph("IV.1.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell5.setBorder(NO_BORDER);
        body4cell5.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell5);

        final var body4cell6 =
                new Cell(new Paragraph("Rentnikul on õigus ja kohustus kasutada Seadet vastavalt Lepingu punktis 1.1 " +
                        "sätestatud sihtotstarbele isiklikult ning on keelatud anda Seade kolmandatele isikutele allrendile tulu teenimiseks. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell6.setBorder(NO_BORDER);
        body4cell6.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell6);

        final var body4cell7 =
                new Cell(new Paragraph("IV.1.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell7.setBorder(NO_BORDER);
        body4cell7.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell7);

        final var body4cell8 =
                new Cell(new Paragraph("Rentnikul on õigus saada kasutades Seadet tulu (vilja).",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell8.setBorder(NO_BORDER);
        body4cell8.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell8);

        final var body4cell9 =
                new Cell(new Paragraph("IV.1.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell9.setBorder(NO_BORDER);
        body4cell9.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell9);

        final var body4cell10 =
                new Cell(new Paragraph("Rentnikul on õigus saada Seade omanikuks pärast lepingu p. 3.1 tähtaja möödumist ja p.5 nimetatud kohustuse täitmisest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell10.setBorder(NO_BORDER);
        body4cell10.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell10);

        final var body4cell11 =
                new Cell(new Paragraph("IV.1.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell11.setBorder(NO_BORDER);
        body4cell11.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell11);

        final var body4cell12 =
                new Cell(new Paragraph("Rentnik kohustub kasutama Seadet heaperemehelikult ning tagama Seadme rendileandja juures tavapärase korrashoiu ja hoolduse.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell12.setBorder(NO_BORDER);
        body4cell12.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell12);

        final var body4cell13 =
                new Cell(new Paragraph("IV.1.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell13.setBorder(NO_BORDER);
        body4cell13.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell13);

        final var body4cell14 =
                new Cell(new Paragraph("Rentnik kohustub viivitamata teatama Rendileandjale igast Seadme väärtust vähendavast või hävitavast sündmusest või teost " +
                        "(arest, avarii, tulekahju jms) või ohust sellise sündmuse toimumise või teo tegemise kohta ning võtma viivitamata tarvitusele abinõud nimetatud " +
                        "sündmuse, teo või ohu kõrvaldamiseks või selle tagajärgede likvideerimiseks. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell14.setBorder(NO_BORDER);
        body4cell14.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell14);

        final var body4cell15 =
                new Cell(new Paragraph("IV.1.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell15.setBorder(NO_BORDER);
        body4cell15.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell15);

        final var body4cell16 =
                new Cell(new Paragraph("Rentnik on kohustatud viivitamatult taasesitamist võimaldavas vormis teavitama oma elukoha ja muude kontaktandmete muutmisest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell16.setBorder(NO_BORDER);
        body4cell16.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell16);

        final var body4cell17 =
                new Cell(new Paragraph("IV.1.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell17.setBorder(NO_BORDER);
        body4cell17.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell17);

        final var body4cell18 =
                new Cell(new Paragraph("Rentnik viivitamatult teavitab, kui tema suhtes on algatatud pankrotimenetlus või muudes asjaoludes," +
                        " mille järgi rentnik ei saa käesolevas lepingus  sätestatud kohustused täitma. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell18.setBorder(NO_BORDER);
        body4cell18.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell18);

        final var body4cell19 =
                new Cell(new Paragraph("IV.1.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell19.setBorder(NO_BORDER);
        body4cell19.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell19);

        final var body4cell20 =
                new Cell(new Paragraph("Rentnik kohustub Lepingu lõppemisel või ennetähtaegsel lõpetamisel tagastama Seadme Rendileandjale mitte " +
                        "halvemas seisundis, kui see talle üle anti, arvestades normaalset kulumist.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell20.setBorder(NO_BORDER);
        body4cell20.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell20);

        final var body4cell21 =
                new Cell(new Paragraph("IV.1.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell21.setBorder(NO_BORDER);
        body4cell21.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell21);

        final var body4cell22 =
                new Cell(new Paragraph("Rentnik kohustub hüvitama Rendileandjale Rentniku süül Seadme seisukorra halvenemisest või selle hävimisest tuleneva kahju.   ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell22.setBorder(NO_BORDER);
        body4cell22.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell22);

        final var body4cell23 =
                new Cell(new Paragraph("IV.1.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell23.setBorder(NO_BORDER);
        body4cell23.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell23);

        final var body4cell24 =
                new Cell(new Paragraph("Rentnik on kohustatud Seadme tagastama samasse kohta, kust sai ta selle kätte, kui ei ole lepitud kokku teisiti.    ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell24.setBorder(NO_BORDER);
        body4cell24.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell24);

        final var body4cell25 =
                new Cell(new Paragraph("IV.1.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell25.setBorder(NO_BORDER);
        body4cell25.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell25);

        final var body4cell26 =
                new Cell(new Paragraph("Rentnik on kohustatud kasutama Seade ainult Eesti Vabariigi piirides, välja arvatud juhul, kui ta saab eraldi kirjaliku loa rendileandja poolt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell26.setBorder(NO_BORDER);
        body4cell26.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell26);

        final var body4cell27 =
                new Cell(new Paragraph("IV.1.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell27.setBorder(NO_BORDER);
        body4cell27.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell27);

        final var body4cell28 =
                new Cell(new Paragraph("Rentnikul on keelatud ilma rendileandja kirjaliku loata teha Seadmes muudatused, " +
                        "remonti ja või muud toimingud, mille tulemusel oleks Seade sisemine/välimine /tehniline seisund muudetud. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell28.setBorder(NO_BORDER);
        body4cell28.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell28);

        final var body4cell29 =
                new Cell(new Paragraph("IV.1.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell29.setBorder(NO_BORDER);
        body4cell29.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell29);

        final var body4cell30 =
                new Cell(new Paragraph("Rentnik on kohustatud tasuma kõik trahvid seoses Seade kasutamisega ning mida on koostatud Liiklus ja korrakaitse Seaduse alusel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell30.setBorder(NO_BORDER);
        body4cell30.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell30);

        final var body4cell31 =
                new Cell(new Paragraph("IV.1.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell31.setBorder(NO_BORDER);
        body4cell31.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell31);

        final var body4cell32 =
                new Cell(new Paragraph("Rentnik kohustub tasuma tema poolt auto ebaõige parkimise eest ja kiiruse ületamise eest saadud trahvid hiljemalt kolme päeva jooksul pärast nende väljastamist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell32.setBorder(NO_BORDER);
        body4cell32.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell32);

        final var body4cell33 =
                new Cell(new Paragraph("IV.1.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell33.setBorder(NO_BORDER);
        body4cell33.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell33);

        final var body4cell34 =
                new Cell(new Paragraph("Sõiduki kahjustamisel Rentniku enda või kolmandate isikute poolt kannab " +
                        "kahju Rentnik. Täpsemalt  vaidluse lahendamisel pooled saavad rakendada Koostöölepingus p VII nimetatud.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell34.setBorder(NO_BORDER);
        body4cell34.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell34);

        contractPdfDoc.add(body4);


        final var body42 = new Table(2);
        body42.setWidths(new float[]{1, 20});
        body42.setPadding(0f);
        body42.setSpacing(0f);
        body42.setWidth(100f);
        body42.setBorderColor(white);
        body42.setHorizontalAlignment(LEFT);
        body42.setBorder(NO_BORDER);
        body42.setBorder(NO_BORDER);

        final var body42cell3 =
                new Cell(new Paragraph("IV.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body42cell3.setBorder(NO_BORDER);
        body42cell3.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell3);

        final var body42cell4 =
                new Cell(new Paragraph("Rendileandja õigused ja kohustused ",
                        new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
        body42cell4.setBorder(NO_BORDER);
        body42cell4.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell4);

        final var body42cell5 =
                new Cell(new Paragraph("IV.2.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell5.setBorder(NO_BORDER);
        body42cell5.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell5);

        final var body42cell6 =
                new Cell(new Paragraph("Rendileandjal on õigus kontrollida igal ajal Seadme seisukorda ja sihtotstarbelist kasutamist Rentniku poolt.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell6.setBorder(NO_BORDER);
        body42cell6.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell6);

        final var body42cell7 =
                new Cell(new Paragraph("IV.2.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell7.setBorder(NO_BORDER);
        body42cell7.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell7);

        final var body42cell8 =
                new Cell(new Paragraph("Rendileandjal on õigus nõuda oma varale Rentniku poolt tekitatud kahjude hüvitamist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell8.setBorder(NO_BORDER);
        body42cell8.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell8);

        final var body42cell9 =
                new Cell(new Paragraph("IV.2.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell9.setBorder(NO_BORDER);
        body42cell9.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell9);

        final var body42cell10 =
                new Cell(new Paragraph("Nõuda välja Seade kolmandate isikute käest, kes valdab Seade ilma õigusliku aluseta. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell10.setBorder(NO_BORDER);
        body42cell10.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell10);

        final var body42cell11 =
                new Cell(new Paragraph("IV.2.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell11.setBorder(NO_BORDER);
        body42cell11.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell11);

        final var body42cell12 =
                new Cell(new Paragraph("Rendileandja kohustub tagama Seadme funktsionaalse töötamise tema sihtotstarbe kohaselt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell12.setBorder(NO_BORDER);
        body42cell12.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell12);

        final var body42cell13 =
                new Cell(new Paragraph("IV.2.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell13.setBorder(NO_BORDER);
        body42cell13.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell13);

        final var body42cell14 =
                new Cell(new Paragraph("Esitama Rentnikule pretensioonid, mida on tal õigus Lepingu kohaselt Rentnikule esitada," +
                        " 1 kuu jooksul arvates päevast, mil ta sai teada või pidi saama teada sündmustest või asjaoludest, mis annavad aluse pretensiooni esitamiseks.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell14.setBorder(NO_BORDER);
        body42cell14.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell14);

        final var body42cell15 =
                new Cell(new Paragraph("IV.2.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body42cell15.setBorder(NO_BORDER);
        body42cell15.setHorizontalAlignment(LEFT);
        body42.addCell(body42cell15);

        final var body42cell16 =
                new Cell(new Paragraph("Erakorraliselt lõpetada lepingu.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body42cell16.setBorder(NO_BORDER);
        body42cell16.setHorizontalAlignment(JUSTIFIED);
        body42.addCell(body42cell16);

        contractPdfDoc.add(body42);


        final var body5 = new Table(2);
        body5.setWidths(new float[]{1, 20});
        body5.setPadding(0f);
        body5.setSpacing(0f);
        body5.setWidth(100f);
        body5.setBorderColor(white);
        body5.setHorizontalAlignment(LEFT);
        body5.setBorder(NO_BORDER);
        body5.setBorder(NO_BORDER);

        final var body5cell1 =
                new Cell(new Paragraph("V.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body5cell1.setBorder(NO_BORDER);
        body5cell1.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell1);

        final var body5cell2 =
                new Cell(new Paragraph(" Seade omastamise õigus/lepingu erikokkuleppe ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body5cell2.setBorder(NO_BORDER);
        body5cell2.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell2);

        final var body5cell3 =
                new Cell(new Paragraph("V.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell3.setBorder(NO_BORDER);
        body5cell3.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell3);

        final var body5cell4 =
                new Cell(new Paragraph("Pooled kinnitavad, et käesolev punkt loetakse erikokkuleppena, mille tingimused on läbiräägitud," +
                        " arutatud ning pooltele on selge, kõik kinnitused seoses käesoleva punktiga on tagasivõtmatu.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell4.setBorder(NO_BORDER);
        body5cell4.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell4);

        final var body5cell5 =
                new Cell(new Paragraph("V.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell5.setBorder(NO_BORDER);
        body5cell5.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell5);

        final var body5cell6 =
                new Cell(new Paragraph("Rendileandja ja rentnik lepivad kokku, et on rentnikul õigus saada p 2.1 nimetatud " +
                        " Seade enda omandisse, kuid selleks peavad olema täidetud allpool nimetatud tingimused:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell6.setBorder(NO_BORDER);
        body5cell6.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell6);

        final var body5cell7 =
                new Cell(new Paragraph("V.2.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell7.setBorder(NO_BORDER);
        body5cell7.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell7);

        final var body5cell8 =
                new Cell(new Paragraph("Rentnik tasub p. 3.1 tähtaja jooksul nõuetekohaselt ja õigeaegselt rendi vastavalt p.3.3 sätestatule. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell8.setBorder(NO_BORDER);
        body5cell8.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell8);

        final var body5cell9 =
                new Cell(new Paragraph("V.2.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell9.setBorder(NO_BORDER);
        body5cell9.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell9);

        final var body5cell10 =
                new Cell(new Paragraph("Juhul, kui soovib rentnik osta Seade välja ennetähtaegselt, on ta kohustatud tasuma " +
                        "p. 3.3 nimetatud summa, korrutatud p. 3.1 ülejäänud tähtaja peale.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell10.setBorder(NO_BORDER);
        body5cell10.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell10);

        final var body5cell11 =
                new Cell(new Paragraph("V.2.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell11.setBorder(NO_BORDER);
        body5cell11.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell11);

        final var body5cell12 =
                new Cell(new Paragraph("Juhul, kui on rentnikul jäänud rendi või ta muude kohustuste osas võlgnevused lepingu " +
                        "lõppemise hetkeks (vastavalt punktis 3.1 ja 3.2 kirjeldatud tingimustele), kuid soovib ta siiski kasutada punktis " +
                        "5.2 kirjeldatud seade omastamise õigus, jääb tal võimalus jätkata seade rentimist igaks vajalikuks perioodiks olemasolevate " +
                        "võlgade tasumiseks, kuid peavad sel juhul olema rentniku sissemaksed mitte vähem, kui ta auto kestev nädala rendimaksumus. " +
                        "Sel juhul toimub kõikide punktis 3.4 kirjeldatud liisinguandja kohustuste tasumine üürniku kulul. " +
                        "Peale selle, kui on rentnikul jäänud lepingu tähtaja möödumisel (p. 3.1 ja p. 3.2) võlgnevused ja/või ületab ta nimetatud tähtaega," +
                        " on ta Seade omastamise soovil kohustatud tasuma 5% trahvi kehtivast nädalarendi summast iga" +
                        " lepingu tähtaja ületatud päeva eest võlgnevuse hüvitamise ajani.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell12.setBorder(NO_BORDER);
        body5cell12.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell12);

        final var body5cell13 =
                new Cell(new Paragraph("V.2.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell13.setBorder(NO_BORDER);
        body5cell13.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell13);

        final var body5cell14 =
                new Cell(new Paragraph("Antud lepingulisa seade väljaostu õigus ei loeta õiguslikuks p. 3.2 määratud tähtaja saabumisel, kui on rentnikul olemas võlgnevused rendileandjale",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell14.setBorder(NO_BORDER);
        body5cell14.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell14);

        final var body5cell15 =
                new Cell(new Paragraph("V.2.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell15.setBorder(NO_BORDER);
        body5cell15.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell15);

        final var body5cell16 =
                new Cell(new Paragraph("Juhul, kui rentnik ei soovi Seade välja osta ja lõpetab lepingu enne p. " +
                        "3.1 nimetatud tähtaja, ei ole tal õigust saada/tasaarvestada tasutud rendi tagasi. " +
                        "Tasutud summad on renditasu ja muud koostöölepinguga ning väljaostu lisaga reguleeritavad rentniku kohustused ja tagastamisele ei kuulu.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell16.setBorder(NO_BORDER);
        body5cell16.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell16);

        final var body5cell17 =
                new Cell(new Paragraph("V.2.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell17.setBorder(NO_BORDER);
        body5cell17.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell17);

        final var body5cell18 =
                new Cell(new Paragraph("Käesolev erikokkuleppe on sõlmitud vaid p 2.1 nimetatud Seadele suhtes ning nõuab käesoleva lepingu tingimuste nõuetekohast täitmist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell18.setBorder(NO_BORDER);
        body5cell18.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell18);


        contractPdfDoc.add(body5);

        final var body6 = new Table(2);
        body6.setWidths(new float[]{1, 20});
        body6.setPadding(0f);
        body6.setSpacing(0f);
        body6.setWidth(100f);
        body6.setBorderColor(white);
        body6.setHorizontalAlignment(LEFT);
        body6.setBorder(NO_BORDER);
        body6.setBorder(NO_BORDER);

        final var body6cell1 =
                new Cell(new Paragraph("VI.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body6cell1.setBorder(NO_BORDER);
        body6cell1.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell1);

        final var body6cell2 =
                new Cell(new Paragraph(" Sanktsioonid ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body6cell2.setBorder(NO_BORDER);
        body6cell2.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell2);

        final var body6cell3 =
                new Cell(new Paragraph("VI.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell3.setBorder(NO_BORDER);
        body6cell3.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell3);

        final var body6cell4 =
                new Cell(new Paragraph("Lepinguga võetud kohustuste täitmata jätmise eest kannavad " +
                        "Pooled Eesti Vabariigi Seadustes ja muudes õigusaktides ning Lepingus ettenähtud vastutust. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell4.setBorder(NO_BORDER);
        body6cell4.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell4);

        final var body6cell5 =
                new Cell(new Paragraph("VI.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell5.setBorder(NO_BORDER);
        body6cell5.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell5);

        final var body6cell6 =
                new Cell(new Paragraph("Pooled kannavad täielikku varalist vastutust Lepingust tulenevate kohustuste täitmata" +
                        " jätmise või mittekohase täitmisega teisele Poolele tekitatud kahju eest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell6.setBorder(NO_BORDER);
        body6cell6.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell6);

        final var body6cell7 =
                new Cell(new Paragraph("VI.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell7.setBorder(NO_BORDER);
        body6cell7.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell7);

        final var body6cell8 =
                new Cell(new Paragraph("Kui Rentnik viivitab mistahes Lepingus sätestatud tasu maksmisega on Rendileandjal " +
                        "õigus nõuda viivist summas 1% viivitatud summast  päevas, sõltumata võla summast. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell8.setBorder(NO_BORDER);
        body6cell8.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell8);

        final var body6cell9 =
                new Cell(new Paragraph("VI.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell9.setBorder(NO_BORDER);
        body6cell9.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell9);

        final var body6cell10 =
                new Cell(new Paragraph("Kui Lepingu lõppemisel või ennetähtaegsel lõpetamisel Rentnik ei tagasta Rendileandjale " +
                        "Seadme valduse ning Rendileandja on selle kohustuse täitmise vajadusest Rentnikule kirjalikult teavitanud," +
                        " loetakse Rentnik valdusest loobunuks ning Rendileandjal on õigus asuda tegeliku võimu teostamisele Seadme üle. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell10.setBorder(NO_BORDER);
        body6cell10.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell10);

        final var body6cell11 =
                new Cell(new Paragraph("VI.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell11.setBorder(NO_BORDER);
        body6cell11.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell11);

        final var body6cell12 =
                new Cell(new Paragraph("Käesolevale koostöölepingule alla kirjutades nõustub juhatuse liige või rentniku " +
                        "seaduslik esindaja vastutama rentnikuga solidaarselt ja vastavalt käesoleva lepingu tingimustele.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell12.setBorder(NO_BORDER);
        body6cell12.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell12);

        final var body6cell13 =
                new Cell(new Paragraph("VI.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell13.setBorder(NO_BORDER);
        body6cell13.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell13);

        final var body6cell14 =
                new Cell(new Paragraph("Pooled leppivad kokku, et aktsepteerivad makse teostamisel alljärgnevad võlga " +
                        "tasumise järjekorda: esimesena tuleb tasuda : raha sissenõudmise tasu, trahvid, viivised, siis võlasumma, " +
                        "siis järgmise makse põhisumma ja vajalikud remondi kuulud, mida on ilmunud Seade ebakorraliku kasutamisest " +
                        "ning mida võivad tekkida kolmanda isiku tegevuse tulemusel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell14.setBorder(NO_BORDER);
        body6cell14.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell14);

        contractPdfDoc.add(body6);


        final var body7 = new Table(2);
        body7.setWidths(new float[]{1, 20});
        body7.setPadding(0f);
        body7.setSpacing(0f);
        body7.setWidth(100f);
        body7.setBorderColor(white);
        body7.setHorizontalAlignment(LEFT);
        body7.setBorder(NO_BORDER);
        body7.setBorder(NO_BORDER);

        final var body7cell1 =
                new Cell(new Paragraph("VII.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body7cell1.setBorder(NO_BORDER);
        body7cell1.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell1);

        final var body7cell2 =
                new Cell(new Paragraph(" Vääramatu jõud ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body7cell2.setBorder(NO_BORDER);
        body7cell2.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell2);

        final var body7cell3 =
                new Cell(new Paragraph("VII.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell3.setBorder(NO_BORDER);
        body7cell3.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell3);

        final var body7cell4 =
                new Cell(new Paragraph("Pooled vabanevad vastutusest lepingu mittetäitmise või mittekohase täitmise korral," +
                        " kui mittetäitmise või mittekohase täitmise põhjustasid asjaolud, millised antud olukorras on vältimatud " +
                        "ja/või poolte tahtest sõltumatud, nagu politsei ja/või sõjaväe operatsioonid, faktilised liiklus- ja " +
                        "ilmastikuolud jm. Loodusjõudude toime, samuti sõjaseisukord, streigid, riigivõimu- ja valitsemisorganite" +
                        " normatiivaktide toime ja teised asjaolud, mida rahvusvaheline praktika tunnustab vääramatu jõuna.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell4.setBorder(NO_BORDER);
        body7cell4.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell4);

        final var body7cell5 =
                new Cell(new Paragraph("VII.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell5.setBorder(NO_BORDER);
        body7cell5.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell5);

        final var body7cell6 =
                new Cell(new Paragraph("Vääramatu jõu asjaolude ilmnemise korral teevad pooled kõik nendest sõltuva vältimaks võimaliku kahju teket.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell6.setBorder(NO_BORDER);
        body7cell6.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell6);

        final var body7cell7 =
                new Cell(new Paragraph("VII.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell7.setBorder(NO_BORDER);
        body7cell7.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell7);

        final var body7cell8 =
                new Cell(new Paragraph("Vääramatu jõu esinemisel ei pikene leping tähtaja võrra, mille jooksul pooled ei saanud oma kohustusi täita vääramatu jõu tõttu.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell8.setBorder(NO_BORDER);
        body7cell8.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell8);

        contractPdfDoc.add(body7);


        final var body8 = new Table(2);
        body8.setWidths(new float[]{1, 20});
        body8.setPadding(0f);
        body8.setSpacing(0f);
        body8.setWidth(100f);
        body8.setBorderColor(white);
        body8.setHorizontalAlignment(LEFT);
        body8.setBorder(NO_BORDER);
        body8.setBorder(NO_BORDER);

        final var body8cell1 =
                new Cell(new Paragraph("VIII.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body8cell1.setBorder(NO_BORDER);
        body8cell1.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell1);

        final var body8cell2 =
                new Cell(new Paragraph(" Lepingu lõppemine ja ennetähtaegne lõpetamine ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body8cell2.setBorder(NO_BORDER);
        body8cell2.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell2);

        final var body8cell3 =
                new Cell(new Paragraph("VIII.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell3.setBorder(NO_BORDER);
        body8cell3.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell3);

        final var body8cell4 =
                new Cell(new Paragraph(" Leping lõpeb tähtaja möödumisega või Lepingu ennetähtaegse lõpetamisega.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell4.setBorder(NO_BORDER);
        body8cell4.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell4);

        final var body8cell5 =
                new Cell(new Paragraph("VIII.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell5.setBorder(NO_BORDER);
        body8cell5.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell5);

        final var body8cell6 =
                new Cell(new Paragraph("Pooled lepivad kokku, et juhul kui Rentnik lõpetab lepingu ennetähtaegselt," +
                        " on ta kohustatud tasuma ühekordse trahvi summas 3 000 eurot. Pooled käesoleva tingimuse läbirääkinud " +
                        "ning on rentnik teadlik, et antud tingimus ei loeta ebamõistlikult kahjustavaks, kuna see puudutab " +
                        "lepingu põhilist eset, hinna ja üleantu väärtuse suhet.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell6.setBorder(NO_BORDER);
        body8cell6.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell6);

        final var body8cell7 =
                new Cell(new Paragraph("VIII.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell7.setBorder(NO_BORDER);
        body8cell7.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell7);

        final var body8cell8 =
                new Cell(new Paragraph(" Rentniku suhtes kohaldatakse käesoleva lepingu punktis VIII.2 nimetatud trahvi " +
                        "ka juhul, kui rendileandja lõpetab selle lepingu ennetähtaegselt rentniku võlgnevuse tõttu rendileandja ees," +
                        " mis on seotud käesolevast lepingust tulenevate kohustuste täitmisega, kas rentnik on võlgu 5" +
                        " järjestiku rendimaksed või  rendi võlgnevus ületab 1 000 eurot. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell8.setBorder(NO_BORDER);
        body8cell8.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell8);

        final var body8cell9 =
                new Cell(new Paragraph("VIII.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell9.setBorder(NO_BORDER);
        body8cell9.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell9);

        final var body8cell10 =
                new Cell(new Paragraph(" Juhul, kui leping lõpeb tähtaja möödumisel ja rakendatakse  lepingu p. 5," +
                        " siis pooled kontrollivad kõikide maksete nõuetekohane täitmine, koostav kontroll akti ning toimub omandi vormistamine. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell10.setBorder(NO_BORDER);
        body8cell10.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell10);

        final var body8cell11 =
                new Cell(new Paragraph("VIII.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell11.setBorder(NO_BORDER);
        body8cell11.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell11);

        final var body8cell12 =
                new Cell(new Paragraph(" Juhul, kui leping lõpetatakse poolte nõusolekul enne tähtaega, siis Rentnik tagastab rendileandjale" +
                        " vara akti alusel. Kontrollitakse Seade seisukord. Pooled kontrollivad millised maksed on " +
                        "tagastamise seisuga tasutud/tasumata. Siis lõpetavad pooled lepingu.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell12.setBorder(NO_BORDER);
        body8cell12.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell12);

        final var body8cell13 =
                new Cell(new Paragraph("VIII.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell13.setBorder(NO_BORDER);
        body8cell13.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell13);

        final var body8cell14 =
                new Cell(new Paragraph(" Rendileandjal on õigus Leping ennetähtaegselt lõpetada, teatades sellest 5 (viis) " +
                        "tööpäeva ette kui Rentnik ei täida Lepingust või Seadusest tulenevaid kohustusi. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell14.setBorder(NO_BORDER);
        body8cell14.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell14);

        final var body8cell15 =
                new Cell(new Paragraph("VIII.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell15.setBorder(NO_BORDER);
        body8cell15.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell15);

        final var body8cell16 =
                new Cell(new Paragraph(" Rentnikul on õigus lõpetada Leping ennetähtaegselt, teatades sellest Rendileandjale 5 (viis) tööpäeva ette. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell16.setBorder(NO_BORDER);
        body8cell16.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell16);

        contractPdfDoc.add(body8);


        final var body9 = new Table(2);
        body9.setWidths(new float[]{1, 20});
        body9.setPadding(0f);
        body9.setSpacing(0f);
        body9.setWidth(100f);
        body9.setBorderColor(white);
        body9.setHorizontalAlignment(LEFT);
        body9.setBorder(NO_BORDER);
        body9.setBorder(NO_BORDER);

        final var body9cell1 =
                new Cell(new Paragraph("IX.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body9cell1.setBorder(NO_BORDER);
        body9cell1.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell1);

        final var body9cell2 =
                new Cell(new Paragraph("Lõppsätted ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body9cell2.setBorder(NO_BORDER);
        body9cell2.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell2);

        final var body9cell3 =
                new Cell(new Paragraph("IX.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell3.setBorder(NO_BORDER);
        body9cell3.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell3);

        final var body9cell4 =
                new Cell(new Paragraph("Leping on koostatud kahes identses eksemplaris, millest kumbki pool on saanud ühe eksemplari. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell4.setBorder(NO_BORDER);
        body9cell4.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell4);

        final var body9cell5 =
                new Cell(new Paragraph("IX.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell5.setBorder(NO_BORDER);
        body9cell5.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell5);

        final var body9cell6 =
                new Cell(new Paragraph("Lepingu muudatused tuleb teha kirjalikus vormis.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell6.setBorder(NO_BORDER);
        body9cell6.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell6);

        final var body9cell7 =
                new Cell(new Paragraph("IX.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell7.setBorder(NO_BORDER);
        body9cell7.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell7);

        final var body9cell8 =
                new Cell(new Paragraph("Lepingust tulenevad vaidlused püüavad pooled lahendada läbirääkimiste teel. Kokkuleppe mittesaavutamisel lahendatakse vaidlused kohtus. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell8.setBorder(NO_BORDER);
        body9cell8.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell8);

        final var body9cell9 =
                new Cell(new Paragraph("IX.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell9.setBorder(NO_BORDER);
        body9cell9.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell9);

        final var body9cell10 =
                new Cell(new Paragraph("Lepingu Lisa 1: Seadme spetsifikatsioon ja lisavarustuse loetelu.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell10.setBorder(NO_BORDER);
        body9cell10.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell10);

        contractPdfDoc.add(body9);




        final var signature = new Table(2);
        signature.setWidths(new float[]{1, 4});
        signature.setPadding(0f);
        signature.setSpacing(0f);
        signature.setWidth(100f);
        signature.setBorderColor(white);
        signature.setHorizontalAlignment(LEFT);
        signature.setBorder(NO_BORDER);
        signature.setBorder(NO_BORDER);

        final var signaturecell1 =
                new Cell(new Paragraph(" Poolte allkirjad:  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell1.setBorder(NO_BORDER);
        signaturecell1.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell1);

        final var signaturecell2 =
                new Cell(new Paragraph(model.getQFirmName(),
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell2.setBorder(NO_BORDER);
        signaturecell2.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell2);

        final var signaturecell3 =
                new Cell(new Paragraph("Rendileandja: ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell3.setBorder(NO_BORDER);
        signaturecell3.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell3);

        final var signaturecell4 =
                new Cell(new Paragraph("________________________________________________________________________      _______________  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell4.setBorder(NO_BORDER);
        signaturecell4.setHorizontalAlignment(JUSTIFIED);
        signature.addCell(signaturecell4);

        final var signaturecell41 =
                new Cell(new Paragraph("Nimi / Perekonnanimi  , allkiri                                                        Kuupäev              ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        signaturecell41.setColspan(2);
        signaturecell41.setBorder(NO_BORDER);
        signaturecell41.setHorizontalAlignment(RIGHT);
        signature.addCell(signaturecell41);

        final var signaturecell5 =
                new Cell(new Paragraph("RENTNIK:  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell5.setBorder(NO_BORDER);
        signaturecell5.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell5);

        final var signaturecell6 =
                new Cell(new Paragraph(model.getRenterName(),
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell6.setBorder(NO_BORDER);
        signaturecell6.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell6);

        final var signaturecell7 =
                new Cell(new Paragraph("Rentnik  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell7.setBorder(NO_BORDER);
        signaturecell7.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell7);

        final var signaturecell8 =
                new Cell(new Paragraph("________________________________________________________________________      _______________  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell8.setBorder(NO_BORDER);
        signaturecell8.setHorizontalAlignment(JUSTIFIED);
        signature.addCell(signaturecell8);

        final var signaturecell9 =
                new Cell(new Paragraph("Nimi / Perekonnanimi  , allkiri                                                        Kuupäev             ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        signaturecell9.setColspan(2);
        signaturecell9.setBorder(NO_BORDER);
        signaturecell9.setHorizontalAlignment(RIGHT);
        signature.addCell(signaturecell9);

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
