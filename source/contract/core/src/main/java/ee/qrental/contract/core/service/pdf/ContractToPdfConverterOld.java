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
public class ContractToPdfConverterOld {

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
                        new Paragraph("KOOSTÖÖLEPING   Nr. " + model.getNumber(), new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
        cell1.setBorder(NO_BORDER);
        cell1.setHorizontalAlignment(CENTER);
        header1.addCell(cell1);

        final var cell2 =
                new Cell(new Paragraph("Data : " + model.getCreated(), new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
        cell2.setBorder(NO_BORDER);
        cell2.setHorizontalAlignment(CENTER);
        header1.addCell(cell2);

        final var cell3 =
                new Cell(new Paragraph(format("Koostöölepingu pooled on RENDILEANDJA ja RENTNIK."), new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
        cell3.setBorder(NO_BORDER);
        cell3.setHorizontalAlignment(CENTER);
        header1.addCell(cell3);

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
                new Cell(new Paragraph("Esindaja:  " + getTextOrEmpty(model.getQFirmCeo()),
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
                new Cell(new Paragraph("ÜLDSÄTTED",
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
                new Cell(new Paragraph("Koostöölepingu sõlmitakse määramata tähtajaks, ja see kehtib kliendi poolt " +
                        "asjakohase taotluse esitamiseni ja pretensioonide puudumisel. " +
                        "Pretensioonide korral, kehtib koostööleping nende lahendamiseni.",
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
                new Cell(new Paragraph("Auto rendipäeva pikkus on 24 tundi alates iga ööpäeva kella 10:00’st.",
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
                new Cell(new Paragraph("Kütus ei sisaldu rendihinnas.",
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
                new Cell(new Paragraph("Rendiautos on keelatud süüa, suitsetada, tarbida alkoholi ja vedada loomi. " +
                        "Antud keelu eiramise korral Rentniku poolt on Rendileandjal õigus nõuda Rentnikult trahvi 500.- EUR.",
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
                new Cell(new Paragraph("KOOSTÖÖLEPINGUGA SEOTUD DOKUMENDID:",
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
                new Cell(new Paragraph("Üleandmise-vastuvõtmise aks, millel on määratud üleantava rendiauto üleandmise-vastuvõtmise kuupäev," +
                        " registreerimisnumber, auto seisund (olemasolevad vigastused ja sisepuhtus) üleandmise ajaks, üleantud tagatise suurus " +
                        "ning rendileandja ja rentniku allkirjad, mis kinnitavad poolte nõustamist nimetatud üleandmise-vastuvõtmise akti andmetega.\n ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell4.setBorder(NO_BORDER);
        body2cell4.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell4);

/*        final var body2cell5 =
                new Cell(new Paragraph("II.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body2cell5.setBorder(NO_BORDER);
        body2cell5.setHorizontalAlignment(LEFT);
        body2.addCell(body2cell5);

        final var body2cell6 =
                new Cell(new Paragraph("Sularahata arvelduste vahendusleping",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body2cell6.setBorder(NO_BORDER);
        body2cell6.setHorizontalAlignment(JUSTIFIED);
        body2.addCell(body2cell6);
*/
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
                new Cell(new Paragraph(" SÕIDUK ",
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
                new Cell(new Paragraph("Vaba auto Q Takso Veod OÜ autopargist.",
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
                new Cell(new Paragraph("Käesoleva Koostöölepingu kohaselt kohustub Rendileandja andma Rentnikule " +
                        "kasutamiseks Koostöölepingus toodud tingimustel vaba sõidukit oma autopargist -  koostöölepingu eseme ehk Rendiauto." +
                        " Rentnik on kohustatud maksma selle eest tasu (Renti) Rendileandjale kogu Rendiperioodi eest. ",
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
                new Cell(new Paragraph("Sõiduki väljastamise koht on Lasnamäe 30a, Tallinn.",
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
                new Cell(new Paragraph("Sõiduki viimine üle Eesti Vabariigi  välise- või sisse piiri ilma eelneva Rendileandja kirjaliku nõusolekuta keelatud.",
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
                new Cell(new Paragraph("Sõiduki tagastamiskoht on Lasnamäe 30a, Tallinn. ",
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
                new Cell(new Paragraph("Rendiauto tagatise summa on määratud auto üleandmise/vastuvõtmise aktis." +
                        " Tagatise summa tagastatakse rentnikule mitte varem kui kolm nädalat pärast auto rendiandjale tagastamist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body3cell14.setBorder(NO_BORDER);
        body3cell14.setHorizontalAlignment(JUSTIFIED);
        body3.addCell(body3cell14);

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
                new Cell(new Paragraph(" RENDI TASUSTAMINE: ",
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
                new Cell(new Paragraph("IV.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell5.setBorder(NO_BORDER);
        body4cell5.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell5);

        final var body4cell6 =
                new Cell(new Paragraph(" Rendiauto lepinguline nädala renditasu suurus üleandmise-vastuvõtmise hetkeks" +
                        " sõltub üleantava auto omadustest ja määratakse eraldi auto üleandmise-vastuvõtmise aktis. Üleandmise-vastuvõtmise " +
                        "aktis määratud renditasu on aktuaalne vaid täis rendinädala tasumise puhul. Täisrendinädalat arvestatakse iga " +
                        "kalendri nädala Esmaspäeva kella 10:00'st kuni järgneva kalendri nädala kella 10:00'ni - nimelt seitse päevat, " +
                        "millest on Pühapäev tasuta ja võib olla kasutatud puhkepäevana. Iga mittetäis nädala autorendi ööpäeva maksumus " +
                        "on võrdne viiendikuga terve nädala rendi maksumusest.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell6.setBorder(NO_BORDER);
        body4cell6.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell6);

        final var body4cell7 =
                new Cell(new Paragraph("IV.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body4cell7.setBorder(NO_BORDER);
        body4cell7.setHorizontalAlignment(LEFT);
        body4.addCell(body4cell7);

        final var body4cell8 =
                new Cell(new Paragraph(" Rentnik kohustub tasuma rendileandjale ettemaksu iga nädala rendi eest sularahas " +
                        "Q Takso Veod OÜ kontoris, mis asub aadressil Lasnamäe 30a, Tallinn, või ülekandega Q Takso Veod OÜ " +
                        "pangakontole  (või muule Q Takso Veod OÜ esindajaga maaratud pangakontole) asjakohase selgitusega – " +
                        "”autorent + auto number”, mitte hiljem, kui iga nädala teisipäeva kella 16:00’ni.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body4cell8.setBorder(NO_BORDER);
        body4cell8.setHorizontalAlignment(JUSTIFIED);
        body4.addCell(body4cell8);

        contractPdfDoc.add(body4);


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
                new Cell(new Paragraph(" RENTNIKU  ÕIGUSED ",
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
                new Cell(new Paragraph("Rentnikul on õigus kasutada sõidukit oma isiklikus hüvanguks.",
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
                new Cell(new Paragraph("Rentnikul on õigus kasutada sõidukit ainult siis, kui ta on vähemalt 21 aastat" +
                        " vana ja omab mootorsõiduki juhistaaži vähemalt kaks aastat.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell6.setBorder(NO_BORDER);
        body5cell6.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell6);

        final var body5cell7 =
                new Cell(new Paragraph("V.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body5cell7.setBorder(NO_BORDER);
        body5cell7.setHorizontalAlignment(LEFT);
        body5.addCell(body5cell7);

        final var body5cell8 =
                new Cell(new Paragraph("Kui rentnik ei nõustu Rendileandja poolt esitatud nõude või selle suurusega," +
                        " on tal õigus esitada kohtusse hagi 30 päeva jooksul arvates nõude kättesaamisest." +
                        " Hagi tähtaegset esitamata jätmist tõlgendatakse nõudega nõustumisena täies ulatuses ja rentniku hilisemaid pretensioone ei arvestata.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body5cell8.setBorder(NO_BORDER);
        body5cell8.setHorizontalAlignment(JUSTIFIED);
        body5.addCell(body5cell8);

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
                new Cell(new Paragraph(" RENTNIKU  KOHUSTUSED ",
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
                new Cell(new Paragraph(" Rentnik kohustub maksma tasu rendiperioodi eest. " +
                        "Renditasu ja muu rendiauto kasutamisest tulenevad kohustused mittetähtaegsel tasumisel on " +
                        "rentnik kohustatud tasuma viivist 0.25% kalendripäevas tasumata summalt.",
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
                new Cell(new Paragraph("Tasumata summa arvestatakse nädalapõhiselt konkreetse sõiduki nädala rendi maksumuse ja juhi tehtud maksete alusel.",
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
                new Cell(new Paragraph("Rentnik on kohustatud sõiduki üle vaatama enne vastuvõtmist, veenduma selle sobilikkuses ja korrasolekus," +
                        " tegema vastavasisulise märke autol olemasolevatest kahjustustest Vastuvõtmise-üleandmise aktil ja panna oma allkirja nende kinnitamiseks. " +
                        "Rentniku allkiri kinnitab ta nõusolekut ainult nende kahjustuste olemasoluga autol, mis on märgitud Vastuvõtmise-üleandmise aktil.",
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
                new Cell(new Paragraph("Vastuvõtmise-üleandmise akt koostatakse kahes eksemplaris – originaal ja selle koopia, mis antakse rentnikule auto kahjustuste kinnitamiseks.",
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
                new Cell(new Paragraph("Rentnik on kohustatud sõidukit kasutama vastavalt valmistajatehase juhendile.",
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
                new Cell(new Paragraph("Rentnik on kohustatud hoolitsema Rendisõiduki eest heaperemehelikult ja kõrvaldama " +
                        "tekkinud puudused VÕS § 345 lg. 1 toodud tingimustel (kandma kulu).",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell14.setBorder(NO_BORDER);
        body6cell14.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell14);

        final var body6cell15 =
                new Cell(new Paragraph("VI.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell15.setBorder(NO_BORDER);
        body6cell15.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell15);

        final var body6cell16 =
                new Cell(new Paragraph("Rentnik on kohustatud sõidukit mitte kasutama: ebaseaduslikuks tegevuseks või seadusevastasel " +
                        "eesmärkidel, samuti pukseerimiseks, autovõidusõiduks, treeninguks jne.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell16.setBorder(NO_BORDER);
        body6cell16.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell16);

        final var body6cell17 =
                new Cell(new Paragraph("VI.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell17.setBorder(NO_BORDER);
        body6cell17.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell17);

        final var body6cell18 =
                new Cell(new Paragraph("Rentnik on kohustatud sõidukit kasutama ainult teedel, mis on teed Teeseaduse mõistes",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell18.setBorder(NO_BORDER);
        body6cell18.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell18);

        final var body6cell19 =
                new Cell(new Paragraph("VI.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell19.setBorder(NO_BORDER);
        body6cell19.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell19);

        final var body6cell20 =
                new Cell(new Paragraph("Rentnik on kohustatud sõidukit mitte andma kolmandale isikule kasutamiseks.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell20.setBorder(NO_BORDER);
        body6cell20.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell20);

        final var body6cell43 =
                new Cell(new Paragraph("VI.10",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell43.setBorder(NO_BORDER);
        body6cell43.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell43);

        final var body6cell44 =
                new Cell(new Paragraph("Rentnik on kohustatud jälgida rendiauto dokumentide kehtivust ja veenduda, et on rendiauto liikluses kasutamine seaduslik.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell44.setBorder(NO_BORDER);
        body6cell44.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell44);

        final var body6cell21 =
                new Cell(new Paragraph("VI.11",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell21.setBorder(NO_BORDER);
        body6cell21.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell21);

        final var body6cell22 =
                new Cell(new Paragraph("Rentnik või teine juht on kohustatud veenduma enne igat " +
                        "sõitu sõiduki tehnilises korrasolekus ning jälgima tehnoseisundit sõidu ajal." +
                        " Tehnilise rikke esinemisel sõitmise katkestama vea  kõrvaldamiseni.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell22.setBorder(NO_BORDER);
        body6cell22.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell22);

        final var body6cell23 =
                new Cell(new Paragraph("VI.12",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell23.setBorder(NO_BORDER);
        body6cell23.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell23);

        final var body6cell24 =
                new Cell(new Paragraph("Rentnik on kohustatud sõidukit tankima üksnes kvaliteetse ja  sõidukile ettenähtud mootorikütusega. ",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell24.setBorder(NO_BORDER);
        body6cell24.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell24);

        final var body6cell25 =
                new Cell(new Paragraph("VI.13",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell25.setBorder(NO_BORDER);
        body6cell25.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell25);

        final var body6cell26 =
                new Cell(new Paragraph("Rentnik on kohustatud tegema kõik, et vältida Rendileandjale ja kolmandatele isikutele kahju tekkimist.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell26.setBorder(NO_BORDER);
        body6cell26.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell26);

        final var body6cell27 =
                new Cell(new Paragraph("VI.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell27.setBorder(NO_BORDER);
        body6cell27.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell27);

        final var body6cell28 =
                new Cell(new Paragraph("Liiklusõnnetuse, varguse, vandalismi jms. juhtumi korral on Rentnik kohustatud viivitamatult teavitama " +
                        " Rendileandjat juhtunust. Kui tegemist on olukorraga, kus Rentnikul tekib seadusest tulenev kohustus, teatada juhtunust " +
                        "päästeametit või politseid, on Rentnik kohustatud seda tegema. Liiklusõnnetuse korral, milles Rentnik pole süüdi, " +
                        "tuleb liiklusõnnetuses osalenud juhtidel täita nõuetekohaselt LE vorm nr.1 või kutsuda sündmuskohale politsei, " +
                        "et fikseerida liiklusõnnetus. Selle LE vorm nr.1 või politsei poolt koostatud sündmuskoha skeemi koopia mitteesitamisel " +
                        "Rendileandjale, on Rentnik täies ulatuses vastutav Rendileandjale tekitatud kahjude eest.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell28.setBorder(NO_BORDER);
        body6cell28.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell28);

        final var body6cell29 =
                new Cell(new Paragraph("VI.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell29.setBorder(NO_BORDER);
        body6cell29.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell29);

        final var body6cell30 =
                new Cell(new Paragraph("Rentnik on kohustatud tagastama sõiduki lepingus märgitud Rendiperioodi lõppemisel kokkulepitud kohas ettenähtud ajal. Sõidukit ei tohi hüljata.",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell30.setBorder(NO_BORDER);
        body6cell30.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell30);

        final var body6cell31 =
                new Cell(new Paragraph("VI.16",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell31.setBorder(NO_BORDER);
        body6cell31.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell31);

        final var body6cell32 =
                new Cell(new Paragraph("Kahju tekkimisel Rendileandjale või kolmandatele isikutele või dokumentide või esemete kadumisel on Rentnik " +
                        "ja teine juht kohustatud hiljemalt 24 tunni möödumisel esitama Rendileandjale kirjaliku seletuse juhtunu kohta. " +
                        "Kui puudub muu võimalus kirjaliku seletuse andmiseks, võib seda esitada Rendileandjale erandkorras ka lepingus toodud " +
                        "e-posti aadressile, varustades seletuse digitaalallkirjaga. \n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell32.setBorder(NO_BORDER);
        body6cell32.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell32);

        final var body6cell33 =
                new Cell(new Paragraph("VI.17",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell33.setBorder(NO_BORDER);
        body6cell33.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell33);

        final var body6cell34 =
                new Cell(new Paragraph("Kui sõiduk antakse Rentnikule üle seest ja väljast puhtana ja pestuna, kohustub rentnik tagastama " +
                        "sõiduki samas seisukorras. Pesemata sõiduki tagastamisel tuleb Rentnikul tasuda trahvi välispesu vajaduse eest 60.- EUR, " +
                        "salongi puhastamise vajaduse eest 180.- EUR ja vajadusel pakiruumi puhastamise vajaduse eest 40.- EUR." +
                        " Juhul kui sõiduk vajab keemilist puhastust, tuleb Rentnikul tasuda trahvi keemilise puhastuse vajaduse eest 360.- EUR.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell34.setBorder(NO_BORDER);
        body6cell34.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell34);

        final var body6cell35 =
                new Cell(new Paragraph("VI.18",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell35.setBorder(NO_BORDER);
        body6cell35.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell35);

        final var body6cell36 =
                new Cell(new Paragraph("Kõik käesolevast lepingust tulenevad mistahes rahalised nõuded ja kohustused tuleb Rentnikul" +
                        " täita kahe (2) päeva jooksul arvates Rendileandjalt vastavasisulise nõude saamisest. Rahaliste nõuete kohest täitmist ei " +
                        "peata ega takista väärteo-, kriminaalasja ega kindlustusjuhtumi menetlemine. Ühegi kahju hüvitamine ei vabasta Rentnikku " +
                        "Renditasu maksmisest nende päevade eest, mil kahju suurust selgitati ja taastati kahju tekkimisele eelnenud olukord. \n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell36.setBorder(NO_BORDER);
        body6cell36.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell36);

        final var body6cell37 =
                new Cell(new Paragraph("VI.19",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell37.setBorder(NO_BORDER);
        body6cell37.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell37);

        final var body6cell38 =
                new Cell(new Paragraph("Rendileandja poolt käesoleva koostöölepingu alusel osutatava rendiautoteenuse (edaspidi \"renditeenus\") " +
                        "minimaalne kestus on " + model.getDuration() + "  täis kalendrinädalat esimese rendiauto edastamise kuupäevast. " +
                        "Ülalnimetatud kestus hakatakse lugema auto üleandmise-vastuvõtmise aktis märgitud kuupäevast." +
                        " Renditeenuse kasutamise ennetähtaegsel lõpetamisel kohustub rentnik tasuma kõigi kasutamata rendinädalate eest vastavalt " +
                        "käesoleva lepingu ja asjakohase üleandmise-vastuvõtmise akti tingimustele. \n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell38.setBorder(NO_BORDER);
        body6cell38.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell38);

        final var body6cell39 =
                new Cell(new Paragraph("VI.20",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell39.setBorder(NO_BORDER);
        body6cell39.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell39);

        final var body6cell40 =
                new Cell(new Paragraph(" Auto renditeenuste lõpetamiseks kohustub rentnik teavitada oma soovist renditud auto tagastada seitse päeva ette järgmise esmaspäevani.\n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell40.setBorder(NO_BORDER);
        body6cell40.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell40);

        final var body6cell41 =
                new Cell(new Paragraph("VI.21",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body6cell41.setBorder(NO_BORDER);
        body6cell41.setHorizontalAlignment(LEFT);
        body6.addCell(body6cell41);

        final var body6cell42 =
                new Cell(new Paragraph("Kui ei ole rentnik oma soovist teenuse lõpetada rendileandja õigeaegselt teavitanud, " +
                        "pikeneb teenuse kestvus automaatselt samadel tingimustel veel ühe nädala võrra. \n",
                        new Font(Font.TIMES_ROMAN, 8, Font.NORMAL)));
        body6cell42.setBorder(NO_BORDER);
        body6cell42.setHorizontalAlignment(JUSTIFIED);
        body6.addCell(body6cell42);

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
                new Cell(new Paragraph(" RENTNIKU  VASTUTUS ",
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
                new Cell(new Paragraph("Liiklusõnnetusest või kolmanda isiku õigusvastasest käitumisest tekkinud kahju kannab Rentnik " +
                        "ulatuses, mida ei kanna kindlustus (s.h. omavastutuse määra). Kui kindlustusfirma keeldub kindlustushüvitise väljamaksmisest " +
                        "või kui kahjujuhtum ei ole kindlustusjuhtum, kohustub Rentnik tasuma Rendileandjale sõiduki täisväärtuse ja hüvitama tekkinud kahjud. ",
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
                new Cell(new Paragraph("Rentnik vastutab täielikult kõikide sõidukist kadunud või vahetatud detailide ja lisavarustuse eest. ",
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
                new Cell(new Paragraph("Sõiduki kahjustamisel Rentniku enda või kolmandate isikute poolt kannab kahju Rentnik. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell8.setBorder(NO_BORDER);
        body7cell8.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell8);

        final var body7cell9 =
                new Cell(new Paragraph("VII.4",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell9.setBorder(NO_BORDER);
        body7cell9.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell9);

        final var body7cell10 =
                new Cell(new Paragraph("Kui Rentnik või teine juht on oma teoga (tegevuse või  tegevusetusega) põhjustanud kahju " +
                        "Rendileandjale või kolmandale isikule tahtlikult, kannab Rentnik kahju täielikult. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell10.setBorder(NO_BORDER);
        body7cell10.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell10);

        final var body7cell11 =
                new Cell(new Paragraph("VII.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell11.setBorder(NO_BORDER);
        body7cell11.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell11);

        final var body7cell12 =
                new Cell(new Paragraph("Rentnik kannab täielikult kahju, mis on tekitatud, kui sõidukit on juhitud alkoholijoobes " +
                        "või selle tarvitamise tunnustel, väsimusseisundis või mõne narkootilise aine mõju all. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell12.setBorder(NO_BORDER);
        body7cell12.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell12);

        final var body7cell13 =
                new Cell(new Paragraph("VII.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell13.setBorder(NO_BORDER);
        body7cell13.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell13);

        final var body7cell14 =
                new Cell(new Paragraph("Kui rentnik annab sõiduki üle kolmandale isikule, kannab Rentnik täielikult Rendileandjale või kolmandatele isikutele tekkinud kahju.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell14.setBorder(NO_BORDER);
        body7cell14.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell14);

        final var body7cell15 =
                new Cell(new Paragraph("VII.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell15.setBorder(NO_BORDER);
        body7cell15.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell15);

        final var body7cell16 =
                new Cell(new Paragraph("Kui Rendiauto on varastatud, ärandatud või röövitud, on Rentnik vastutav sõiduki täisväärtuse ulatuses ja kohustub hüvitama Rendileandjale tekitatud kahjud. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell16.setBorder(NO_BORDER);
        body7cell16.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell16);

        final var body7cell17 =
                new Cell(new Paragraph("VII.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell17.setBorder(NO_BORDER);
        body7cell17.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell17);

        final var body7cell18 =
                new Cell(new Paragraph("Kui Rendileandjale tagastatud sõiduk vajab remonti, kannab Rentnik iga remondipäeva eest lepingus kokkulepitud rendipäeva hinnale lisaks ka remondikulud. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell18.setBorder(NO_BORDER);
        body7cell18.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell18);

        final var body7cell19 =
                new Cell(new Paragraph("VII.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell19.setBorder(NO_BORDER);
        body7cell19.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell19);

        final var body7cell20 =
                new Cell(new Paragraph("Ebakvaliteetsest kütusest tekkinud kahjud kannab Rentnik.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell20.setBorder(NO_BORDER);
        body7cell20.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell20);

        final var body7cell21 =
                new Cell(new Paragraph("VII.10 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell21.setBorder(NO_BORDER);
        body7cell21.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell21);

        final var body7cell22 =
                new Cell(new Paragraph("Sõiduki dokumentide või võtmete mittetagastamisel või puuduliku varustusega " +
                        "sõiduki tagastamisel tasub Rentnik Rendileandjale leppetrahvi 250.-EUR iga kaotatud või puuduva dokumendi või " +
                        "eseme kohta. Purunenud rehvi või velje eest tuleb tasuda trahvi 300.-EUR.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell22.setBorder(NO_BORDER);
        body7cell22.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell22);

        final var body7cell23 =
                new Cell(new Paragraph("VII.11 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell23.setBorder(NO_BORDER);
        body7cell23.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell23);

        final var body7cell24 =
                new Cell(new Paragraph("Rentniku poolt sõiduki hülgamisel tasub Rentnik Rendileandjale leppetrahvi sõiduki soetusmaksumuse ulatuses. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell24.setBorder(NO_BORDER);
        body7cell24.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell24);

        final var body7cell25 =
                new Cell(new Paragraph("VII.12 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell25.setBorder(NO_BORDER);
        body7cell25.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell25);

        final var body7cell26 =
                new Cell(new Paragraph("Rentnik on kohustatud tasuma vara kasutamise käigus põhjustatud seaduserikkumiste korral " +
                        "kõik trahvid ja nõuded ning leppetrahvid vastavalt seaduses sätestatud korrale ning Rendileandja kehtivate hinnakirjade" +
                        " alusel, kuid mitte vähem kui 40.- EUR. Parkimistrahvid kaasa arvatud nendega seotud kulud, millest Rendileandjat ei " +
                        "ole teavitatud 3 päeva jooksul, nõutakse hiljem sisse kahekordselt. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell26.setBorder(NO_BORDER);
        body7cell26.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell26);

        final var body7cell27 =
                new Cell(new Paragraph("VII.13 ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell27.setBorder(NO_BORDER);
        body7cell27.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell27);

        final var body7cell28 =
                new Cell(new Paragraph("Kui Rentnik osaleb Rendisõidukiga liiklusõnnetuses, mille tõttu Rendileandja " +
                        "kindlustusriski koefitsent suureneb, tasub Rentnik ühekordset leppetrahvi 200.-EUR. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell28.setBorder(NO_BORDER);
        body7cell28.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell28);

        final var body7cell29 =
                new Cell(new Paragraph("VII.14",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell29.setBorder(NO_BORDER);
        body7cell29.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell29);

        final var body7cell30 =
                new Cell(new Paragraph("Käesolevale koostöölepingule alla kirjutades nõustub juhatuse liige või rentniku seaduslik esindaja " +
                        "vastutama rentnikuga solidaarselt ja vastavalt käesoleva lepingu tingimustele.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell30.setBorder(NO_BORDER);
        body7cell30.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell30);

        final var body7cell31 =
                new Cell(new Paragraph("VII.15",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell31.setBorder(NO_BORDER);
        body7cell31.setHorizontalAlignment(LEFT);
        body7.addCell(body7cell31);

        final var body7cell32 =
                new Cell(new Paragraph("Liiklusõnnetuse korral hüvitab Rentnik Rendileandjale tekitatud kahju täies mahus.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body7cell32.setBorder(NO_BORDER);
        body7cell32.setHorizontalAlignment(JUSTIFIED);
        body7.addCell(body7cell32);

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
                new Cell(new Paragraph(" RENDILEANDJA  KOHUSTUSED ",
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
                new Cell(new Paragraph(" Rendileandja kohustub sõiduki Rentnikule üle andma lepingus märgitud kokkulepitud kohas ja ajal. ",
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
                new Cell(new Paragraph(" Rendileandja kohustub täitma lepingut heas usus. ",
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
                new Cell(new Paragraph(" Rendileandja kohustub teostama sõiduki regulaarset tehnilist hooldust.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body8cell8.setBorder(NO_BORDER);
        body8cell8.setHorizontalAlignment(JUSTIFIED);
        body8.addCell(body8cell8);

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
                new Cell(new Paragraph("RENDILEANDJA ÕIGUSED",
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
                new Cell(new Paragraph("Rendileandjal on õigus kasutada rentniku tagatisraha katmata kohustuste hüvitamiseks.",
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
                new Cell(new Paragraph("Rendileandjal on õigus kontrollida sõiduki seisundit ja selle korrashoiu tagamist.",
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
                new Cell(new Paragraph("Rendileandja määrab sõiduki remondi koha, tingimused ja ulatuse sõltumata sõiduki asukohast.",
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
                new Cell(new Paragraph("Rendileandjal on õigus loobuda sõiduki rentimisest, lõpetada Koostööleping ja nõuda sõiduki kohest tagastamist," +
                        " kui Rentnik või teine juht äratab kahtlust, rikub Koostöölepingu tingimusi, ei tule toime sõiduki ekspluatatsiooniga, " +
                        "on esitanud lepingu sõlmimisel valeandmeid, kasutab sõidukit  pahatahtlikul eesmärgil, on Rendileandjat eksitanud või ilmnevad muud asjaolud," +
                        " mis võivad ohtu seada renditud sõiduki või Rendileandja huvid. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell10.setBorder(NO_BORDER);
        body9cell10.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell10);

        final var body9cell11 =
                new Cell(new Paragraph("IX.5",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell11.setBorder(NO_BORDER);
        body9cell11.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell11);

        final var body9cell12 =
                new Cell(new Paragraph("Juhul kui Rendileandja nõuab punktis IX. 4 toodud põhjustel Koostöölepingu katkestamist, " +
                        "on Rentnik kohustatud tagastama sõiduki Eesti Vabariigi siseselt Rendileandjale hiljemalt viie (5) tunni möödudes," +
                        " arvates Rendileandja poolsest telefoni teel edastatud teatest lepingu katkestamise kohta. " +
                        "Kui Rentnik sõidukit viie (5) tunni jooksul ei tagasta, kohustub Rentnik tasuma leppetrahvi 200.-EUR. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell12.setBorder(NO_BORDER);
        body9cell12.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell12);

        final var body9cell13 =
                new Cell(new Paragraph("IX.6",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell13.setBorder(NO_BORDER);
        body9cell13.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell13);

        final var body9cell14 =
                new Cell(new Paragraph("Sõiduki rikkest tingitud ja muud kahjud (avarii, liiklusõnnetus, reisi ärajäämine või katkemine, " +
                        "töö või muude kohustuste täitmata jätmisest tulenev kahju Rentniku või teise juhi ja kolmanda isiku vahel) ei kuulu kandmisele Rendileandja poolt." +
                        " Need kulud jäävad Rentniku enda kanda ja see on rendirisk. Võimalusel tagab Rendileandja vahetusauto." +
                        " Kui rike tekib Rendiperioodi ajal ja puudub Rentniku tahtlik või ettevaatamatu käitumine rikke tekkimises," +
                        " võib Rentnik nõuda kuni 10 protsendilist hinnaalandust sõlmitud rendiperioodi eest, kui Rendilandjal puudub võimalus sõiduki asendamiseks teisega. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell14.setBorder(NO_BORDER);
        body9cell14.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell14);

        final var body9cell15 =
                new Cell(new Paragraph("IX.7",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell15.setBorder(NO_BORDER);
        body9cell15.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell15);

        final var body9cell16 =
                new Cell(new Paragraph("Kahju tekkimise juhtumist Rendileandja mitteteavitamisel tasub Rentnik kolmekordselt päevarendisumma ja kannab Rendileandjale sellest tekkinud kahjud. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell16.setBorder(NO_BORDER);
        body9cell16.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell16);

        final var body9cell17 =
                new Cell(new Paragraph("IX.8",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell17.setBorder(NO_BORDER);
        body9cell17.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell17);

        final var body9cell18 =
                new Cell(new Paragraph("Rendiaja ületamisel ilma Rendileandja vastava nõusolekuta, on Rendileandjal õigus nõuda kahekordset renditasu iga rendiaega ületanud päeva eest," +
                        " samuti on Rentnik vastutav hilinemisest tulenevast majanduslikust kahjust, mis Rendileandjale seoses hilinemisega tekib.  ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell18.setBorder(NO_BORDER);
        body9cell18.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell18);

        final var body9cell19 =
                new Cell(new Paragraph("IX.9",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell19.setBorder(NO_BORDER);
        body9cell19.setHorizontalAlignment(LEFT);
        body9.addCell(body9cell19);

        final var body9cell20 =
                new Cell(new Paragraph("On rendileandjal õigus lõpetada rendilepingu erakorraliselt, juhul, kui ei ole rentnik tasunud kahte järjestikust " +
                        "üürimakset täies ulatuses ja/või üürivõla kogusumma ületab 500 eurot.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body9cell20.setBorder(NO_BORDER);
        body9cell20.setHorizontalAlignment(JUSTIFIED);
        body9.addCell(body9cell20);

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
                new Cell(new Paragraph("MUUD TINGIMUSED",
                        new Font(Font.TIMES_ROMAN, 9, Font.BOLD)));
        body10cell2.setBorder(NO_BORDER);
        body10cell2.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell2);

        final var body10cell3 =
                new Cell(new Paragraph("X.1",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell3.setBorder(NO_BORDER);
        body10cell3.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell3);

        final var body10cell4 =
                new Cell(new Paragraph("Koostöölepingust tulenevad vaidlused, milles Rentnik ja Rendileandja ei jõua kokkuleppele, " +
                        "lahendatakse Harju Maakohtus vastavalt seadusele. Vaidluse läbivaatamisel kohtus rakendatakse käesoleva lepingu tingimusi.",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell4.setBorder(NO_BORDER);
        body10cell4.setHorizontalAlignment(JUSTIFIED);
        body10.addCell(body10cell4);

        final var body10cell5 =
                new Cell(new Paragraph("X.2",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell5.setBorder(NO_BORDER);
        body10cell5.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell5);

        final var body10cell6 =
                new Cell(new Paragraph("Pooled lepivad kokku, et Lepingu tingimusteks ei loeta Poolte varasemaid tahteavaldusi, tegusid ega kokkuleppeid, mis ei ole Lepingus otseselt sätestatud. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell6.setBorder(NO_BORDER);
        body10cell6.setHorizontalAlignment(JUSTIFIED);
        body10.addCell(body10cell6);

        final var body10cell7 =
                new Cell(new Paragraph("X.3",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell7.setBorder(NO_BORDER);
        body10cell7.setHorizontalAlignment(LEFT);
        body10.addCell(body10cell7);

        final var body10cell8 =
                new Cell(new Paragraph("Käesolev leping on sõlmitud kahes eksemplaris, üks kummalegi lepingupoolele. ",
                        new Font(Font.TIMES_ROMAN, 9, Font.NORMAL)));
        body10cell8.setBorder(NO_BORDER);
        body10cell8.setHorizontalAlignment(JUSTIFIED);
        body10.addCell(body10cell8);

        contractPdfDoc.add(body10);

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
