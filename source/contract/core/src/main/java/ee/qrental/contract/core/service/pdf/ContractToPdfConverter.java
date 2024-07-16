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
public class ContractToPdfConverter {

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
        cell.setRowspan(3);
        cell.setBorder(NO_BORDER);
        header1.addCell(cell);

        final var cell1 =
                new Cell(
                        new Paragraph("Rendileping  sõiduauto taksoteenuse ja majandustegevuse kasutamiseks   Nr. "  + model.getNumber(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        cell1.setBorder(NO_BORDER);
        cell1.setHorizontalAlignment(CENTER);
        header1.addCell(cell1);

        final var cell2 =
                new Cell(new Paragraph("Data : " + model.getCreated(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        cell2.setBorder(NO_BORDER);
        cell2.setHorizontalAlignment(CENTER);
        header1.addCell(cell2);

        /*final var cell3 =
                new Cell(new Paragraph(format("Koostöölepingu pooled on RENDILEANDJA ja RENTNIK."), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        cell3.setBorder(NO_BORDER);
        cell3.setHorizontalAlignment(CENTER);
        header1.addCell(cell3);
        */

        contractPdfDoc.add(header1);
        contractPdfDoc.add(new Paragraph("\n"));

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
                new Cell(new Paragraph("E-post:  " + getTextOrEmpty(model.getQFirmEmail()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell8.setBorder(NO_BORDER);
        rendileandjacell8.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell8);

        final var rendileandjacell9 =
                new Cell(new Paragraph("Kontakttelefon:  " + getTextOrEmpty(model.getQFirmVatPhone()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell9.setBorder(NO_BORDER);
        rendileandjacell9.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell9);

        final var rendileandjacell10 =
                new Cell(new Paragraph("Rendileandja esindaja:  " + getTextOrEmpty(model.getQFirmCeo() + " või volitatud isik Nadežda Tarraste"),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rendileandjacell10.setBorder(NO_BORDER);
        rendileandjacell10.setHorizontalAlignment(LEFT);
        rendileandja.addCell(rendileandjacell10);

        contractPdfDoc.add(rendileandja);

/*
@SneakyThrows
 /*       private Table getRequisitesQfirm (
        final String invoiceDriverCompany,
        final String invoiceDriverCompanyAddress,
        final String invoiceDriverCompanyRegNumber,
        final String driverCompanyVat,
        final String driverInfo){
            final var requisites = new Table(2);
            requisitesQfirm.setWidths(new float[]{1, 4});
            requisitesQfirm.setPadding(0f);
            requisitesQfirm.setSpacing(-1f);
            requisitesQfirm.setWidth(100f);
            requisitesQfirm.setHorizontalAlignment(HorizontalAlignment.LEFT);
            requisitesQfirm.setBorder(NO_BORDER);
            requisitesQfirm.addCell(getRequisitLabelCell("Maksja"));
            requisitesQfirm.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompany)));
            requisitesQfirm.addCell(getRequisitLabelCell("Aadress"));
            requisitesQfirm.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompanyAddress)));
            requisitesQfirm.addCell(getRequisitLabelCell("Äriregistri nr."));
            requisitesQfirm.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompanyRegNumber)));
            requisitesQfirm.addCell(getRequisitLabelCell("KMKR nr."));
            requisitesQfirm.addCell(getRequisitValueCell(getTextOrEmpty(driverCompanyVat)));
            requisitesQfirm.addCell(getRequisitLabelCell("Arve saaja"));
            requisitesQfirm.addCell(getRequisitValueCell(getTextOrEmpty(driverInfo)));

            return requisitesQfirm;
        }

*/
        final var rentnik = new Table(1);
        rentnik.setPadding(0f);
        rentnik.setSpacing(0f);
        rentnik.setWidth(100f);
        rentnik.setBorderColor(white);
        rentnik.setHorizontalAlignment(LEFT);
        rentnik.setBorder(NO_BORDER);
        rentnik.setBorder(NO_BORDER);

        final var rentnikcell1 =
                new Cell(new Paragraph("RENTNIKU ANDMED: ", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
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
                new Cell(new Paragraph("Rentniku seadusliku või volitatud esindaja nimi:  " + getTextOrEmpty(model.getRenterCeoName()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell5.setBorder(NO_BORDER);
        rentnikcell5.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell5);

        final var rentnikcell6 =
                new Cell(new Paragraph("Rentniku seadusliku või volitatud esindaja isikukood:  " + model.getRenterCeoIsikukood(),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell6.setBorder(NO_BORDER);
        rentnikcell6.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell6);

        final var rentnikcell7 =
                new Cell(new Paragraph("Rentniku või selle seadusliku ega volitatud esindaja juhiloa number:  " + getTextOrEmpty(model.getDriverLicenceNumber()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell7.setBorder(NO_BORDER);
        rentnikcell7.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell7);

        final var rentnikcell8 =
                new Cell(new Paragraph("Rentniku või selle seadusliku ega volitatud esindaja kontaktaadress:   " + getTextOrEmpty(model.getDriverAddress()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell8.setBorder(NO_BORDER);
        rentnikcell8.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell8);

        final var rentnikcell9 =
                new Cell(new Paragraph("Rentniku või selle seadusliku ega volitatud esindaja kontakttelefon:   " + getTextOrEmpty(model.getRenterPhone()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell9.setBorder(NO_BORDER);
        rentnikcell9.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell9);

        final var rentnikcell10 =
                new Cell(new Paragraph("Rentniku või selle seadusliku ega volitatud esindaja e-post:    " + getTextOrEmpty(model.getRenterEmail()),
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        rentnikcell10.setBorder(NO_BORDER);
        rentnikcell10.setHorizontalAlignment(LEFT);
        rentnik.addCell(rentnikcell10);

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
                new Cell(new Paragraph("Üldsätted",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body1cell2.setBorder(NO_BORDER);
        body1cell2.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell2);

        final var body1cell3 =
                new Cell(new Paragraph("1.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell3.setBorder(NO_BORDER);
        body1cell3.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell3);

        final var body1cell4 =
                new Cell(new Paragraph("Lepingu põhitingimused kasutatud mõistete selgitused on toodud Lepingu üldtingimustes ja nende lisades, " +
                        "mis on käesoleva lepingu lahutamata osa. Käesoleva lepingu allakirjutamisega kinnitab tagasivõtmatult Rentnik, " +
                        "et ta on läbi lugenud, arusaanud, andnud ning nõustunud lepingu üldtingimustega. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell4.setBorder(NO_BORDER);
        body1cell4.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell4);

        final var body1cell5 =
                new Cell(new Paragraph("1.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell5.setBorder(NO_BORDER);
        body1cell5.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell5);

        final var body1cell6 =
                new Cell(new Paragraph("Kõik käesoleva lepingu eritingimused on pooltel vahel eraldi läbiräägitud ja kokkulepitud." +
                        " Pooled tagasivõtmatult kinnitavad, et iga eritingimuses sätestatud omab imperatiivsed kohaldamist võrreldes üldtingimustes sätestatuga. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell6.setBorder(NO_BORDER);
        body1cell6.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell6);

        final var body1cell7 =
                new Cell(new Paragraph("1.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell7.setBorder(NO_BORDER);
        body1cell7.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell7);

        final var body1cell8 =
                new Cell(new Paragraph("Rendileandja ja Rentnik sõlmivad lepingu poolte majandustegevuse raames, mille alusel Rentnikul on õigus rentida tulu saamiseks," +
                        " ehk taksoteenuse osutamiseks vaba auto Rendileandja autopargist. Käesoleva lepingu kohaselt kohustub Rendileandja andma Rentnikule kasutamiseks lepingus " +
                        "ja üldtingimustes toodud vaba sõidukit oma autopargist - rendieseme ehk Rendiauto. Rentnik on kohustatud maksma selle eest tasu (Renti) Rendileandjale kogu Rendiperioodi eest.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1cell8.setBorder(NO_BORDER);
        body1cell8.setHorizontalAlignment(JUSTIFIED);
        body1.addCell(body1cell8);

        final var body1cell9 =
                new Cell(new Paragraph("1.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1cell9.setBorder(NO_BORDER);
        body1cell9.setHorizontalAlignment(LEFT);
        body1.addCell(body1cell9);

        final var body1cell10 =
                new Cell(new Paragraph("Rendileping sõlmitakse määramata tähtajaks, ja see kehtib poolte poolt ülesütlemise avalduse esitamiseni. Leping pikeneb automaatselt perioodiks, mis on võrdne käesoleva lepingu punktis 3.2 nimetatud renditeenuse minimaalse kestusega. ",
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
                new Cell(new Paragraph("Rendilepingu tingimused",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body2cell2.setBorder(NO_BORDER);
        body2cell2.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell2);

        final var body2cell3 =
                new Cell(new Paragraph("2.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell3.setBorder(NO_BORDER);
        body2cell3.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell3);

        final var body2cell4 =
                new Cell(new Paragraph("Renditav ese (auto) on Rendileandja poolt Rentnikule vastavalt üleandmise vastuvõtmise aktile," +
                        " mis on käesoleva lepingu lahutamatu osa, antud Rendileandja autopargist vaba auto. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell4.setBorder(NO_BORDER);
        body2cell4.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell4);

        final var body2cell5 =
                new Cell(new Paragraph("2.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell5.setBorder(NO_BORDER);
        body2cell5.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell5);

        final var body2cell6 =
                new Cell(new Paragraph("Iga renditava auto hind lepitakse kokku eraldi ning sõltub autoomadustest. " +
                        "Pooled fikseerivad rendihinda suurust eraldi üleandmise-vastuvõtmise aktis, mis on käesoleva lepingu lisa ja/või lisad. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell6.setBorder(NO_BORDER);
        body2cell6.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell6);


        final var body2cell7 =
                new Cell(new Paragraph("2.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell7.setBorder(NO_BORDER);
        body2cell7.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell7);

        final var body2cell8 =
                new Cell(new Paragraph("Rendiauto tagatis on 300 eurot. Rendileandjal on õigus tasaarvestada rendilepingu lõpetamisel Rentniku täitmata kohustused tagatise arvelt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell8.setBorder(NO_BORDER);
        body2cell8.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell8);


        final var body2cell9 =
                new Cell(new Paragraph("2.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell9.setBorder(NO_BORDER);
        body2cell9.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell9);

        final var body2cell10 =
                new Cell(new Paragraph("Rentnik kohustub tasuma rendileandjale ettemaksu iga nädala rendi eest sularahas Rendileandja kontoris, " +
                        "mis asub aadressil Lasnamäe 30a, Tallinn, või ülekandega Rendileandja pangakontole (või muule Rendileandja esindajaga maaratud pangakontole) " +
                        "asjakohase selgitusega – ”autorent + auto number”, mitte hiljem, kui iga nädala teisipäeva kella 16:00’ni. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell10.setBorder(NO_BORDER);
        body2cell10.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell10);


        final var body2cell11 =
                new Cell(new Paragraph("2.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell11.setBorder(NO_BORDER);
        body2cell11.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell11);

        final var body2cell12 =
                new Cell(new Paragraph("Rendileping on tähtajatu ning kehtib kuni lepingu ülesütlemiseni. ",
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
                new Cell(new Paragraph(" Eritingimused ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body3cell2.setBorder(NO_BORDER);
        body3cell2.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell2);

        final var body3cell3 =
                new Cell(new Paragraph("3.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell3.setBorder(NO_BORDER);
        body3cell3.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell3);

        final var body3cell4 =
                new Cell(new Paragraph("Kütus ei sisaldu rendihinnas. Korralist tehnilist hooldust teostab Rendileandja.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell4.setBorder(NO_BORDER);
        body3cell4.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell4);

        final var body3cell5 =
                new Cell(new Paragraph("3.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell5.setBorder(NO_BORDER);
        body3cell5.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell5);

        final var body3cell6 =
                new Cell(new Paragraph("Rendileandja poolt käesoleva koostöölepingu alusel osutatava renditeenuse  " +
                        "minimaalne kestus on " + model.getDuration() + "  täis kalendrinädalat esimese rendiauto edastamise kuupäevast pärast käesoleva rendilepingu allkirjastamist. " +
                        "Ülalnimetatud kestus hakatakse lugema tema esimese lepingujärgse rendiauto üleandmise-vastuvõtmise aktis märgitud kuupäevast. Renditeenuse kasutamise ennetähtaegsel " +
                        "ja erakorralisel (käesoleva rendilepingu ja selle tüüptimgimuste alusel) lõpetamisel kohustub rentnik tasuma koige kasutamata rendinädalate eest vastavalt käesoleva " +
                        "lepingu ja tema esimese lepingujärgse rendiauto üleandmise-vastuvõtmise akti tingimustele.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell6.setBorder(NO_BORDER);
        body3cell6.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell6);

        final var body3cell7 =
                new Cell(new Paragraph("3.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell7.setBorder(NO_BORDER);
        body3cell7.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell7);

        final var body3cell8 =
                new Cell(new Paragraph("Rentnik vastutab kahju eest vastavalt üldtingimustes ptk VI,  VII ja VIII sätestatud järgi.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell8.setBorder(NO_BORDER);
        body3cell8.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell8);

        final var body3cell9 =
                new Cell(new Paragraph("3.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell9.setBorder(NO_BORDER);
        body3cell9.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell9);

        final var body3cell10 =
                new Cell(new Paragraph("Viivis iga tasumata jäetud päeva eest on 0,1% kalendripäevas tasumata summalt.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell10.setBorder(NO_BORDER);
        body3cell10.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell10);

        final var body3cell11 =
                new Cell(new Paragraph("3.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell11.setBorder(NO_BORDER);
        body3cell11.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell11);

        final var body3cell12 =
                new Cell(new Paragraph("Võlalimiit on 240 eurot, mille ületamisel annab Rendileandja Rentnikule 14 päeva võlgade " +
                        "kõrvaldamiseks siis edasi peatub Rendileandja renditeenuse osutamist ja loeb lepingu oluliselt rikutuks.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell12.setBorder(NO_BORDER);
        body3cell12.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell12);

        final var body3cell13 =
                new Cell(new Paragraph("3.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell13.setBorder(NO_BORDER);
        body3cell13.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell13);

        final var body3cell14 =
                new Cell(new Paragraph("Pooled on leppinud kokku eraldi, et käesolev leping on sõlmitud Rentniku majandustegevuse raames ning käesolevale ei kohada VÕS sätestatud tarbija sätted.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell14.setBorder(NO_BORDER);
        body3cell14.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell14);
//
        final var body3cell15 =
                new Cell(new Paragraph("3.6.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell15.setBorder(NO_BORDER);
        body3cell15.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell15);

        final var body3cell16 =
                new Cell(new Paragraph("Rentnik (esindaja ) füüsilise isikuna (" + getTextOrEmpty(model.getRenterCeoName()) + " " + model.getRenterCeoIsikukood() + ") avaldab ja kinnitab oma allkirjaga tagasivõtmatult," +
                        "et ta käendab käesolevas lepingus tekkitavad kohustused mis tekkivad majandustegevuse raames, kuivõrd olles Põhivõlgniku juhatuse liige ja Põhivõlgniku tegelik " +
                        "kasusaav omanik (" + getTextOrEmpty(model.getRenterCeoName()) + " " +  model.getRenterCeoIsikukood() + "), tagab Käendaja nimetatud lepingus tekkivad kohustused antava käendusega Pooled avaldavad, " +
                        "et nad ei käsitle käesoleva võlatunnistuse antud käendust tarbijakäendusena võlaõigusseaduse tähenduses. Käendaja vastutab Rendileandja ees täies ulatuses solidaarselt," +
                        " tagades kõiki Rendileandja nõudeid Rentniku vastu, mis tekivad või võivad tekkida käesoleva lepingu alusel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell16.setBorder(NO_BORDER);
        body3cell16.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell16);

        final var body3cell17 =
                new Cell(new Paragraph("3.6.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell17.setBorder(NO_BORDER);
        body3cell17.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell17);

        final var body3cell18 =
                new Cell(new Paragraph("Pooled on leppinud kokku, et käendaja füüsilise isikuna maksimaalne vastutuse piir on kuus tuhat eurot. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell18.setBorder(NO_BORDER);
        body3cell18.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell18);

        final var body3cell19 =
                new Cell(new Paragraph("3.6.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell19.setBorder(NO_BORDER);
        body3cell19.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell19);

        final var body3cell20 =
                new Cell(new Paragraph("Käendusega on tagatud nii põhi- kui kõrvalkohustuste täitmine, sealhulgas intresside, viiviste ja trahvide tasumise kohustus, " +
                        "samuti Võlaõigusliku lepingu rikkumisest tuleneva kahju hüvitamise, Võlaõigusliku lepingu ülesütlemise või sellest taganemisega seotud kulude ja võla" +
                        " sissenõudmise kulude tasumise kohustused. Käesolevale lepingule ei kohaldata VÕS § 143 sätestatut. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell20.setBorder(NO_BORDER);
        body3cell20.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell20);

        final var body3cell21 =
                new Cell(new Paragraph("3.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell21.setBorder(NO_BORDER);
        body3cell21.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell21);

        final var body3cell22 =
                new Cell(new Paragraph("Auto renditeenuste lõpetamiseks kohustub rentnik teavitada oma soovist renditud auto tagastada "+ model.getDuration1()+ " päeva enne järgmist esmaspäeva.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell22.setBorder(NO_BORDER);
        body3cell22.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell22);

        final var body3cell23 =
                new Cell(new Paragraph("3.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3cell23.setBorder(NO_BORDER);
        body3cell23.setHorizontalAlignment(LEFT);
        body3.addCell(body3cell23);

        final var body3cell24 =
                new Cell(new Paragraph("Rentniku kohustused Rendileandja eest muutuvad sissenõutavaks  vastavalt lepingus sätestatud üldtingimustele.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell24.setBorder(NO_BORDER);
        body3cell24.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell24);

        contractPdfDoc.add(body3);


        final var body4 = new Table(2);
        body4.setWidths(new float[]{1, 20});
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
                new Cell(new Paragraph(" Lõppsätted ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body4cell2.setBorder(NO_BORDER);
        body4cell2.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell2);

 /*       final var body4cell3 =
                new Cell(new Paragraph("IV.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell3.setBorder(NO_BORDER);
        body4cell3.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell3);

        final var body4cell4 =
                new Cell(new Paragraph("Rendiauto informatsioon ja seisund, ning informatsioon üleantud tagatise kohta" +
                        " auto üleandmise ajaks on määratud Üleandmise / Vastuvõtmise aktis.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell4.setBorder(NO_BORDER);
        body4cell4.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell4);
*/
        final var body4cell5 =
                new Cell(new Paragraph("4.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell5.setBorder(NO_BORDER);
        body4cell5.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell5);

        final var body4cell6 =
                new Cell(new Paragraph("Rendilepingust tulenevad vaidlused, milles Rentnik ja Rendileandja ei jõua kokkuleppele, " +
                        "lahendatakse Harju Maakohtus vastavalt seadusele. Vaidluse läbivaatamisel kohtus rakendatakse käesoleva lepingu tingimusi.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell6.setBorder(NO_BORDER);
        body4cell6.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell6);

        final var body4cell7 =
                new Cell(new Paragraph("4.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell7.setBorder(NO_BORDER);
        body4cell7.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell7);

        final var body4cell8 =
                new Cell(new Paragraph("Pooled lepivad kokku, et Lepingu tingimusteks ei loeta Poolte varasemaid tahteavaldusi, " +
                        "tegusid ega kokkuleppeid, mis ei ole Lepingus ega üldtingimustes otseselt sätestatud. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell8.setBorder(NO_BORDER);
        body4cell8.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell8);

        final var body4cell9 =
                new Cell(new Paragraph("4.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell9.setBorder(NO_BORDER);
        body4cell9.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell9);

        final var body4cell10 =
                new Cell(new Paragraph("Kõik käesoleva lepingu tingimustes tehtud muudatused loetakse kehtivaks ainult siis, " +
                        "kui need on tehtud kirjalikult kehtiva lepingu lisana koos nende tingimustega nõustumise kinnitusega mõlema poole" +
                        " allkirjade kujul käesoleval dokumendil. Kõik muud arutelud ja kokkulepped loetakse tühiseks. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell10.setBorder(NO_BORDER);
        body4cell10.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell10);

        contractPdfDoc.add(body4);

// NEW ADD CONTRACT

        final var rendileandja2 = new Table(1);
        rendileandja2.setPadding(0f);
        rendileandja2.setSpacing(0f);
        rendileandja2.setWidth(100f);
        rendileandja2.setBorderColor(white);
        rendileandja2.setHorizontalAlignment(LEFT);
        rendileandja2.setBorder(NO_BORDER);
        rendileandja2.setBorder(NO_BORDER);


        final var rendileandja2cell2 =
                new Cell(new Paragraph( model.getQFirmName() + " rendilepingu sõiduauto taksoteenuse ja majandustegevuse kasutamiseks tüüptingimused", new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        rendileandja2cell2.setBorder(NO_BORDER);
        rendileandja2cell2.setHorizontalAlignment(LEFT);
        rendileandja2.addCell(rendileandja2cell2);

        final var rendileandja2cell3 =
                new Cell(new Paragraph("Kehtivad alates 01.06.2024", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
        rendileandja2cell3.setBorder(NO_BORDER);
        rendileandja2cell3.setHorizontalAlignment(LEFT);
        rendileandja2.addCell(rendileandja2cell3);

        contractPdfDoc.add(rendileandja2);


 // end add header
        final var body1a = new Table(2);
        body1a.setWidths(new float[]{1, 20});
        body1a.setPadding(0f);
        body1a.setSpacing(0f);
        body1a.setWidth(100f);
        body1a.setBorderColor(white);
        body1a.setHorizontalAlignment(LEFT);
        body1a.setBorder(NO_BORDER);
        body1a.setBorder(NO_BORDER);

        final var body1acell1 =
                new Cell(new Paragraph("I.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body1acell1.setBorder(NO_BORDER);
        body1acell1.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell1);

        final var body1acell2 =
                new Cell(new Paragraph("ÜLDSÄTTED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body1acell2.setBorder(NO_BORDER);
        body1acell2.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell2);

        final var body1acell3 =
                new Cell(new Paragraph("1.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell3.setBorder(NO_BORDER);
        body1acell3.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell3);

        final var body1acell4 =
                new Cell(new Paragraph("Käesolevad juriidilise isiku Rendileandja renditeenuste kasutamise tingimused (tingimused), sätestavad:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell4.setBorder(NO_BORDER);
        body1acell4.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell4);

        final var body1acell5 =
                new Cell(new Paragraph("1.1.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell5.setBorder(NO_BORDER);
        body1acell5.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell5);

        final var body1acell6 =
                new Cell(new Paragraph("sõiduki rentimist",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell6.setBorder(NO_BORDER);
        body1acell6.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell6);

        final var body1acell7 =
                new Cell(new Paragraph("1.1.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell7.setBorder(NO_BORDER);
        body1acell7.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell7);

        final var body1acell8 =
                new Cell(new Paragraph("sõiduki ja vara kasutamise tingimused ja nõuded",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell8.setBorder(NO_BORDER);
        body1acell8.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell8);

        final var body1acell9 =
                new Cell(new Paragraph("1.1.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell9.setBorder(NO_BORDER);
        body1acell9.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell9);

        final var body1acell10 =
                new Cell(new Paragraph("Rentniku vastutuse tingimused ja piirid",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell10.setBorder(NO_BORDER);
        body1acell10.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell10);

        final var body1acell11 =
                new Cell(new Paragraph("1.1.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell11.setBorder(NO_BORDER);
        body1acell11.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell11);

        final var body1acell12 =
                new Cell(new Paragraph("maksetingimused ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell12.setBorder(NO_BORDER);
        body1acell12.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell12);

        final var body1acell13 =
                new Cell(new Paragraph("1.1.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell13.setBorder(NO_BORDER);
        body1acell13.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell13);

        final var body1acell14 =
                new Cell(new Paragraph("mistahes muud suhted seoses teenuste kasutamisega.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell14.setBorder(NO_BORDER);
        body1acell14.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell14);

        final var body1acell15 =
                new Cell(new Paragraph("1.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell15.setBorder(NO_BORDER);
        body1acell15.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell15);

        final var body1acell16 =
                new Cell(new Paragraph("Alates vastavalt VÕS § 339 rendilepingu sätestatule sõlmivad Rentnik ja Rendileandja lepingulise õigussuhte," +
                        "mida reguleerivad käesolevad tingimused (sealhulgas selle lisad), hinnakiri, teenuse hinnad ja muud sõiduki rentimise eritingimused (leping).",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell16.setBorder(NO_BORDER);
        body1acell16.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell16);

        final var body1acell17 =
                new Cell(new Paragraph("1.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell17.setBorder(NO_BORDER);
        body1acell17.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell17);

        final var body1acell18 =
                new Cell(new Paragraph("Enne sõiduki renti võtmist peab Rentnik tutvuma hinnakirja ja muude renditingimustega. " +
                        "Rendilepingu sõlmimisel loetakse, et Rentnik on teenuse hindadest, hinnakirjast ja muudest rentimistingimustest teadlik ja on nendega nõustunud.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell18.setBorder(NO_BORDER);
        body1acell18.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell18);

        final var body1acell19 =
                new Cell(new Paragraph("1.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell19.setBorder(NO_BORDER);
        body1acell19.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell19);

        final var body1acell20 =
                new Cell(new Paragraph("Kui tingimuste sätestatud allikates esineb vastuolusid või lahknevusi, tõlgendatakse ja kohaldatakse lepingut järgmise prioriteetsuse alusel:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell20.setBorder(NO_BORDER);
        body1acell20.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell20);

        final var body1acell21 =
                new Cell(new Paragraph("1.4.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell21.setBorder(NO_BORDER);
        body1acell21.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell21);

        final var body1acell22 =
                new Cell(new Paragraph("rendilepingust toodud eritingimused, teenuse hinnad ja muud konkreetse sõiduki renditingimused;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell22.setBorder(NO_BORDER);
        body1acell22.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell22);

        final var body1acell23 =
                new Cell(new Paragraph("1.4.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body1acell23.setBorder(NO_BORDER);
        body1acell23.setHorizontalAlignment(LEFT);
        body1a.addCell(body1acell23);

        final var body1acell24 =
                new Cell(new Paragraph("käesolevad tingimused;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body1acell24.setBorder(NO_BORDER);
        body1acell24.setHorizontalAlignment(JUSTIFIED);
        body1a.addCell(body1acell24);

        contractPdfDoc.add(body1a);


        final var body2a = new Table(2);
        body2a.setWidths(new float[]{1, 20});
        body2a.setPadding(0f);
        body2a.setSpacing(0f);
        body2a.setWidth(100f);
        body2a.setBorderColor(white);
        body2a.setHorizontalAlignment(LEFT);
        body2a.setBorder(NO_BORDER);
        body2a.setBorder(NO_BORDER);

        final var body2acell1 =
                new Cell(new Paragraph("II.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body2acell1.setBorder(NO_BORDER);
        body2acell1.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell1);

        final var body2acell2 =
                new Cell(new Paragraph("MÕISTED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body2acell2.setBorder(NO_BORDER);
        body2acell2.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell2);

        final var body2acell3 =
                new Cell(new Paragraph("2.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell3.setBorder(NO_BORDER);
        body2acell3.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell3);

        final var body2acell4 =
                new Cell(new Paragraph("Rendileandja – "  + model.getQFirmName(),
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell4.setBorder(NO_BORDER);
        body2acell4.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell4);

       final var body2acell5 =
                new Cell(new Paragraph("2.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell5.setBorder(NO_BORDER);
        body2acell5.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell5);

        final var body2acell6 =
                new Cell(new Paragraph("Hinnakiri - lepingus ja/või eritingimustes ja/või auto üleandmise aktis fikseeritud rendi hind, trahvid, muud tasud ja lõivud. " +
                        "Käesolevate tingimustega nõustudes nõustub Rentnik samal ajal ka käesolevate tingimuste lahutamatuks osaks oleva hinnakirjaga.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell6.setBorder(NO_BORDER);
        body2acell6.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell6);

        final var body2acell7 =
                new Cell(new Paragraph("2.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell7.setBorder(NO_BORDER);
        body2acell7.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell7);

        final var body2acell8 =
                new Cell(new Paragraph("Liikluseeskirjad - asjaomases riigis kehtivad liikluseeskirjad ja nendega seotud õigusaktide sätted." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell8.setBorder(NO_BORDER);
        body2acell8.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell8);

        final var body2acell9 =
                new Cell(new Paragraph("2.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell9.setBorder(NO_BORDER);
        body2acell9.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell9);

        final var body2acell10 =
                new Cell(new Paragraph("Rentnik - Rendileandja klient (füüsiline ja/või juriidiline isik), kes nõustub käesolevate tingimustega ja kasutab vastavalt " +
                        "lepingule rendiauto teenuseid. Rentnikul on õigus kasutada sõidukit ainult siis, kui ta on vähemalt" +
                        " 21 aastat vana ja omab mootorsõiduki juhistaaži vähemalt kaks aastat. " ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell10.setBorder(NO_BORDER);
        body2acell10.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell10);

        final var body2acell11 =
                new Cell(new Paragraph("2.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell11.setBorder(NO_BORDER);
        body2acell11.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell11);

        final var body2acell12 =
                new Cell(new Paragraph("Kasutusperiood - ajavahemik sõiduki üleandmise-vastuvõtmise akti alusel võtmise hetkest kuni sõiduki tagastamise vastuvõtmise " +
                        "akti allkirjastamiseni. Kasutusperioodist lepitakse eraldi kokku lepingu eritingimustes. Kasutusperiood on arvestatud kalendri täisnädala kaupa ja " +
                        "rendinädalaid peetakse ajaperioodi alates uue nädala Esmaspäeva kella 10:00’ni kuni järgmise nädala Esmaspäeva kella 10:00’ni.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell12.setBorder(NO_BORDER);
        body2acell12.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell12);

        final var body2acell13 =
                new Cell(new Paragraph("2.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell13.setBorder(NO_BORDER);
        body2acell13.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell13);

        final var body2acell14 =
                new Cell(new Paragraph("Teenused - Rendileandja poolt Rentnikule osutatavad autorendi teenused vastavalt VÕS  § 399 sätestatule, mille käigus Rendileandja " +
                        "annab teisele isikule (rentnikule) kasutamiseks rendilepingu eseme ning võimaldab talle rendilepingu esemest korrapärase majandamise reeglite järgi saadava vilja. " +
                        "Rentnik on kohustatud maksma selle eest tasu (renti).  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell14.setBorder(NO_BORDER);
        body2acell14.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell14);

        final var body2acell15 =
                new Cell(new Paragraph("2.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell15.setBorder(NO_BORDER);
        body2acell15.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell15);

        final var body2acell16 =
                new Cell(new Paragraph("Üleandmise-vastuvõtmise akt - kirjalik dokument, millega fikseeritakse rendiauto registreerimisnumbrit, üleandmise-vastuvõtmise kuupäeva, " +
                        "nädala rendihinda, auto seisundit (vigastusi ja puudusi) üleandmise ajal, ning Rendileandja ja Rentniku nõusolekut nimetatud üleandmise-vastuvõtmise akti andmetega," +
                        " mis kinnitatakse poolte allkirjadega.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell16.setBorder(NO_BORDER);
        body2acell16.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell16);

        final var body2acell17 =
                new Cell(new Paragraph("2.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell17.setBorder(NO_BORDER);
        body2acell17.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell17);

        final var body2acell18 =
                new Cell(new Paragraph("Rendiauto kahjustuste fikseerimise akt on kirjalik dokument millega fikseeritakse rendiesemele tekitatud vigastused selle" +
                        " rentniku kasutamise aja jooksul. Akt sisaldab vigastuste kirjeldust ja määrab nende asukohti. Võimalusel lisatakse aktile kahjustuste fotosid. " ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell18.setBorder(NO_BORDER);
        body2acell18.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell18);

        final var body2acell19 =
                new Cell(new Paragraph("2.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell19.setBorder(NO_BORDER);
        body2acell19.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell19);

        final var body2acell20 =
                new Cell(new Paragraph("Rendiauto (sõiduk) - vaba auto Rendileandja autopargist. Rendilepingu kohaselt kohustub Rendileandja andma Rentnikule " +
                        "kasutamiseks rendilepingu ning käesoleva tingimuste sätestatu alusel vaba sõidukit oma autopargist - rendilepingu eseme ehk Rendiauto." +
                        " Rentnik on kohustatud maksma selle eest tasu (Renti) Rendileandjale kogu Rendiperioodi eest." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell20.setBorder(NO_BORDER);
        body2acell20.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell20);

        final var body2acell21 =
                new Cell(new Paragraph("2.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell21.setBorder(NO_BORDER);
        body2acell21.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell21);

        final var body2acell22 =
                new Cell(new Paragraph("Rent - tasu rendiauto kasutamise perioodi eest vastavalt lepingus, aktis ja tüüptingimustes toodule." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell22.setBorder(NO_BORDER);
        body2acell22.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell22);

        final var body2acell23 =
                new Cell(new Paragraph("2.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell23.setBorder(NO_BORDER);
        body2acell23.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell23);

        final var body2acell24 =
                new Cell(new Paragraph("Rendi arvutamise põhimõtted - lepinguline nädala renditasu suurus üleandmise-vastuvõtmise hetkeks" +
                        " sõltub üleantava auto omadustest ja määratakse eraldi iga auto üleandmise-vastuvõtmise aktis. Üleandmise-vastuvõtmise aktis " +
                        "määratud renditasu on aktuaalne vaid täisrendinädala eest tasumise puhul. Täpsemalt sätestatud p.12.3." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell24.setBorder(NO_BORDER);
        body2acell24.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell24);

        final var body2acell25 =
                new Cell(new Paragraph("2.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell25.setBorder(NO_BORDER);
        body2acell25.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell25);

        final var body2acell26 =
                new Cell(new Paragraph("Leping - autorendi teenuste osutamise leping. Rentniku ja Rendiandja vahel sõlmitud teenuste osutamise " +
                        "leping loetakse sõlmituks alates allakirjutamise hetkest. Lepingut reguleerivad sätted, nagu on sätestatud käesolevate tingimuste p. 1.2. "  ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell26.setBorder(NO_BORDER);
        body2acell26.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell26);

        final var body2acell27 =
                new Cell(new Paragraph("2.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell27.setBorder(NO_BORDER);
        body2acell27.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell27);

        final var body2acell28 =
                new Cell(new Paragraph("Käendus - erikokkuleppe sõlmitud lepingus eritingimusena." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell28.setBorder(NO_BORDER);
        body2acell28.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell28);

        final var body2acell29 =
                new Cell(new Paragraph("2.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell29.setBorder(NO_BORDER);
        body2acell29.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell29);

        final var body2acell30 =
                new Cell(new Paragraph("Võlalimiit –  võla summa mille ületamisel loetakse leping oluliselt rikutuks. " ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell30.setBorder(NO_BORDER);
        body2acell30.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell30);

        final var body2acell31 =
                new Cell(new Paragraph("2.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2acell31.setBorder(NO_BORDER);
        body2acell31.setHorizontalAlignment(LEFT);
        body2a.addCell(body2acell31);

        final var body2acell32 =
                new Cell(new Paragraph("Muude käesolevates tingimustes kasutatud mõistete tähendus vastab tingimuste p. 1.2 nimetatud allikates sätestatud tähendusele." ,
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2acell32.setBorder(NO_BORDER);
        body2acell32.setHorizontalAlignment(JUSTIFIED);
        body2a.addCell(body2acell32);

        contractPdfDoc.add(body2a);



        final var body3a = new Table(2);
        body3a.setWidths(new float[]{1, 20});
        body3a.setPadding(0f);
        body3a.setSpacing(0f);
        body3a.setWidth(100f);
        body3a.setBorderColor(white);
        body3a.setHorizontalAlignment(LEFT);
        body3a.setBorder(NO_BORDER);
        body3a.setBorder(NO_BORDER);

        final var body3acell1 =
                new Cell(new Paragraph("III.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body3acell1.setBorder(NO_BORDER);
        body3acell1.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell1);

        final var body3acell2 =
                new Cell(new Paragraph("SÕIDUKI KASUTUSTINGIMUSED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body3acell2.setBorder(NO_BORDER);
        body3acell2.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell2);

        final var body3acell3 =
                new Cell(new Paragraph("3.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell3.setBorder(NO_BORDER);
        body3acell3.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell3);

        final var body3acell4 =
                new Cell(new Paragraph("Rendileandja kohustub tagama, et rendiauto on heas sõidukorras ja selle vahetuks otstarbeks kasutamiseks ja käitamiseks sobilik, võttes arvesse sõiduki tavapärast kulumist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell4.setBorder(NO_BORDER);
        body3acell4.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell4);

        final var body3acell5 =
                new Cell(new Paragraph("3.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell5.setBorder(NO_BORDER);
        body3acell5.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell5);

        final var body3acell6 =
                new Cell(new Paragraph("Defektideks ei loeta rikkeid ja talitlushäireid, mis ei mõjuta praegu ega lähitulevikus liiklusohutust (nt: kriimustused seadmete sise- " +
                        "ja välispindadel, varuosad, multimeediaseadmete talitlushäireid, andurite rikked). Puudusi fikseeritakse üleandmise-vastuvõtmise aktis.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell6.setBorder(NO_BORDER);
        body3acell6.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell6);

        final var body3acell7 =
                new Cell(new Paragraph("3.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell7.setBorder(NO_BORDER);
        body3acell7.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell7);

        final var body3acell8 =
                new Cell(new Paragraph("Renditeenuse kasutamisel peab Rentnik muuhulgas:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell8.setBorder(NO_BORDER);
        body3acell8.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell8);

        final var body3acell9 =
                new Cell(new Paragraph("3.3.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell9.setBorder(NO_BORDER);
        body3acell9.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell9);

        final var body3acell10 =
                new Cell(new Paragraph("Rentnik on kohustatud vaatama sõiduki üle enne" +
                        " selle vastuvõtmist ja veenduma selle sobilikkuses ja korrasolekus, tegema vastavasisulise märke autol olemasolevatest kahjustustest " +
                        "üleandmise-vastuvõtmise aktil ja panna oma allkirja nende kinnitamiseks. Rentniku allkiri kinnitab tema nõusolekut ainult " +
                        "üleandmise-vastuvõtmise aktil märgitud kahjustuste olemasoluga. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell10.setBorder(NO_BORDER);
        body3acell10.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell10);

        final var body3acell11 =
                new Cell(new Paragraph("3.3.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell11.setBorder(NO_BORDER);
        body3acell11.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell11);

        final var body3acell12 =
                new Cell(new Paragraph("täitma sõiduki käitamisnõudeid, sealhulgas mistahes käesolevates tingimustes nimetamata nõudeid, mis on selliste esemete kasutamisel tavapärased; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell12.setBorder(NO_BORDER);
        body3acell12.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell12);

        final var body3acell13 =
                new Cell(new Paragraph("3.3.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell13.setBorder(NO_BORDER);
        body3acell13.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell13);

        final var body3acell14 =
                new Cell(new Paragraph("sõitma tähelepanelikult, ettevaatlikult, viisakalt ja ohutult, austades teisi liiklejaid ja inimesi," +
                        " võttes kõik vajalikud ettevaatusabinõud ja ohustamata teiste liiklejate, teiste inimeste või nende vara ja keskkonna ohutust;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell14.setBorder(NO_BORDER);
        body3acell14.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell14);

        final var body3acell15 =
                new Cell(new Paragraph("3.3.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell15.setBorder(NO_BORDER);
        body3acell15.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell15);

        final var body3acell16 =
                new Cell(new Paragraph("käituma piisavalt ettevaatlikult, mõistlikult, vastutustundlikult ja teadlikult;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell16.setBorder(NO_BORDER);
        body3acell16.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell16);

        final var body3acell17 =
                new Cell(new Paragraph("3.3.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell17.setBorder(NO_BORDER);
        body3acell17.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell17);

        final var body3acell18 =
                new Cell(new Paragraph("olema täiesti kaine (0,00 promilli) ja mitte vaimset seisundit mõjutavate ainete mõju all;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell18.setBorder(NO_BORDER);
        body3acell18.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell18);

        final var body3acell19 =
                new Cell(new Paragraph("3.3.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell19.setBorder(NO_BORDER);
        body3acell19.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell19);

        final var body3acell20 =
                new Cell(new Paragraph("mitte juhtima sõidukit haige või väsinuna, kui sõiduki juhtimine võib ohustada liiklusohutust " +
                        "või kui esineb mõni muu põhjus, miks Rentnik ei saa sõidukit vastavalt õigusaktides sätestatud nõuetele ohutult juhtida;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell20.setBorder(NO_BORDER);
        body3acell20.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell20);

        final var body3acell21 =
                new Cell(new Paragraph("3.3.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell21.setBorder(NO_BORDER);
        body3acell21.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell21);

        final var body3acell22 =
                new Cell(new Paragraph("Rentnikul ei ole õigust lubada teistel isikutel sõidukit juhtida, kontrollida või kasutada, sõidukit edasi rentida, käesolevates tingimustes sätestatud mistahes õigusi või kohustusi üle anda; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell22.setBorder(NO_BORDER);
        body3acell22.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell22);

        final var body3acell23 =
                new Cell(new Paragraph("3.3.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell23.setBorder(NO_BORDER);
        body3acell23.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell23);

        final var body3acell24 =
                new Cell(new Paragraph("Rentnikul ei ole õigust kopeerida, muuta või kustutada sõiduki süsteemis olevaid andmeid, omastada, hävitada või muul viisil kahjustada sõidukis olevaid sõiduki dokumente (nt tehnilist passi); ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell24.setBorder(NO_BORDER);
        body3acell24.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell24);

        final var body3acell25 =
                new Cell(new Paragraph("3.3.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell25.setBorder(NO_BORDER);
        body3acell25.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell25);

        final var body3acell26 =
                new Cell(new Paragraph("Rentnikule on keelatud anda üle autoga seotud dokumendid ning poolte suhted reguleerivad dokumendid kolmandatele isikutele ilma teise poole nõusolekuta. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell26.setBorder(NO_BORDER);
        body3acell26.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell26);

        final var body3acell27 =
                new Cell(new Paragraph("3.3.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell27.setBorder(NO_BORDER);
        body3acell27.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell27);

        final var body3acell28 =
                new Cell(new Paragraph("Rentnikul ei ole õigust sõidukit lahti monteerida, remontida või muuta;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell28.setBorder(NO_BORDER);
        body3acell28.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell28);

        final var body3acell29 =
                new Cell(new Paragraph("3.3.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell29.setBorder(NO_BORDER);
        body3acell29.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell29);

        final var body3acell30 =
                new Cell(new Paragraph("Rentnikul  ei ole õigust vedada sõidukis plahvatusohtlikke, tuleohtlikke, mürgiseid aineid või inimelule või " +
                        "-tervisele kahjulikke aineid jms ega õigust kasutada sõidukis või sõiduki lähedal kütteseadmeid, lahtist tuld või muid võimalikke tuleallikaid;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell30.setBorder(NO_BORDER);
        body3acell30.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell30);

        final var body3acell31 =
                new Cell(new Paragraph("3.3.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell31.setBorder(NO_BORDER);
        body3acell31.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell31);

        final var body3acell32 =
                new Cell(new Paragraph("Rentnikul ei ole õigust kasutada sõidukit eesmärkidel, milleks see ei ole ette nähtud või kohandatud, nt kauba " +
                        "vedamiseks või kasutada sõidukit suurema koorma vedamiseks (raskete veoste vedamiseks jne), suurte loomade vedamiseks, samuti metsas, veekogudes" +
                        " ja muudel maastikel sõitmiseks, sõidukit üle koormata, koormat mitte nõuetekohaselt kinnitada ja paigutada; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell32.setBorder(NO_BORDER);
        body3acell32.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell32);

        final var body3acell33 =
                new Cell(new Paragraph("3.3.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell33.setBorder(NO_BORDER);
        body3acell33.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell33);

        final var body3acell34 =
                new Cell(new Paragraph("Rentnikul ei ole õigust kasutada sõidukit võidusõitudel, võistlustel ega muudel spordi või võidusõiduga seotud eesmärkidel;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell34.setBorder(NO_BORDER);
        body3acell34.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell34);

        final var body3acell35 =
                new Cell(new Paragraph("3.3.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell35.setBorder(NO_BORDER);
        body3acell35.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell35);

        final var body3acell36 =
                new Cell(new Paragraph("Rentnikul ei ole õigust kasutada sõidukit õppesõidukina ega kasutada sõidukit teiste sõidukite vedamiseks; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell36.setBorder(NO_BORDER);
        body3acell36.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell36);

        final var body3acell37 =
                new Cell(new Paragraph("3.3.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell37.setBorder(NO_BORDER);
        body3acell37.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell37);

        final var body3acell38 =
                new Cell(new Paragraph("täitma liikluseeskirju; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell38.setBorder(NO_BORDER);
        body3acell38.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell38);

        final var body3acell39 =
                new Cell(new Paragraph("3.3.16",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell39.setBorder(NO_BORDER);
        body3acell39.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell39);

        final var body3acell40 =
                new Cell(new Paragraph("kaitsma sõidukit, kasutama sõidukit ja selles olevat vara hoolikalt, võtma kõik mõistlikud meetmed sõiduki turvalisuse tagamiseks (st lukustama sõiduki, sulgema aknad, lülitama tuled ja muusikaseadme välja jne);",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell40.setBorder(NO_BORDER);
        body3acell40.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell40);

        final var body3acell41 =
                new Cell(new Paragraph("3.3.17",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell41.setBorder(NO_BORDER);
        body3acell41.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell41);

        final var body3acell42 =
                new Cell(new Paragraph("tagama, et A. sõidukis ei suitsetata ja B. väikseid lemmikloomi veetakse selleks ette nähtud spetsiaalses transpordikastis;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell42.setBorder(NO_BORDER);
        body3acell42.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell42);

        final var body3acell43 =
                new Cell(new Paragraph("3.3.18",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell43.setBorder(NO_BORDER);
        body3acell43.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell43);

        final var body3acell44 =
                new Cell(new Paragraph("enne sõidukiga sõitma asumist peab Rentnik veenduma, et sõidukil ei ole ilmseid rikkeid ja/või defekte ning nende olemasolu korral viivitamatult teavitama Rendileandja taasesitamist võimaldavas vormis; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell44.setBorder(NO_BORDER);
        body3acell44.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell44);

        final var body3acell45 =
                new Cell(new Paragraph("3.3.19",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell45.setBorder(NO_BORDER);
        body3acell45.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell45);

        final var body3acell46 =
                new Cell(new Paragraph("täitma muid õigusaktides sätestatud nõudeid. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell46.setBorder(NO_BORDER);
        body3acell46.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell46);

        final var body3acell47 =
                new Cell(new Paragraph("3.3.20",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body3acell47.setBorder(NO_BORDER);
        body3acell47.setHorizontalAlignment(LEFT);
        body3a.addCell(body3acell47);

        final var body3acell48 =
                new Cell(new Paragraph("Sõiduki võib kasutada ainult Eesti territooriumil. Rentnikul on lubatud kasutada sõidukit väljaspool Eesti territooriumi üksnes Rendileandja " +
                        "eelneval kirjalikul nõusolekul. Ettevõte otsustab nõusoleku andmise pärast seda, kui on hinnanud Rentnikul taotluse individuaalselt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3acell48.setBorder(NO_BORDER);
        body3acell48.setHorizontalAlignment(JUSTIFIED);
        body3a.addCell(body3acell48);

       contractPdfDoc.add(body3a);



        final var body4a = new Table(2);
        body4a.setWidths(new float[]{1, 20});
        body4a.setPadding(0f);
        body4a.setSpacing(0f);
        body4a.setWidth(100f);
        body4a.setBorderColor(white);
        body4a.setHorizontalAlignment(LEFT);
        body4a.setBorder(NO_BORDER);
        body4a.setBorder(NO_BORDER);

        final var body4acell1 =
                new Cell(new Paragraph("IV.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body4acell1.setBorder(NO_BORDER);
        body4acell1.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell1);

        final var body4acell2 =
                new Cell(new Paragraph(" SÕIDUKI KÄTTESAAMINE JA TAGASTAMINE  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body4acell2.setBorder(NO_BORDER);
        body4acell2.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell2);

        final var body4acell3 =
                new Cell(new Paragraph("4.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell3.setBorder(NO_BORDER);
        body4acell3.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell3);

        final var body4acell4 =
                new Cell(new Paragraph("Sõiduki valjastamise koht on Lasnamäe 30a, Tallinn. Sõiduk antakse Rendileandja poolt üle Rentnikule kirjaliku poolte poolt allakirjutatud akti alusel. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell4.setBorder(NO_BORDER);
        body4acell4.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell4);

        final var body4acell5 =
                new Cell(new Paragraph("4.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell5.setBorder(NO_BORDER);
        body4acell5.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell5);

        final var body4acell6 =
                new Cell(new Paragraph("Sõiduki kasutamise periood ei ole piiratud. Pooled võivad lepingu eritingimustes leppida kokku minimaalsest ja maksimaalsest renditeenuse kestusest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell6.setBorder(NO_BORDER);
        body4acell6.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell6);

        final var body4acell7 =
                new Cell(new Paragraph("4.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell7.setBorder(NO_BORDER);
        body4acell7.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell7);

        final var body4acell8 =
                new Cell(new Paragraph("Sõiduki tagastamiskoht on Lasnamäe 30a, Tallinn. Sõiduk tagastatakse Rendileandjale Rendileandja ja Rentniku poolt kirjaliku allkirjastatud akti alusel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell8.setBorder(NO_BORDER);
        body4acell8.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell8);

        final var body4acell9 =
                new Cell(new Paragraph("4.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell9.setBorder(NO_BORDER);
        body4acell9.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell9);

        final var body4acell10 =
                new Cell(new Paragraph("Rentnik on kohustatud kirjaliku üleandmise -vastuvõtmise akti alusel tagastama sõiduki lepingus määratud Rendiperioodi lõppemisel kokkulepitud kohas ja ettenähtud ajal. Sõidukit ei tohi maha jätta. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell10.setBorder(NO_BORDER);
        body4acell10.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell10);

        final var body4acell11 =
                new Cell(new Paragraph("4.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell11.setBorder(NO_BORDER);
        body4acell11.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell11);

        final var body4acell12 =
                new Cell(new Paragraph("Rentnik vastutab täies ulatuses ka puuduste eest, mis on tekkinud sõidukil, mis on tagastatud Rendileandjale ilma kirjaliku üleandmise-vastuvõtmise aktita, kui ei ole tõendatud vastupidi ja/või algses üleandmise aktis märgitud. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell12.setBorder(NO_BORDER);
        body4acell12.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell12);

        final var body4acell13 =
                new Cell(new Paragraph("4.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell13.setBorder(NO_BORDER);
        body4acell13.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell13);

        final var body4acell14 =
                new Cell(new Paragraph("Tagatise summa arvestatakse lähtudes iga rendiauto omadusest eraldi, lepitakse kokku lepingutingimustega ning märgitakse lepingus või üleandmise aktis." +
                        " Tagatise summa määr on 300 eurot, mis muutub tasumiseks kohustuslikuks viivitamatult peale rendilepingu allkirjastamist." +
                        " Tagatise summa tagastatakse rentnikule mitte varem kui kolm nädalat parast auto rendiandjale tagastamist. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell14.setBorder(NO_BORDER);
        body4acell14.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell14);

        final var body4acell15 =
                new Cell(new Paragraph("4.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell15.setBorder(NO_BORDER);
        body4acell15.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell15);

        final var body4acell16 =
                new Cell(new Paragraph("Rentnik peab tagastama rendiauto seisukorras, mis ei ole kehvem kui seisukord, milles selle kätte saadi ja võttes arvesse selle kulumist. Sõiduki " +
                        "kulumine määratakse vastavalt Eesti Liisinguühingute Liidu poolt koostatud ja avaldatud „Sõidukite loomuliku ja ebaloomuliku kulumise määramise juhendile“," +
                        " mis on avaldatud liidu kodulehel https://www.liisingliit.ee/regulatsioon/juhendid-ja-aktid (juhend loetakse tingimuste lahutamatuks osaks), ja riikliku tehnoülevaatuse eeskirjale. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell16.setBorder(NO_BORDER);
        body4acell16.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell16);

        final var body4acell17 =
                new Cell(new Paragraph("4.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell17.setBorder(NO_BORDER);
        body4acell17.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell17);

        final var body4acell18 =
                new Cell(new Paragraph("Kulumine ei hõlma:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell18.setBorder(NO_BORDER);
        body4acell18.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell18);

        final var body4acell19 =
                new Cell(new Paragraph("4.8.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell19.setBorder(NO_BORDER);
        body4acell19.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell19);

        final var body4acell20 =
                new Cell(new Paragraph("purunenud, deformeerunud või muul viisil mehaaniliselt või termiliselt kahjustatud osi, seadmeid ja mehhanisme;.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell20.setBorder(NO_BORDER);
        body4acell20.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell20);

        final var body4acell21 =
                new Cell(new Paragraph("4.8.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell21.setBorder(NO_BORDER);
        body4acell21.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell21);

        final var body4acell22 =
                new Cell(new Paragraph("sõiduki keres olevaid mõlke, värvikihis olevaid pragusid ja nähtavaid kriimustusi.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell22.setBorder(NO_BORDER);
        body4acell22.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell22);

        final var body4acell23 =
                new Cell(new Paragraph("4.8.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell23.setBorder(NO_BORDER);
        body4acell23.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell23);

        final var body4acell24 =
                new Cell(new Paragraph("värvikihi kulumist sõiduki tugeva pesemise/puhastamise tõttu;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell24.setBorder(NO_BORDER);
        body4acell24.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell24);

        final var body4acell25 =
                new Cell(new Paragraph("4.8.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell25.setBorder(NO_BORDER);
        body4acell25.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell25);

        final var body4acell26 =
                new Cell(new Paragraph("kehva kvaliteediga remonditöid ja/või remonditöödest tulenevaid defekte (vaatamata sellele, et Rentnikul ei ole õigust sõidukit ise ega kolmandate isikute kaudu remontida);",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell26.setBorder(NO_BORDER);
        body4acell26.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell26);

        final var body4acell27 =
                new Cell(new Paragraph("4.8.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell27.setBorder(NO_BORDER);
        body4acell27.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell27);

        final var body4acell28 =
                new Cell(new Paragraph("sõidukikere akendel olevaid pragusid;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell28.setBorder(NO_BORDER);
        body4acell28.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell28);

        final var body4acell29 =
                new Cell(new Paragraph("4.8.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell29.setBorder(NO_BORDER);
        body4acell29.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell29);

        final var body4acell30 =
                new Cell(new Paragraph("sõiduki valest kasutamisest ja/või puhastamisest tingitud kriimustusi sõidukikere akendel;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell30.setBorder(NO_BORDER);
        body4acell30.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell30);

        final var body4acell31 =
                new Cell(new Paragraph("4.8.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell31.setBorder(NO_BORDER);
        body4acell31.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell31);

        final var body4acell32 =
                new Cell(new Paragraph("salongi kahjustusi ja määrdumist, näiteks põlenud või määrdunud istmeid, katkiseid armatuurlaua osi või muid plastosi, pagasiruumi luuki, akende avamise käepidemeid jms;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell32.setBorder(NO_BORDER);
        body4acell32.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell32);

        final var body4acell33 =
                new Cell(new Paragraph("4.8.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4acell33.setBorder(NO_BORDER);
        body4acell33.setHorizontalAlignment(LEFT);
        body4a.addCell(body4acell33);

        final var body4acell34 =
                new Cell(new Paragraph("sõidukikere geomeetria vigastusi.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4acell34.setBorder(NO_BORDER);
        body4acell34.setHorizontalAlignment(JUSTIFIED);
        body4a.addCell(body4acell34);

        contractPdfDoc.add(body4a);
       

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
                new Cell(new Paragraph(" SÜNDMUSED KASUTUSEPERIOODIL ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body5cell2.setBorder(NO_BORDER);
        body5cell2.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell2);

        final var body5cell3 =
                new Cell(new Paragraph("5.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell3.setBorder(NO_BORDER);
        body5cell3.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell3);

        final var body5cell4 =
                new Cell(new Paragraph("Kui sõiduk läheb rikki, sõiduki armatuurlaual hakkab põlema mistahes hoiatus, kuulda on kahtlast " +
                        "kummalist heli või sõidukit ei ole võimalik enam ohutult kasutada ja juhtida, peab Rentnik koheselt: \n" +
                        "a) lõpetama sõiduki kasutamise;\n" +
                        "b) teavitama Rendileandja sellest telefoni teel ja\n" +
                        "c) täitma muid Rendileandja juhiseid.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell4.setBorder(NO_BORDER);
        body5cell4.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell4);

        final var body5cell5 =
                new Cell(new Paragraph("5.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell5.setBorder(NO_BORDER);
        body5cell5.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell5);

        final var body5cell6 =
                new Cell(new Paragraph("Liiklusõnnetuse korral või sõiduki, kolmandate isikute või nende vara vigastamisel, " +
                        "Rendileandja või selle vara mistahes muul viisil kahjustamisel peab Rentnik Rendileandjat sellest koheselt teavitama ja vajadusel " +
                        "teavitama asjakohaseid riigiasutusi või teenistusi (politsei, tuletõrje jne), täitma liiklusõnnetuse deklaratsiooni ja/või tegema muid vajalikke toiminguid, " +
                        "mida tuleb vastavalt kohaldatavatele õigusaktidele teha, samuti toiminguid, mida tuleb teha sõidukile, muule varale ja/või isikutele suurema kahju tekkimise vältimiseks või kahju vähendamiseks.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell6.setBorder(NO_BORDER);
        body5cell6.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell6);

        final var body5cell7 =
                new Cell(new Paragraph("5.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell7.setBorder(NO_BORDER);
        body5cell7.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell7);

        final var body5cell8 =
                new Cell(new Paragraph("Liiklusõnnetuse korral, milles pole Rentnik süüdi, tuleb liiklusõnnetuses osalenud juhtidel täita nõuetekohaselt " +
                        "LE vorm nr.1 või kutsuda sündmuskohale politsei, et liiklusõnnetuse asjaolusid fikseerida. Selle LE vorm nr.1 või politsei poolt koostatud" +
                        " sündmuskoha skeemi koopia mitteesitamisel Rendileandjale, on Rentnik täies ulatuses vastutav Rendileandjale tekitatud kahjude eest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell8.setBorder(NO_BORDER);
        body5cell8.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell8);

        final var body5cell9 =
                new Cell(new Paragraph("5.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell9.setBorder(NO_BORDER);
        body5cell9.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell9);

        final var body5cell10 =
                new Cell(new Paragraph("Kahju tekkimisel Rendileandjale või kolmandatele isikutele või dokumentide või esemete kadumisel on Rentnik " +
                        "kohustatud esitama Rendileandjale kirjaliku seletuse juhtunu kohta hiljemalt 24 tunni möödumisel. Kui puudub muu võimalus kirjaliku seletuse " +
                        "andmiseks, võib seda esitada Rendileandjale erandkorras ka lepingus toodud e-posti aadressile, varustades seletuse digitaalallkirjaga. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell10.setBorder(NO_BORDER);
        body5cell10.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell10);

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
                new Cell(new Paragraph(" VASTUTUS",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body6cell2.setBorder(NO_BORDER);
        body6cell2.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell2);

        final var body6cell3 =
                new Cell(new Paragraph("6.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell3.setBorder(NO_BORDER);
        body6cell3.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell3);

        final var body6cell4 =
                new Cell(new Paragraph(" Hüvitamise üldsätted",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell4.setBorder(NO_BORDER);
        body6cell4.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell4);

        final var body6cell5 =
                new Cell(new Paragraph("6.1.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell5.setBorder(NO_BORDER);
        body6cell5.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell5);

        final var body6cell6 =
                new Cell(new Paragraph("Rentnik kui suurema ohu allika valdaja vastutab kogu sõiduki kasutusperioodi jooksul täielikult tingimuste ja " +
                        "õigusaktide rikkumise eest ning Rendileandjale, sõidukile ja/või kolmandatele isikutele tekitatud kahju eest.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell6.setBorder(NO_BORDER);
        body6cell6.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell6);

        final var body6cell7 =
                new Cell(new Paragraph("6.1.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell7.setBorder(NO_BORDER);
        body6cell7.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell7);

        final var body6cell8 =
                new Cell(new Paragraph("Rentnik vastutab koos Rentnikuga sõidukit kasutavate isikute (nt kaassõitjate) ohutuse, tervise ja elu eest," +
                        " samuti enda vara või teiste isikute vara kahjustamise, hävimise või kaotsimineku eest, kui kohaldatavates õigusaktides ei ole sätestatud teisiti.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell8.setBorder(NO_BORDER);
        body6cell8.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell8);

        final var body6cell9 =
                new Cell(new Paragraph("6.1.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell9.setBorder(NO_BORDER);
        body6cell9.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell9);

        final var body6cell10 =
                new Cell(new Paragraph("Mitte ükski käesolevate tingimuste säte ei piira Rendileandjal õigust nõuda võlakohustuste täitmist kolmandate isikute" +
                        " poolt (vastavalt lepinguvälisele vastutusele), kes põhjustasid oma tegevuse või tegevusetusega Rendileandjale kahju, kuid selline Rendileandja " +
                        "õigus ei piira mingil juhul Rentniku eelnevalt nimetatud vastutust.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell10.setBorder(NO_BORDER);
        body6cell10.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell10);

        final var body6cell11 =
                new Cell(new Paragraph("6.1.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell11.setBorder(NO_BORDER);
        body6cell11.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell11);

        final var body6cell12 =
                new Cell(new Paragraph("Käesolevate tingimuste tähenduses on Rendileandja poolt kantud kahju(d) järgmine(sed) (sealhulgas, kuid mitteainult):\n" +
                        "a.\tRendileandja mainele, firmaväärtusele ja heale nimele, kaubamärgile ja ärinimele, Rendileandja üldjuhtimise põhimõtetele, samuti Rendileandja sotsiaalsele kuvandile tekitatud kahju;\n" +
                        "b.\tsõidukile (sealhulgas selle väärtuse vähenemine), selle Rendileandjale või teistele isikutele kuuluvatele osadele ja varale, sealhulgas sõiduki lisatarvikutele tekitatud kahju;\n" +
                        "c.\tkõik sõiduki transportimise, turvalisuse, puhastamise, parkimise, remonditöödega seotud kulud (nii tegelikult kantud kui ka veel kandmata kulud, mille sõltumatu hindaja on kindlaks teinud ja hinnanud kui vajalikud sõiduki remondikulud);\n" +
                        "d.\tkahju hindamise, kindlakstegemise, kahjukäsitluse ja asjaajamisega seotud kulud;\n" +
                        "e.\tsõiduki müügi, võõrandamisega seotud kulud;\n" +
                        "f.\tvõlgnevuse sissenõudmise kulud;\n" +
                        "g.\tkaudne kahju (nt saamata jäänud tulu, sõiduki jõude seismise aeg);\n" +
                        "h.\tkahju ärahoidmise või vähendamisega seotud kulud;\n" +
                        "i.\tkindlustushüvitised, mis jäävad Rendileandjale välja maksmata Rentniku poolt tingimuste rikkumise tõttu.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell12.setBorder(NO_BORDER);
        body6cell12.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell12);

        final var body6cell13 =
                new Cell(new Paragraph("6.1.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell13.setBorder(NO_BORDER);
        body6cell13.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell13);

        final var body6cell14 =
                new Cell(new Paragraph("Ilma et see piiraks käesolevate tingimuste mistahes sätteid, vastutab Rentnik täielikult sõiduki kahjustamise eest:\n" +
                        "a.\tRentnik vastutab kogu kahju eest, mis on tema tegevuse ja või tegevusetuse tulemusena on tekitatud sõidukile (avarii, väline kahju, kriimustamised, deformeerimised jne. " +
                        "Nimekiri ei ole lõplik).  Kahju hüvitamiseks pooled lähtuvad VÕS sätestatutest ja üleandmise-vastuvõtmise aktis märgitud andmetest.\n" +
                        "b.\tkui sõiduk, selle lisatarvikud või nende mõni osa varastatakse või saab kahjustada, sest juht jättis aknad või katuseluugid lahti, katuse peale tõmbamata, uksed lukustamata jne;\n" +
                        "c.\tkui sõiduk või selle mõni osa varastatakse või saab kahjustada isiku poolt, kes kasutas sõidukit koos Rentnikuga või Rentniku teadmisel ja tahtel.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell14.setBorder(NO_BORDER);
        body6cell14.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell14);

        final var body6cell15 =
                new Cell(new Paragraph("6.1.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell15.setBorder(NO_BORDER);
        body6cell15.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell15);

        final var body6cell16 =
                new Cell(new Paragraph("Juhul kui tingimusi rikub või muid ebaseaduslikke tegevusi või tegematajätmisi teeb ja/või kahju Rendileandjale ja/või teistele isikutele," +
                        " sealhulgas Rentnikule endale põhjustab kolmas isik, kellele Rentnik lubab, annab nõusoleku, annab üle või muul viisil võimaldab või teeb võimalikuks või mistahes muul viisil " +
                        "loob oma aktiivse või passiivse tegevuse ja/või tegevusetusega otseselt või kaudselt, tahtlikult või hooletuse tõttu kolmandale isikule või kolmandate isikute rühmale võimaluse" +
                        " pääseda sõidukisse, juhtida ja/või kasutada muul viisil sõidukit, selle lisatarvikuid ja/või  ei takista seda:\n" +
                        "a.\t kannab Rentnik kõiki kolmandate isikute tegevusest või tegevusetusest tingitud tingimuste ja seaduste rikkumisest tulenevaid riske, vastutust ja kahju ja/või " +
                        "vastutab Rendileandjale ja/või kolmandatele isikutele põhjustatud kahju eest;\n" +
                        "b.\tkohaldatakse Rentniku suhtes tingimustes sätestatud trahve, kahjuhüvitisi ja muud vastutust ning nendest tulenevaid tagajärgi, lugedes sellised tegevused ja " +
                        "rikkumised Rentnik enda poolt toime panduks ja kahju Rentnik enda poolt põhjustatuks;\n" +
                        "c.\tLepingus nimetatud trahvid, parkimistrahvid, kiiruseületamise trahvid ja muud järelevalve organite poolt määratud trahvid Rentnik kohustatud tasuma 7 (seitsme) " +
                        "päeva jooksul pärast Rentnikule trahvi teatavaks tegemisest ning kättetoimetamisest. Kättetoimetamine võib olla elektronile, sms, e-kiri, füüsiliselt kätte andmine jne.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell16.setBorder(NO_BORDER);
        body6cell16.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell16);

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
                new Cell(new Paragraph(" TRAHVID ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body7cell2.setBorder(NO_BORDER);
        body7cell2.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell2);

        final var body7cell3 =
                new Cell(new Paragraph("7.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell3.setBorder(NO_BORDER);
        body7cell3.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell3);

        final var body7cell4 =
                new Cell(new Paragraph("Tingimustes käsitletakse trahve ja nende määramise tingimusi, põhimõtteid ja korda, kuid konkreetsed trahviliigid ja nende konkreetsed summad on toodud lepingus ja või käesolevates tingimustes.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell4.setBorder(NO_BORDER);
        body7cell4.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell4);

        final var body7cell5 =
                new Cell(new Paragraph("7.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell5.setBorder(NO_BORDER);
        body7cell5.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell5);

        final var body7cell6 =
                new Cell(new Paragraph("Igal juhul Rentnik peab enne rendilepingu sõlmimist tutvuma kohaldatava ja hetkel kehtiva trahvinimekirjaga. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell6.setBorder(NO_BORDER);
        body7cell6.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell6);

        final var body7cell7 =
                new Cell(new Paragraph("7.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell7.setBorder(NO_BORDER);
        body7cell7.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell7);

        final var body7cell8 =
                new Cell(new Paragraph("Tingimustes käsitletavad ja lepingus loetletud trahvid loetakse Rendileandja poolt määravaks leppetrahviks.   ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell8.setBorder(NO_BORDER);
        body7cell8.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell8);

        final var body7cell9 =
                new Cell(new Paragraph("7.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell9.setBorder(NO_BORDER);
        body7cell9.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell9);

        final var body7cell10 =
                new Cell(new Paragraph("Kõik tingimustes ja lepingus nimetatud trahvid, välja arvatud tingimustes konkreetselt sätestatud erandid, on kõikehõlmavad, " +
                        "st need sisaldavad Rendileandjale tekitatud kahju. Lisaks sellele peab Rentnik trahvi maksmisel hüvitama Rendileandjale kõik täiendavad summad või täiendavad " +
                        "kahjuliigid, mida tasutud trahv ei kata. Trahvi tasumine ei vabasta Rentnikku kohustusest hüvitada kõik muud Rendileandja poolt kantud kahjud, " +
                        "mida tasutud trahv ei kata. Lisaks ei vabasta trahvi määramine Rentnikku kohustusest täita muid käesolevates tingimustes ja/või õigusaktides sätestatud " +
                        "kohustusi ulatuses, milles Rentniku poolt tasutud trahv ei kata või asenda selliseid kohustusi vastavalt nende olemusele või sisule. Kõik erinevad " +
                        "Rentniku individuaalsetest tegevustest põhjustatud kahjuliigid määratletakse ja neid hinnatakse eraldi, isegi kui need on põhjustatud samal ajal." +
                        " Rentniku sellistest individuaalsetest tegevustest põhjustatud individuaalsete/erinevate kahjuliikide hüvitamine (trahvi tasumine ja/või kahju hüvitamine) " +
                        "ei ole vastastiku kaasav ja seda kohaldatakse iga kahjuliigi ja seda kahjuliiki põhjustanud Rentniku vastavate tegevuste suhtes eraldi. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell10.setBorder(NO_BORDER);
        body7cell10.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell10);

        final var body7cell11 =
                new Cell(new Paragraph("7.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell11.setBorder(NO_BORDER);
        body7cell11.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell11);

        final var body7cell12 =
                new Cell(new Paragraph("Rentnik peab maksma Rendileandjale hinnakirjas toodud summas trahvi muu hulgas järgmistel juhtudel (allpool toodud juhtude loetelu on toodud üksnes illustreerimiseks ja trahvide ammendav loetelu on toodud hinnakirjas): ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell12.setBorder(NO_BORDER);
        body7cell12.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell12);

        final var body7cell13 =
                new Cell(new Paragraph("7.5.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell13.setBorder(NO_BORDER);
        body7cell13.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell13);

        final var body7cell14 =
                new Cell(new Paragraph("sõiduki, selle osade, lisatarvikute, lisaseadmete (sealhulgas sõiduki võtme) või seadmete kahjustamise või kaotsimineku korral;  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell14.setBorder(NO_BORDER);
        body7cell14.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell14);

        final var body7cell15 =
                new Cell(new Paragraph("7.5.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell15.setBorder(NO_BORDER);
        body7cell15.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell15);

        final var body7cell16 =
                new Cell(new Paragraph("sõidukis suitsetamise korral; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell16.setBorder(NO_BORDER);
        body7cell16.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell16);

        final var body7cell17 =
                new Cell(new Paragraph("7.5.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell17.setBorder(NO_BORDER);
        body7cell17.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell17);

        final var body7cell18 =
                new Cell(new Paragraph("ohtliku, hoolimatu või hooletu sõitmise korral; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell18.setBorder(NO_BORDER);
        body7cell18.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell18);

        final var body7cell19 =
                new Cell(new Paragraph("7.5.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell19.setBorder(NO_BORDER);
        body7cell19.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell19);

        final var body7cell20 =
                new Cell(new Paragraph("määrdunud, musta sõiduki korral, kui sõiduk on määrdunum kui sõidukite tavapärase kasutamise korral " +
                        "(näiteks maastikul, metsas, veekogudes, madalsoodes, mägedel, ainult eritranspordi või spetsiaalselt ettevalmistatud sõidukitega " +
                        "ligipääsetavates kohtades sõitmisel või liikluseeskirjade rikkumisel); ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell20.setBorder(NO_BORDER);
        body7cell20.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell20);

        final var body7cell21 =
                new Cell(new Paragraph("7.5.5 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell21.setBorder(NO_BORDER);
        body7cell21.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell21);

        final var body7cell22 =
                new Cell(new Paragraph("alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete ja muude vaimset seisundit mõjutavate ainete mõju all " +
                        "sõitmise korral (või kui Rentnik tarvitas alkoholi või muid joovastavaid aineid pärast liiklusõnnetust enne liiklusõnnetuse asjaolude tuvastamist või " +
                        "vältis vere alkoholisisalduse testi või joobetesti tegemist (käesolevate tingimuste tähenduses tähendab vere alkoholisisaldus või joove õigusaktides sätestatut)." +
                        " Rentnik peab maksma Rendileandjale alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete või muude vaimset seisundit mõjutavate ainete mõju all sõitmise eest" +
                        " hinnakirjas nimetatud summas trahvi ka juhul, kui Rentnik andis sõiduki üle või tegi sõiduki juhtimise muul viisil võimalikuks teisele isikule, kui isik oli " +
                        "alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete või muude vaimset seisundit mõjutavate ainete mõju all või kui isik vältis vere alkoholisisalduse testi või joobetesti tegemist; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell22.setBorder(NO_BORDER);
        body7cell22.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell22);

        final var body7cell23 =
                new Cell(new Paragraph("7.5.6 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell23.setBorder(NO_BORDER);
        body7cell23.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell23);

        final var body7cell24 =
                new Cell(new Paragraph("sõiduki või selle juurde kuuluva vara seadusevastase omastamise või kaotsimineku korral; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell24.setBorder(NO_BORDER);
        body7cell24.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell24);

        final var body7cell25 =
                new Cell(new Paragraph("7.5.7 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell25.setBorder(NO_BORDER);
        body7cell25.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell25);

        final var body7cell26 =
                new Cell(new Paragraph("käesolevate tingimuste või õigusaktide muude sätete rikkumise korral. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body7cell26.setBorder(NO_BORDER);
        body7cell26.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell26);

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
                new Cell(new Paragraph(" TRAHVIDE ERISÄTTED ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body8cell2.setBorder(NO_BORDER);
        body8cell2.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell2);

        final var body8cell3 =
                new Cell(new Paragraph("8.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell3.setBorder(NO_BORDER);
        body8cell3.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell3);

        final var body8cell4 =
                new Cell(new Paragraph(" Hinnakirjas märgitud trahv alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete ja muude vaimset seisundit mõjutavate " +
                        "ainete mõju all sõitmise eest (või kui Rentnik tarvitas alkoholi või muid joovastavaid aineid pärast liiklusõnnetust enne liiklusõnnetuse asjaolude tuvastamist " +
                        "või vältis vere alkoholisisalduse testi või joobetesti tegemist) loetakse Rentniku ja Rendileandja poolt eelnevalt kokkulepitud leppetrahviks kahju põhjustamise " +
                        "eest Rendileandja mainele, firmaväärtusele ja heale nimele, kaubamärkidele ja ärinimele, Rendileandja üldjuhtimise põhimõtetele, samuti Rendileandja sotsiaalsele " +
                        "kuvandile, samuti on see mõeldud kõikide muude Rendileandjale tekitatud ebamugavuste, piirangute jms eest, mis on tingitud sellest, et Rentnik ei täida nõuetekohaselt " +
                        "tingimustes sätestatud nõudeid või jätab need täitmata. Eelnevalt nimetatud trahv tagab ka seda, et Rentnik täidab nõuetekohaselt kohustust mitte sõita alkoholijoobes " +
                        "(üle 0,00 promilli) või narkootiliste ainete või muude vaimset seisundit mõjutavate ainete mõju all, nagu on täpsemalt kirjeldatud käesolevates tingimustes, ning täidab sellega seotud ennetavat funktsiooni. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell4.setBorder(NO_BORDER);
        body8cell4.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell4);

        final var body8cell5 =
                new Cell(new Paragraph("8.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell5.setBorder(NO_BORDER);
        body8cell5.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell5);

        final var body8cell6 =
                new Cell(new Paragraph(" Liiklusõnnetusest või kolmandaisiku õigusvastasest käitumisest tekkinud kahju kannab Rentnik ulatuses, mida ei kanna kindlustus (s.h. omavastutuse määra)." +
                        " Kui kindlustusfirma keeldub kindlustushüvitise väljamaksmisest või kui kahjujuhtum ei ole kindlustusjuhtum, kohustub Rentnik tasuma Rendileandjale sõiduki täisväärtuse ja hüvitama tekkinud kahjud.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell6.setBorder(NO_BORDER);
        body8cell6.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell6);

        final var body8cell7 =
                new Cell(new Paragraph("8.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell7.setBorder(NO_BORDER);
        body8cell7.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell7);

        final var body8cell8 =
                new Cell(new Paragraph(" Kui rentnik annab sõidukit üle kolmandale isikule, kannab Rentnik täielikult Rendileandjale või kolmandatele isikutele tekkinud kahju.   ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell8.setBorder(NO_BORDER);
        body8cell8.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell8);

        final var body8cell9 =
                new Cell(new Paragraph("8.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell9.setBorder(NO_BORDER);
        body8cell9.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell9);

        final var body8cell10 =
                new Cell(new Paragraph(" Kui Rendiauto on varastatud, ärandatud või röövitud, on Rentnik vastutav sõiduki täisväärtuse ulatuses ja kohustub hüvitama Rendileandjale tekitatud kahjud.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell10.setBorder(NO_BORDER);
        body8cell10.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell10);

        final var body8cell11 =
                new Cell(new Paragraph("8.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell11.setBorder(NO_BORDER);
        body8cell11.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell11);

        final var body8cell12 =
                new Cell(new Paragraph(" Kui Rendileandjale tagastatud sõiduk vajab remonti, kannab Rentnik iga remondipäeva eest lepingus kokkulepitud rendipäeva hinnale lisaks ka remondikulud.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell12.setBorder(NO_BORDER);
        body8cell12.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell12);

        final var body8cell13 =
                new Cell(new Paragraph("8.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell13.setBorder(NO_BORDER);
        body8cell13.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell13);

        final var body8cell14 =
                new Cell(new Paragraph(" Ebakvaliteetsest kütusest tekkinud kahjud kannab Rentnik. Kui sõiduki kütusepaaki on täidetud vale kütusega," +
                        " hüvitab Rentnik Rendileandjale sellest tingitud kahju vastavalt kahju kõrvaldava teenusepakkuja poolt väljastatud arvele, lähtudes tegelikult tekitatud kahjust.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell14.setBorder(NO_BORDER);
        body8cell14.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell14);

        final var body8cell15 =
                new Cell(new Paragraph("8.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell15.setBorder(NO_BORDER);
        body8cell15.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell15);

        final var body8cell16 =
                new Cell(new Paragraph(" Sõiduki dokumentide või puuduliku varustusega sõiduki tagastamisel tasub Rentnik Rendileandjale leppetrahvi 250.-EUR iga kaotatud või puuduva dokumendi või eseme kohta ja hüvitab tekkinud kahjud.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell16.setBorder(NO_BORDER);
        body8cell16.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell16);

        final var body8cell17 =
                new Cell(new Paragraph("8.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell17.setBorder(NO_BORDER);
        body8cell17.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell17);

        final var body8cell18 =
                new Cell(new Paragraph(" Sõiduki võtmete mittetagastamisel või kaotamisel tasub Rentnik Rendileandja kahju vastavalt uue võtme soetamise ning tarkvara seadistamise arvele, mida üldjuhul väljastab autoesindus. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell18.setBorder(NO_BORDER);
        body8cell18.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell18);

        final var body8cell19 =
                new Cell(new Paragraph("8.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell19.setBorder(NO_BORDER);
        body8cell19.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell19);

        final var body8cell20 =
                new Cell(new Paragraph(" Rentniku poolt sõiduki hävitamisel tasub Rentnik Rendileandjale leppetrahvi sõiduki soetusmaksumuse ulatuses.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell20.setBorder(NO_BORDER);
        body8cell20.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell20);

        final var body8cell21 =
                new Cell(new Paragraph("8.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell21.setBorder(NO_BORDER);
        body8cell21.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell21);

        final var body8cell22 =
                new Cell(new Paragraph(" Rentnik on kohustatud lähtuma p.6.1.6/c - ning tasuma vara kasutamise käigus põhjustatud seaduserikkumiste" +
                        " korral kõik trahvid ja nõuded ning leppetrahvid vastavalt seaduses sätestatud korrale ning Parkimistrahvid kaasa arvatud nendega seotud kulud, " +
                        "millest Rentniku poolt Rendileandjat ei ole teavitatud ega tasutud 7 päeva jooksul, nõutakse hiljem Rentnikult sisse kolmekordselt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell22.setBorder(NO_BORDER);
        body8cell22.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell22);

        final var body8cell23 =
                new Cell(new Paragraph("8.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell23.setBorder(NO_BORDER);
        body8cell23.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell23);

        final var body8cell24 =
                new Cell(new Paragraph(" Kui Rentnik osaleb Rendisõidukiga liiklusõnnetuses, mille tõttu Rendileandja kindlustusriski koefitsent suureneb, tasub Rentnik ühekordset leppetrahvi 300.-EUR.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell24.setBorder(NO_BORDER);
        body8cell24.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell24);

        final var body8cell25 =
                new Cell(new Paragraph("8.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell25.setBorder(NO_BORDER);
        body8cell25.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell25);

        final var body8cell26 =
                new Cell(new Paragraph(" Rentniku süül liiklusõnnetuse korral hüvitab Rentnik Rendileandjale tekitatud kahju täies mahus. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell26.setBorder(NO_BORDER);
        body8cell26.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell26);

        final var body8cell27 =
                new Cell(new Paragraph("8.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell27.setBorder(NO_BORDER);
        body8cell27.setHorizontalAlignment(LEFT);
        body8.addCell(body8cell27);

        final var body8cell28 =
                new Cell(new Paragraph(" Selguse huvides peab ettevõte tagama, et kõik sõidukid on kindlustatud kohustusliku mootorsõidukite kasutamise tsiviilvastutuskindlustusega, mis vastab Eesti " +
                        "liikluskindlustuse seadusele või samalaadsele kohustuslikku liikluskindlustust reguleerivale muu riigi õigusaktile. Ettevõte võib, aga ei ole kohustatud pakkuma sõidukitele täiendavat " +
                        "kindlustust (nt vabatahtlikku liikluskindlustust KASKO). Rentnikul on õigus vormistada vabatahtlikku KASKO autokindlustust enda nimelt. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body8cell28.setBorder(NO_BORDER);
        body8cell28.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell28);

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
                new Cell(new Paragraph("KAHJU HINDAMINE. KAHJUKÄSITLUS ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body9cell2.setBorder(NO_BORDER);
        body9cell2.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell2);

        final var body9cell3 =
                new Cell(new Paragraph("9.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell3.setBorder(NO_BORDER);
        body9cell3.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell3);

        final var body9cell4 =
                new Cell(new Paragraph("Kui Rendileandja kannab kahju (välja arvatud juhul, kui kahjusumma sisaldub poolte vahel eelnevalt kokkulepitud leppetrahvis (trahvides), " +
                        "mille summad on märgitud hinnakirjas ja või muus kokkuleppes), määratakse Rendileandja poolt kantud kahju(de) summa kahjuhindaja ja/või muude teenuseosutajate kaasamise teel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell4.setBorder(NO_BORDER);
        body9cell4.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell4);

        final var body9cell5 =
                new Cell(new Paragraph("9.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell5.setBorder(NO_BORDER);
        body9cell5.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell5);

        final var body9cell6 =
                new Cell(new Paragraph("Sõidukile tekitatud kahju ja Rendileandja poolt kantud kahju(d) määratakse kindlaks vastavalt sõidukite ja muu vara hindamise ja väärtuse määramise metoodikale, " +
                        "kahju hindamise metoodikale ja eeskirjadele, mida kahjukäsitluse ekspert peab vastavalt Eesti Vabariigis kehtivatele õigusaktidele sõiduki kahju hindamisel järgima.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell6.setBorder(NO_BORDER);
        body9cell6.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell6);

        final var body9cell7 =
                new Cell(new Paragraph("9.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell7.setBorder(NO_BORDER);
        body9cell7.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell7);

        final var body9cell8 =
                new Cell(new Paragraph("Rentnik võib 7 (seitsme) päeva jooksul esitada oma põhjendatud vastuväited Rendileandja või Rendileandja poolt kaasatud kahjukäsitluse " +
                        "eksperdi poolt teostatud kahju hindamisele, esitades Rentniku poolt kaasatud sõltumatu sertifitseeritud (litsentseeritud) hindaja kahju hindamise ja väärtuse " +
                        "hindamise aruande, mis vastab sellise hindamise ja dokumentide suhtes kohaldatavatele õigusnõuetele (edaspidi nimetatud „alternatiivne kahjuaruanne“). " +
                        "Rentniku esitatud alternatiivset kahjuaruannet ja muid Rentniku poolt Rendileandjale esitatud dokumente hinnatakse koos muu Rendileandja ja Rendileandja poolt " +
                        "kaasatud kahjukäsitluse eksperdi ja muude teenuseosutajate poolt kogutud ja koostatud teabega. Kui pooltel on kahjusumma osas mistahes lahkarvamusi, esitab " +
                        "küsimuses lõpliku järelduse Rendileandja poolt kaasatud kahjukäsitluse ekspert, kelle järeldused on Rendileandjale ja Rentnikule kohustuslikud. " +
                        "Rentnik katab kõik alternatiivse kahjuaruande ja Rentniku või kolmandate isikute poolt palgatud sõltumatu kahjuhindaja tööga seotud kulud.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell8.setBorder(NO_BORDER);
        body9cell8.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell8);

        final var body9cell9 =
                new Cell(new Paragraph("9.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell9.setBorder(NO_BORDER);
        body9cell9.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell9);

        final var body9cell10 =
                new Cell(new Paragraph("Rentnik katab Rendileandja poolt kantud kahju hindamise, korrigeerimise ja haldamise kulud, samuti kõik alternatiivse või täiendava uurimise või kahju hindamisega seotud kulud. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell10.setBorder(NO_BORDER);
        body9cell10.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell10);

        final var body9cell11 =
                new Cell(new Paragraph("9.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell11.setBorder(NO_BORDER);
        body9cell11.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell11);

        final var body9cell12 =
                new Cell(new Paragraph("Rentnik kannab kõik riigi poolt liikluseeskirjade rikkumise eest määratud trahvid isegi juhul, kui sõidukit ei juhtinud Rentnik. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell12.setBorder(NO_BORDER);
        body9cell12.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell12);

        final var body9cell13 =
                new Cell(new Paragraph("9.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell13.setBorder(NO_BORDER);
        body9cell13.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell13);

        final var body9cell14 =
                new Cell(new Paragraph("Tingimustes sätestatud juhtudel katab Rentnik ka Rendileandja poolt kantud kulud seoses Rentnik poolt põhjustatud kahju või" +
                        " võlgnevuse haldamisega, välja arvatud juhul, kui sellised kulud katab juba hinnakirjas märgitud trahvisumma.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body9cell14.setBorder(NO_BORDER);
        body9cell14.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell14);

        contractPdfDoc.add(body9);


        final var body10 = new Table(2);
        body10.setWidths(new float[]{1, 20});
        body10.setPadding(0f);
        body10.setSpacing(0f);
        body10.setWidth(100f);
        body10.setBorderColor(white);
        body10.setHorizontalAlignment(LEFT);
        body10.setBorder(NO_BORDER);
        body10.setBorder(NO_BORDER);

        final var body10cell1 =
                new Cell(new Paragraph("X.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body10cell1.setBorder(NO_BORDER);
        body10cell1.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell1);

        final var body10cell2 =
                new Cell(new Paragraph("VÄÄRTEOTRAHVID, MAKSUD JA TASUD ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body10cell2.setBorder(NO_BORDER);
        body10cell2.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell2);

        final var body10cell3 =
                new Cell(new Paragraph("10.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell3.setBorder(NO_BORDER);
        body10cell3.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell3);

        final var body10cell4 =
                new Cell(new Paragraph("Kõik haldus- või muud liiki trahvid, maksud, tasud, muud tasumisele kuuluvad summad, mis tulenevad sõiduki ebaõigest ja (või) ebaseaduslikust " +
                        "kasutamisest või õigusaktide rikkumisest Rentnik poolt, kannab Rentnik. Juhul, kui haldus- või muud liiki trahvid, maksud, tasud, muud tasumisele kuuluvad summad " +
                        "nõutakse sisse Rendileandjalt, on Rendileandjal regressiõigus, et need summad Rentnikult täies ulatuses automaatselt kätte saada ja tagasi nõuda. Pärast politseilt ja " +
                        "teistelt pädevatelt asutustelt ning juriidilistelt isikutelt liiklusrikkumiste või muude rikkumiste, päringute või taotluste kohta teabe saamist annab ettevõte " +
                        "nendele teavet konkreetse Rentnik kui isiku kohta, kes kasutas vastavat sõidukit konkreetsel teenuste kasutamise ajal.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body10cell4.setBorder(NO_BORDER);
        body10cell4.setHorizontalAlignment(JUSTIFIED);
        body10.addCell(body10cell4);

        

        contractPdfDoc.add(body10);


        final var body11a = new Table(2);
        body11a.setWidths(new float[]{1, 20});
        body11a.setPadding(0f);
        body11a.setSpacing(0f);
        body11a.setWidth(100f);
        body11a.setBorderColor(white);
        body11a.setHorizontalAlignment(LEFT);
        body11a.setBorder(NO_BORDER);
        body11a.setBorder(NO_BORDER);

        final var body11acell1 =
                new Cell(new Paragraph("XI.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body11acell1.setBorder(NO_BORDER);
        body11acell1.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell1);

        final var body11acell2 =
                new Cell(new Paragraph("RENDILEANDJA VASTUTUS ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body11acell2.setBorder(NO_BORDER);
        body11acell2.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell2);

        final var body11acell3 =
                new Cell(new Paragraph("11.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell3.setBorder(NO_BORDER);
        body11acell3.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell3);

        final var body11acell4 =
                new Cell(new Paragraph("Rendileandja vastutab käesolevates tingimustes sätestatud kohustuste täitmise eest ja hüvitab " +
                        "Rentnikule Rendileandja kohustuste mittenõuetekohase täitmise tõttu tekkinud kahju üksnes juhul, kui kahju on tekkinud Rendileandja süül.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell4.setBorder(NO_BORDER);
        body11acell4.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell4);

        final var body11acell5 =
                new Cell(new Paragraph("11.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell5.setBorder(NO_BORDER);
        body11acell5.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell5);

        final var body11acell6 =
                new Cell(new Paragraph("Ilma et see piiraks ülaltoodud sätteid, ei vastuta Rendileandja kohaldatava seadusega lubatud ulatuses järgmise eest:",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell6.setBorder(NO_BORDER);
        body11acell6.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell6);

        final var body11acell7 =
                new Cell(new Paragraph("11.2.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell7.setBorder(NO_BORDER);
        body11acell7.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell7);

        final var body11acell8 =
                new Cell(new Paragraph("kahju, mida Rentnik kandis hilinemise (nt teatud kohta saabumisel hilinemine jms), teatud kuupäeva või kellaaja unustamise tõttu seoses renditeenuste kasutamisega või renditeenuste kasutamise võimatuse tõttu;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell8.setBorder(NO_BORDER);
        body11acell8.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell8);

        final var body11acell9 =
                new Cell(new Paragraph("11.2.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell9.setBorder(NO_BORDER);
        body11acell9.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell9);

        final var body11acell10 =
                new Cell(new Paragraph("kahju, mida Rentnik põhjustas renditeenuste kasutamisega kolmandatele isikutele või nende varale; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell10.setBorder(NO_BORDER);
        body11acell10.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell10);

        final var body11acell11 =
                new Cell(new Paragraph("11.2.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell11.setBorder(NO_BORDER);
        body11acell11.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell11);

        final var body11acell12 =
                new Cell(new Paragraph("kahju Rentniku varale, tervisele või elule renditeenuste kasutamisel; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell12.setBorder(NO_BORDER);
        body11acell12.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell12);

        final var body11acell13 =
                new Cell(new Paragraph("11.2.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell13.setBorder(NO_BORDER);
        body11acell13.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell13);

        final var body11acell14 =
                new Cell(new Paragraph("saamata jäänud tulu, sissetulek, äritegevus, kokkulepete või lepingute sõlmimise võimalus, tarkvara, " +
                        "andmete või teabe kasutamise võimaluse kahjustumine või kaotsiminek, maine kaotamine või kahjustumine; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell14.setBorder(NO_BORDER);
        body11acell14.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell14);

        final var body11acell15 =
                new Cell(new Paragraph("11.2.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body11acell15.setBorder(NO_BORDER);
        body11acell15.setHorizontalAlignment(LEFT);
        body11a.addCell(body11acell15);

        final var body11acell16 =
                new Cell(new Paragraph("kahju, mida Rentnik kandis seetõttu, et ei saanud sõidukit liiklusõnnetuse tõttu või muudel Rendileandjast sõltumatutel põhjustel kasutada; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body11acell16.setBorder(NO_BORDER);
        body11acell16.setHorizontalAlignment(JUSTIFIED);
        body11a.addCell(body11acell16);

        contractPdfDoc.add(body11a);



        final var body12a = new Table(2);
        body12a.setWidths(new float[]{2, 35});
        body12a.setPadding(0f);
        body12a.setSpacing(0f);
        body12a.setWidth(100f);
        body12a.setBorderColor(white);
        body12a.setHorizontalAlignment(LEFT);
        body12a.setBorder(NO_BORDER);
        body12a.setBorder(NO_BORDER);

        final var body12acell1 =
                new Cell(new Paragraph("XII.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body12acell1.setBorder(NO_BORDER);
        body12acell1.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell1);

        final var body12acell2 =
                new Cell(new Paragraph("TEENUSTE HIND. LISATASUD. MAKSETINGIMUSED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body12acell2.setBorder(NO_BORDER);
        body12acell2.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell2);

        final var body12acell3 =
                new Cell(new Paragraph("12.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell3.setBorder(NO_BORDER);
        body12acell3.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell3);

        final var body12acell4 =
                new Cell(new Paragraph("Rendiauto lepinguline nädala renditasu suurus lepitakse kokku üleandmise-vastuvõtmise hetkel ja sõltub üleantava auto omadustest ja määratakse eraldi auto üleandmise-vastuvõtmise aktis. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell4.setBorder(NO_BORDER);
        body12acell4.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell4);

        final var body12acell5 =
                new Cell(new Paragraph("12.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell5.setBorder(NO_BORDER);
        body12acell5.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell5);

        final var body12acell6 =
                new Cell(new Paragraph("Üleandmise-vastuvõtmise aktis määratud renditasu on aktuaalne vaid täisrendinädala eest tasumise puhul.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell6.setBorder(NO_BORDER);
        body12acell6.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell6);

        final var body12acell7 =
                new Cell(new Paragraph("12.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell7.setBorder(NO_BORDER);
        body12acell7.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell7);

        final var body12acell8 =
                new Cell(new Paragraph("Täisrendinädalat peetakse ajaperioodi iga kalendrinädala Esmaspäeva kella 10:00'st kuni järgneva kalendrinädala" +
                        " kella 10:00'ni - nimelt seitse päeva, millest on Pühapäev tasuta ja võib olla kasutatud puhkepäevana. Iga osalise autorendinädala ööpäeva maksumus, " +
                        "mida peetakse iga kalendripäeva kella 10:00'st kuni järgneva kalendripäeva kella 10:00'ni ajaperioodi,   on võrdne täisrendinädala rendimaksumusest viiendikuga.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell8.setBorder(NO_BORDER);
        body12acell8.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell8);

        final var body12acell9 =
                new Cell(new Paragraph("12.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell9.setBorder(NO_BORDER);
        body12acell9.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell9);

        final var body12acell10 =
                new Cell(new Paragraph("Rentnik kohustub tasuma rendileandjale ettemaksu iga tuleva nädala rendi eest sularahas Rendileandja kontoris," +
                        " mis asub aadressil Lasnamäe 30a, Tallinn, või ülekandega Rendileandja pangakontole (või muule Rendileandja esindajaga maaratud pangakontole) " +
                        "asjakohase selgitusega – ”autorent + auto number”, mitte hiljem, kui iga nädala teisipäeva kella 16:00’ni.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell10.setBorder(NO_BORDER);
        body12acell10.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell10);

        final var body12acell11 =
                new Cell(new Paragraph("12.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell11.setBorder(NO_BORDER);
        body12acell11.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell11);

        final var body12acell12 =
                new Cell(new Paragraph("Rentnik kohustub maksma tasu rendiperioodi eest. Renditasu ja muu rendiauto kasutamisest tulenevad kohustused hilinenud tasumise puhul on rentnik kohustatud tasuma viivist  0,1% kalendripäevas tasumata summalt.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell12.setBorder(NO_BORDER);
        body12acell12.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell12);

        final var body12acell13 =
                new Cell(new Paragraph("12.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell13.setBorder(NO_BORDER);
        body12acell13.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell13);

        final var body12acell14 =
                new Cell(new Paragraph("Rendileandjal on õigus omal äranägemisel määrata Rentnikule renditeenuste kasutamiseks maksimaalne võlalimiit. " +
                        "Rendiandjal on ainuõigus ühepoolselt omal äranägemisel seda limiiti igal ajal muuta, tühistada, vähendada või suurendada teavitades sellest Rentniku. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell14.setBorder(NO_BORDER);
        body12acell14.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell14);

        final var body12acell15 =
                new Cell(new Paragraph("12.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell15.setBorder(NO_BORDER);
        body12acell15.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell15);

        final var body12acell16 =
                new Cell(new Paragraph("Võlalimiit on 240 eurot, mille ületamisel võib Rendileandja renditeenuse osutamist peatada ja anda" +
                        " täiendava tähtaega kohustuse täitmiseks 14 päeva ning loeb lepingu oluliselt rikutuks juhul, kui Rentnik 14 päeva jooksul ei täida oma kohustused.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell16.setBorder(NO_BORDER);
        body12acell16.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell16);

        final var body12acell17 =
                new Cell(new Paragraph("12.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell17.setBorder(NO_BORDER);
        body12acell17.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell17);

        final var body12acell18 =
                new Cell(new Paragraph("Iganädalase rendimaksete aluseks on leping ja seal sätestatud tasumise tingimused ning auto üleandmise-vastuvõtmise akt. " +
                        "(Rendileping sõiduauto taksoteenuse ja majandustegevuse kasutamiseks). Rentnikule väljastatud arved omavad informatiivsed tähendust ning on aruanne möödunud perioodi eest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell18.setBorder(NO_BORDER);
        body12acell18.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell18);

        final var body12acell19 =
                new Cell(new Paragraph("12.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell19.setBorder(NO_BORDER);
        body12acell19.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell19);

        final var body12acell20 =
                new Cell(new Paragraph("Rendileandja väljastab arve möödunud perioodi/nädala eest võttes arvesse Rentniku täidetud ja/või täitmata " +
                        "jäetud kohustused viivitusega 2 nädalad. Arve annab informatsiooni täidetud ja või täitmata jäetud lepinguliste kohustuse kohta. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell20.setBorder(NO_BORDER);
        body12acell20.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell20);
//
        final var body12acell21 =
                new Cell(new Paragraph("12.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell21.setBorder(NO_BORDER);
        body12acell21.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell21);

        final var body12acell22 =
                new Cell(new Paragraph("Rendileandja väljastab ja esitab Rentnikule arved kõikide hinnakirjas märgitud trahvide, lisatasude ja muude summade eest vastavalt õigusaktides sätestatud korrale. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell22.setBorder(NO_BORDER);
        body12acell22.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell22);

        final var body12acell23 =
                new Cell(new Paragraph("12.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell23.setBorder(NO_BORDER);
        body12acell23.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell23);

        final var body12acell24 =
                new Cell(new Paragraph("Rendileandja poolt väljastatud arvete saamisel peab Rentnik 3 (kolme) päeva jooksul kontrollima, et arved on õiged ja mittevastavuste avastamisel " +
                        "ettevõtet sellest teavitama. Rentnik peab esitama kõik arvel toodud teabega seotud nõuded 3 (kolme) päeva jooksul pärast arve saamist. Kui Rentnik ei esita mistahesnõudeid " +
                        "ülalnimetatud tähtaja jooksul, loetakse, et Rentnik tagasivõtmatult aktsepteerib väljastatud arvet.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell24.setBorder(NO_BORDER);
        body12acell24.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell24);

        final var body12acell25 =
                new Cell(new Paragraph("12.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell25.setBorder(NO_BORDER);
        body12acell25.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell25);

        final var body12acell26 =
                new Cell(new Paragraph("Kui kõikide nimetatud ajavahemikute jooksul kliendile osutatud teenuste eest on täielikult tasutud, märgitakse Rentnikule saadetavas teates," +
                        " et tema poolt tasumisele kuuluv jääk on 0,00 eurot. Igal muul juhul märgitakse Rentnikule saadetavas teates tema poolt tasumisele kuuluv teenuste hinna jääk koos viivistega.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell26.setBorder(NO_BORDER);
        body12acell26.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell26);

        final var body12acell27 =
                new Cell(new Paragraph("12.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell27.setBorder(NO_BORDER);
        body12acell27.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell27);

        final var body12acell28 =
                new Cell(new Paragraph("Kui Rentnik ei tasu osutatud teenuste eest õigeaegselt ja ei tee seda Rendileandja poolt määratud mõistliku lisatähtaja jooksul, on Rendileandjal " +
                        "õigus volitada võla sissenõudmiseks inkassofirma või loovutada oma nõudeõigus Rentniku suhtes inkassofirmale või muule majandusüksusele. Ettevõte võib edastada Rendileandjal " +
                        "olevad Rentniku isikuandmed võlgade sissenõudmise, asjaajamise, kahju hindamise ja haldamise eesmärgil või muudel sarnastel eesmärkidel riigiasutustele (sealhulgas kohtutele)" +
                        " ja/või kohtutäituritele, muudele isikutele ja asutustele, kellel on õigus selliseid andmeid saada ja töödelda. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell28.setBorder(NO_BORDER);
        body12acell28.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell28);

        final var body12acell29 =
                new Cell(new Paragraph("12.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell29.setBorder(NO_BORDER);
        body12acell29.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell29);

        final var body12acell30 =
                new Cell(new Paragraph("Kõik Rentnik poolt käesolevate tingimuste alusel Rendileandjale tasumisele kuuluvad summad tasutakse, debiteeritakse ja tasaarvestatakse järgmises järjekorras: ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell30.setBorder(NO_BORDER);
        body12acell30.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell30);

        final var body12acell31 =
                new Cell(new Paragraph("12.14.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell31.setBorder(NO_BORDER);
        body12acell31.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell31);

        final var body12acell32 =
                new Cell(new Paragraph("trahvid ja viivised; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell32.setBorder(NO_BORDER);
        body12acell32.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell32);

        final var body12acell33 =
                new Cell(new Paragraph("12.14.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell33.setBorder(NO_BORDER);
        body12acell33.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell33);

        final var body12acell34 =
                new Cell(new Paragraph("muud Rendileandjale makstavad tasud, maksud ja maksed; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell34.setBorder(NO_BORDER);
        body12acell34.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell34);

        final var body12acell35 =
                new Cell(new Paragraph("12.14.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell35.setBorder(NO_BORDER);
        body12acell35.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell35);

        final var body12acell36 =
                new Cell(new Paragraph("võlgnevus osutatud teenuste eest;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell36.setBorder(NO_BORDER);
        body12acell36.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell36);

        final var body12acell37 =
                new Cell(new Paragraph("12.14.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body12acell37.setBorder(NO_BORDER);
        body12acell37.setHorizontalAlignment(LEFT);
        body12a.addCell(body12acell37);

        final var body12acell38 =
                new Cell(new Paragraph("jooksev renditasu.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body12acell38.setBorder(NO_BORDER);
        body12acell38.setHorizontalAlignment(JUSTIFIED);
        body12a.addCell(body12acell38);

        contractPdfDoc.add(body12a);
        
////13        
        final var body13a = new Table(2);
        body13a.setWidths(new float[]{2, 35});
        body13a.setPadding(0f);
        body13a.setSpacing(0f);
        body13a.setWidth(100f);
        body13a.setBorderColor(white);
        body13a.setHorizontalAlignment(LEFT);
        body13a.setBorder(NO_BORDER);
        body13a.setBorder(NO_BORDER);

        final var body13acell1 =
                new Cell(new Paragraph("XIII.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body13acell1.setBorder(NO_BORDER);
        body13acell1.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell1);

        final var body13acell2 =
                new Cell(new Paragraph("LEPINGU LÕPETAMINE",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body13acell2.setBorder(NO_BORDER);
        body13acell2.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell2);

        final var body13acell3 =
                new Cell(new Paragraph("13.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell3.setBorder(NO_BORDER);
        body13acell3.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell3);

        final var body13acell4 =
                new Cell(new Paragraph("Rentnikul on õigus leping igal ajal mistahes põhjusel lõpetada esitades Rendileandjale selle kohta kirjaliku teate. " +
                        "Rendileandja lõpetab lepingu Rentnikult lepingu lõpetamise kohta teate saamist hiljemalt 7 (seitsme) päeva jooksul. Auto renditeenuste lõpetamiseks " +
                        "kohustub rentnik teavitada oma soovist renditud auto tagastada " + model.getDuration1()+  " päeva ette järgmise esmaspäevani. Lepingu lõpetamine ei vabasta " +
                        "Rentniku enne lepingu lõpetamist tekkinud kohustuste täitmisest. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell4.setBorder(NO_BORDER);
        body13acell4.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell4);

        final var body13acell5 =
                new Cell(new Paragraph("13.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell5.setBorder(NO_BORDER);
        body13acell5.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell5);

        final var body13acell6 =
                new Cell(new Paragraph("Lepingu lõpetamisel on Rentnik kohustatud tagastama renditud auto kirjaliku üleandmise-vastuvõtmise akti alusel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell6.setBorder(NO_BORDER);
        body13acell6.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell6);

        final var body13acell7 =
                new Cell(new Paragraph("13.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell7.setBorder(NO_BORDER);
        body13acell7.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell7);

        final var body13acell8 =
                new Cell(new Paragraph("Auto tagastamisel teostavad Rentnik ja Rendileandja tagastava vara ülevaatuse ning fikseerivad auto seisundi ning võimalikud puudused ja erinevused võrreldes algse vara üleandmise aktiga.   ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell8.setBorder(NO_BORDER);
        body13acell8.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell8);

        final var body13acell9 =
                new Cell(new Paragraph("13.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell9.setBorder(NO_BORDER);
        body13acell9.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell9);

        final var body13acell10 =
                new Cell(new Paragraph("Rendileandjal on õigus lõpetada Rentnikuga sõlmitud lepingu samal päeval järgmistel juhtudel: ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell10.setBorder(NO_BORDER);
        body13acell10.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell10);

        final var body13acell11 =
                new Cell(new Paragraph("13.4.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell11.setBorder(NO_BORDER);
        body13acell11.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell11);

        final var body13acell12 =
                new Cell(new Paragraph("sõidukit juhtis isik, kellel puudus selleks õigus;  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell12.setBorder(NO_BORDER);
        body13acell12.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell12);

        final var body13acell13 =
                new Cell(new Paragraph("13.4.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell13.setBorder(NO_BORDER);
        body13acell13.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell13);

        final var body13acell14 =
                new Cell(new Paragraph("sõidukit kasutatakse eesmärkidel, milleks see ei ole mõeldud või ette nähtud; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell14.setBorder(NO_BORDER);
        body13acell14.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell14);

        final var body13acell15 =
                new Cell(new Paragraph("13.4.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell15.setBorder(NO_BORDER);
        body13acell15.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell15);

        final var body13acell16 =
                new Cell(new Paragraph("juht juhtis sõidukit alkoholijoobes (üle 0,00 promilli) või narkootiliste " +
                        "ainete või muude vaimset seisundit mõjutavate ainete mõju all (ka juhul, kui Rentnik tarvitas alkoholi või muid " +
                        "joovastavaid aineid pärast liiklusõnnetust enne liiklusõnnetuse asjaolude tuvastamist või vältis vere alkoholisisalduse testi või joobetesti tegemist); ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell16.setBorder(NO_BORDER);
        body13acell16.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell16);

        final var body13acell17 =
                new Cell(new Paragraph("13.4.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell17.setBorder(NO_BORDER);
        body13acell17.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell17);

        final var body13acell18 =
                new Cell(new Paragraph("Rentnik põhjustas sõidukile kahju tahtlikult või raske hooletuse tõttu (nt suure kiiruse ületamise, ohtliku või hoolimatu sõitmise, muu liikluseeskirjade raske rikkumise tõttu); ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell18.setBorder(NO_BORDER);
        body13acell18.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell18);

        final var body13acell19 =
                new Cell(new Paragraph("13.4.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell19.setBorder(NO_BORDER);
        body13acell19.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell19);

        final var body13acell20 =
                new Cell(new Paragraph("hoolimatu ja ohtliku sõitmise korral; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell20.setBorder(NO_BORDER);
        body13acell20.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell20);
//
        final var body13acell21 =
                new Cell(new Paragraph("13.4.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell21.setBorder(NO_BORDER);
        body13acell21.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell21);

        final var body13acell22 =
                new Cell(new Paragraph("Rentnik lahkub õnnetuspaigast; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell22.setBorder(NO_BORDER);
        body13acell22.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell22);

        final var body13acell23 =
                new Cell(new Paragraph("13.4.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell23.setBorder(NO_BORDER);
        body13acell23.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell23);

        final var body13acell24 =
                new Cell(new Paragraph("Rentnik ei täida liikluspolitsei või muude pädevate asutuste juhiseid;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell24.setBorder(NO_BORDER);
        body13acell24.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell24);

        final var body13acell25 =
                new Cell(new Paragraph("13.4.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell25.setBorder(NO_BORDER);
        body13acell25.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell25);

        final var body13acell26 =
                new Cell(new Paragraph("Rentnik kasutab sõidukit kuriteo toimepanemiseks;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell26.setBorder(NO_BORDER);
        body13acell26.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell26);

        final var body13acell27 =
                new Cell(new Paragraph("13.4.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell27.setBorder(NO_BORDER);
        body13acell27.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell27);

        final var body13acell28 =
                new Cell(new Paragraph("Rentnik ei teavita liiklusõnnetusest ettevõtet, politseid, tuletõrjet ja/või muud pädevat asutust või teenistust; ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell28.setBorder(NO_BORDER);
        body13acell28.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell28);

        final var body13acell29 =
                new Cell(new Paragraph("13.4.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell29.setBorder(NO_BORDER);
        body13acell29.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell29);

        final var body13acell30 =
                new Cell(new Paragraph("Rentnik rikub lepingulisi renditasumise kohustusi ning ületab tema võlg 240 eurot. Rendileandjal koos mõjus p. 12.7 on õigus lepingu lõpetada ning auto viivitamatult enda valduse saada. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell30.setBorder(NO_BORDER);
        body13acell30.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell30);

        final var body13acell31 =
                new Cell(new Paragraph("13.4.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell31.setBorder(NO_BORDER);
        body13acell31.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell31);

        final var body13acell32 =
                new Cell(new Paragraph("Rendileandja võib lepingu VÕS § 316 alusel erakorraliselt üles öelda, kui ta on andnud üürnikule kirjalikku taasesitamist võimaldavas vormis vähemalt 14-päevase täiendava tähtaja, hoiatades, et antud tähtaja jooksul võlgnevuse tasumata jätmise korral ütleb ta lepingu üles.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell32.setBorder(NO_BORDER);
        body13acell32.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell32);

        final var body13acell33 =
                new Cell(new Paragraph("13.4.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell33.setBorder(NO_BORDER);
        body13acell33.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell33);

        final var body13acell34 =
                new Cell(new Paragraph("Juhul, kui Rentnik ei tasu võlgnevuse täiendava tähtaja jooksul, loeb üürileandja pärast selle möödumist lepingu ülesöelduks.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell34.setBorder(NO_BORDER);
        body13acell34.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell34);

        final var body13acell35 =
                new Cell(new Paragraph("13.4.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell35.setBorder(NO_BORDER);
        body13acell35.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell35);

        final var body13acell36 =
                new Cell(new Paragraph("Juhul, kui Rentnik viivitab tasumisega, võib üürileandja üürilepingu VÕS § 316 alusel üles öelda täiendavat tähtaega andmata, kui rentnik on võlgnevusele eelnenud aasta jooksul vähemalt kahel korral täitnud kohustused alles täiendava tähtaja jooksul või pärast seda.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell36.setBorder(NO_BORDER);
        body13acell36.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell36);

        final var body13acell37 =
                new Cell(new Paragraph("13.4.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell37.setBorder(NO_BORDER);
        body13acell37.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell37);

        final var body13acell38 =
                new Cell(new Paragraph("Rentnik rikub raskelt käesolevaid tingimusi ja/või jätkab käesolevate tingimuste rikkumist ja/või esinevad muud objektiivsed asjaolud, mille tõttu kujutab Rentnik Rendileandja arvates ohtu teistele Rentnikutele, klientidele, ühiskonnale, Rendileandjale, sõidukile;",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell38.setBorder(NO_BORDER);
        body13acell38.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell38);

        final var body13acell39 =
                new Cell(new Paragraph("13.4.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell39.setBorder(NO_BORDER);
        body13acell39.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell39);

        final var body13acell40 =
                new Cell(new Paragraph("õigusaktides sätestatud tingimustel.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell40.setBorder(NO_BORDER);
        body13acell40.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell40);

        final var body13acell41 =
                new Cell(new Paragraph("13.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body13acell41.setBorder(NO_BORDER);
        body13acell41.setHorizontalAlignment(LEFT);
        body13a.addCell(body13acell41);

        final var body13acell42 =
                new Cell(new Paragraph("Üürileping lõpeb Rentniku surmaga.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body13acell42.setBorder(NO_BORDER);
        body13acell42.setHorizontalAlignment(JUSTIFIED);
        body13a.addCell(body13acell42);

        contractPdfDoc.add(body13a);


        ////14
        
        final var body14a = new Table(2);
        body14a.setWidths(new float[]{1, 20});
        body14a.setPadding(0f);
        body14a.setSpacing(0f);
        body14a.setWidth(100f);
        body14a.setBorderColor(white);
        body14a.setHorizontalAlignment(LEFT);
        body14a.setBorder(NO_BORDER);
        body14a.setBorder(NO_BORDER);

        final var body14acell1 =
                new Cell(new Paragraph("XIV.",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body14acell1.setBorder(NO_BORDER);
        body14acell1.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell1);

        final var body14acell2 =
                new Cell(new Paragraph("LÕPPSÄTTED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body14acell2.setBorder(NO_BORDER);
        body14acell2.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell2);

        final var body14acell3 =
                new Cell(new Paragraph("14.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell3.setBorder(NO_BORDER);
        body14acell3.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell3);

        final var body14acell4 =
                new Cell(new Paragraph("Rendileandjal on õigus käesolevaid tingimusi ühepoolselt muuta, teavitades Rentnikku sellest e-posti teel," +
                        " mis on lepingus näidatud. Tingimuste muudatused jõustuvad 5 (viis) päeva pärast nende Rentnikule teatavaks tegemist. Kui Rentnik jätkab " +
                        "rendilepingu alusel  teenuseid saama kooskõlas muudetud tingimustega, loetakse Rentnik muudatustega nõustunuks.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell4.setBorder(NO_BORDER);
        body14acell4.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell4);

        final var body14acell5 =
                new Cell(new Paragraph("14.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell5.setBorder(NO_BORDER);
        body14acell5.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell5);

        final var body14acell6 =
                new Cell(new Paragraph("Käesolevate tingimuste tähenduses loetakse Rentnik nõuetekohaselt kirjalikult teavitatuks järgmisel päeval pärast seda, kui Rentnikule saadetakse tema lepingus märgitud e-posti aadressile e-kirjaga saadetav teade. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell6.setBorder(NO_BORDER);
        body14acell6.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell6);

        final var body14acell7 =
                new Cell(new Paragraph("14.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell7.setBorder(NO_BORDER);
        body14acell7.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell7);

        final var body14acell8 =
                new Cell(new Paragraph("Rendileandjal on õigus anda ühepoolselt kõik või mõned käesolevatest tingimustest ja/või lepingust tulenevad õigused ja kohustused üle kolmandale isikule, A. olles teavitanud Rentnikut sellest kirjalikult ette saates üldise teate e-posti teel.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell8.setBorder(NO_BORDER);
        body14acell8.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell8);

        final var body14acell9 =
                new Cell(new Paragraph("14.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell9.setBorder(NO_BORDER);
        body14acell9.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell9);

        final var body14acell10 =
                new Cell(new Paragraph("Kõik pooltevahelised vaidlused ja lahkarvamused lahendatakse Eesti Vabariigi pädevas kohtus. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell10.setBorder(NO_BORDER);
        body14acell10.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell10);

        final var body14acell11 =
                new Cell(new Paragraph("14.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell11.setBorder(NO_BORDER);
        body14acell11.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell11);

        final var body14acell12 =
                new Cell(new Paragraph("Käesolevaid tingimusi tõlgendatakse ja kohaldatakse kooskõlas Eesti Vabariigi õigusega.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell12.setBorder(NO_BORDER);
        body14acell12.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell12);

        final var body14acell13 =
                new Cell(new Paragraph("14.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body14acell13.setBorder(NO_BORDER);
        body14acell13.setHorizontalAlignment(LEFT);
        body14a.addCell(body14acell13);

        final var body14acell14 =
                new Cell(new Paragraph("Rentnik võib mistahes ja kõikides käesolevaid tingimusi puudutavates küsimustes pöörduda Rendileandja poole lepingus toodud rekvisiitide ja kontaktide järgi. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body14acell14.setBorder(NO_BORDER);
        body14acell14.setHorizontalAlignment(JUSTIFIED);
        body14a.addCell(body14acell14);

        contractPdfDoc.add(body14a);
        
        
///// 15 
        final var body15a = new Table(2);
        body15a.setWidths(new float[]{1, 20});
        body15a.setPadding(0f);
        body15a.setSpacing(0f);
        body15a.setWidth(100f);
        body15a.setBorderColor(white);
        body15a.setHorizontalAlignment(LEFT);
        body15a.setBorder(NO_BORDER);
        body15a.setBorder(NO_BORDER);

        final var body15acell1 =
                new Cell(new Paragraph("Lisa ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body15acell1.setBorder(NO_BORDER);
        body15acell1.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell1);

        final var body15acell2 =
                new Cell(new Paragraph("nr 1 p. IIX Trahvid ja kahjutasud",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body15acell2.setBorder(NO_BORDER);
        body15acell2.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell2);

        final var body15acell3 =
                new Cell(new Paragraph("A.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell3.setBorder(NO_BORDER);
        body15acell3.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell3);

        final var body15acell4 =
                new Cell(new Paragraph("Kui sõiduk antakse Rentnikule üle seest ja väljast puhtana ja pestuna, kohustub rentnik tagastama sõiduki samas seisukorras." +
                        " Pesemata sõiduki tagastamisel tuleb Rentnikul tasuda trahvi välispesu vajaduse eest 60.- EUR, salongi puhastamise vajaduse eest 180.- EUR " +
                        "ja vajadusel pakiruumi puhastamise vajaduse eest 40.- EUR.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell4.setBorder(NO_BORDER);
        body15acell4.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell4);

        final var body15acell5 =
                new Cell(new Paragraph("B.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell5.setBorder(NO_BORDER);
        body15acell5.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell5);

        final var body15acell6 =
                new Cell(new Paragraph("Juhul kui sõiduk vajab keemilist puhastust, tuleb Rentnikul tasuda trahvi keemilise puhastuse vajaduse eest 360.- EUR. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell6.setBorder(NO_BORDER);
        body15acell6.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell6);

        final var body15acell7 =
                new Cell(new Paragraph("C.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell7.setBorder(NO_BORDER);
        body15acell7.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell7);

        final var body15acell8 =
                new Cell(new Paragraph("sõidukis suitsetamise eest 500 EUR  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell8.setBorder(NO_BORDER);
        body15acell8.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell8);

        final var body15acell9 =
                new Cell(new Paragraph("D.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell9.setBorder(NO_BORDER);
        body15acell9.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell9);

        final var body15acell10 =
                new Cell(new Paragraph("D.\tsõiduki juhtimise eest alkoholijoobes (üle 0,00 promilli), narkootiliste ja muude psühhotroopsete ainete mõju all " +
                        "(või kui tarvitasite alkoholi või muid joovastavaid aineid pärast liiklusõnnetust, enne kui õnnetuse asjaolud välja selgitati, või vältisite vere" +
                        " alkoholisisalduse mõõtmist või joobetesti tegemist (vere alkoholisisaldust ja joovet mõistetakse nii, nagu on määratletud õigusaktides). " +
                        "Viidatud summas trahvi alkoholijoobes (üle 0,00 promillise), narkootiliste ja muude psühhotroopsete ainete mõju all sõiduki juhtimise eest peate " +
                        "meile tasuma ka nendel juhtudel, kui võõrandasite Sõiduki või muul viisil võimaldasite teisel isikul seda juhtida, kui ta oli alkoholijoobes " +
                        "(üle 0,00 promilli), narkootiliste ja muude psühhotroopsete ainete mõju all, või kui see isik vältis vere alkoholisisalduse mõõtmist või joobetesti tegemist: 2000 EUR  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell10.setBorder(NO_BORDER);
        body15acell10.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell10);

        final var body15acell11 =
                new Cell(new Paragraph("E.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell11.setBorder(NO_BORDER);
        body15acell11.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell11);

        final var body15acell12 =
                new Cell(new Paragraph("Ebakvaliteetsest kütusest tekkinud kahjud kannab Rentnik vastavalt arvele, mida väljastab teenuse osutaja remondi teostamiseks.  ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell12.setBorder(NO_BORDER);
        body15acell12.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell12);

        final var body15acell13 =
                new Cell(new Paragraph("F.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell13.setBorder(NO_BORDER);
        body15acell13.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell13);

        final var body15acell14 =
                new Cell(new Paragraph("Sõiduki dokumentide mittetagastamisel või puuduliku varustusega sõiduki tagastamisel tasub Rentnik Rendileandjale leppetrahvi 250.-EUR iga kaotatud või puuduva dokumendi või eseme kohta ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell14.setBorder(NO_BORDER);
        body15acell14.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell14);

        final var body15acell15 =
                new Cell(new Paragraph("G.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell15.setBorder(NO_BORDER);
        body15acell15.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell15);

        final var body15acell16 =
                new Cell(new Paragraph(" Võtmete kaotamise eest kannab Rentnik trahvi vastavalt esinduse poolt väljastatud arvele, mis sisaldab uue võtme ja auto signalisatsiooni ümberprogrammeerimine ja seadistamine.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell16.setBorder(NO_BORDER);
        body15acell16.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell16);

        final var body15acell17 =
                new Cell(new Paragraph("H.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell17.setBorder(NO_BORDER);
        body15acell17.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell17);

        final var body15acell18 =
                new Cell(new Paragraph(" Rentniku poolt ja süül sõiduki hävitamisel tasub Rentnik Rendileandjale leppetrahvi sõiduki turuväärtuse ulatuses.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell18.setBorder(NO_BORDER);
        body15acell18.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell18);

        final var body15acell19 =
                new Cell(new Paragraph("I.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell19.setBorder(NO_BORDER);
        body15acell19.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell19);

        final var body15acell20 =
                new Cell(new Paragraph("Kui aga sõiduk saab kahjustada liiklusõnnetuses, mille põhjustab Rentnik (või muu isik, kellele Rentnik võimaldas sõidukit kasutada) ebakaines olekus, spordivõistlustel osaledes või muul viisil Tingimusi rikkudes, peab Rentnik tekitatud kahjud hüvitama täies ulatuses.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell20.setBorder(NO_BORDER);
        body15acell20.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell20);

        final var body15acell21 =
                new Cell(new Paragraph("J.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body15acell21.setBorder(NO_BORDER);
        body15acell21.setHorizontalAlignment(LEFT);
        body15a.addCell(body15acell21);

        final var body15acell22 =
                new Cell(new Paragraph("Kui Rendileandjale tagastatud sõiduk vajab remonti, kannab Rentnik iga remondipäeva eest lepingus kokkulepitud rendipäeva hinnale lisaks ka remondikulud vastavalt üleandmise-vastuvõtmise aktis märgitud nädala hinnale ning remondiarvele.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body15acell22.setBorder(NO_BORDER);
        body15acell22.setHorizontalAlignment(JUSTIFIED);
        body15a.addCell(body15acell22);


        contractPdfDoc.add(body15a);
        
        
        
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
                new Cell(new Paragraph("RENDILEANDJA:  ",
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
                new Cell(new Paragraph("Rendileandja esindaja  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell3.setBorder(NO_BORDER);
        signaturecell3.setHorizontalAlignment(LEFT);
        signature.addCell(signaturecell3);

        final var signaturecell4 =
                new Cell(new Paragraph("__________________________________________________________________________________________  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        signaturecell4.setBorder(NO_BORDER);
        signaturecell4.setHorizontalAlignment(JUSTIFIED);
        signature.addCell(signaturecell4);

        final var signaturecell41 =
                new Cell(new Paragraph("Alloleva allkirjaga tõendan, et olen Koostöölepingu täielikult läbi lugenud, selle sisust ja mõttest aru saanud ning nõustun nende tingimustega. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        signaturecell41.setColspan(2);
        signaturecell41.setBorder(NO_BORDER);
        signaturecell41.setHorizontalAlignment(JUSTIFIED);
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
                new Cell(new Paragraph("Rentnik esindaja  ",
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
                new Cell(new Paragraph("Nimi, allkiri                                                        Kuupäev             ",
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
