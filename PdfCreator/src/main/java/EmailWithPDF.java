import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


public class EmailWithPDF {

    /**
     * Sends an email with a PDF attachment.
     */

    public void email() {
        String sender = "billing@qrent.ee"; //replace this with a valid sender email address
        String recipient = "billing@qrent.ee"; //replace this with a valid recipient email address
        String content = "test sending email"; //this will be the text of the email
        String subject = "E mail PDF"; //this will be the subject of the email

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");

        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        props.put("mail.smtp.auth", "true");
           props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", "smtp.zone.eu");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.trust", "smtp.zone.eu");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("billing@qrent.ee", "shzlahiyqdwcbzqk");
                    }
                });

        ByteArrayOutputStream outputStream = null;

        try {
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            //now write the PDF content to the output stream
            outputStream = new ByteArrayOutputStream();
            writePdf(outputStream);
            byte[] bytes = outputStream.toByteArray();

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("test.pdf");

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);

            //construct the mime message

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);

            //send off the email
            Transport.send(mimeMessage);

            System.out.println("sent from " + sender +
                    ", to " + recipient);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if (null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (Exception ex) {
                }
            }
        }
    }


    /**
     * Writes the content of a PDF file (using iText API)
     * to the {@link OutputStream}.
     *
     * @param outputStream {@link OutputStream}.
     * @throws Exception
     */
    public void writePdf(OutputStream outputStream) throws Exception {
 /*           Document document1 = new Document();
            pdfDocument.setDefaultPageSize(PageSize.A4);
            PdfWriter.getInstance(document1, outputStream);

            document1.open();

            document1.addTitle("Test PDF");
            document1.addSubject("Testing email PDF");
            document1.addKeywords("iText, email");
            document1.addAuthor("Jee Vang");
            document1.addCreator("Jee Vang");

            Paragraph paragraph = new Paragraph();
            paragraph.add(new Chunk("hello!"));
            document1.add(paragraph);
           document1.close();
      */

//PDF to File

        String path = "c:\\work\\qTemplate.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);

        String imgPath = "images\\qRentalGroup_gorznt_80.png";
        ImageData imageData = ImageDataFactory.create(imgPath);
        Image image = new Image(imageData);

        //    PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument);


        //    PdfWriter.getInstance(document, outputStream);

//Logo
        float columnWidth[] = {300f, 300f};
        Table tableLogo = new Table(columnWidth);

        tableLogo.addCell(new Cell(2, 0).add(image)
                .setBorder(Border.NO_BORDER));

        tableLogo.addCell(new Cell().add("Arve Nr.: year_week_callSign  Date/Month/Year")
                .setBold()
                .setFontSize(14F)
                .setBorder(Border.NO_BORDER));

        tableLogo.addCell(new Cell().add("Ajaperiood: Date-Date/Month/Year")
                .setBold()
                .setFontSize(11F)
                .setBorder(Border.NO_BORDER));


//Rekvizity Firmy voditelia

        float columnWidth1[] = {100f, 200f};
        Table table = new Table(columnWidth1);

        table.addCell(new Cell().add("Maksja:")
                .setFontSize(10f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("FIE Toomas Ilves") //Driver:company
                .setFontSize(10f)
                .setBorder(Border.NO_BORDER));


        table.addCell(new Cell().add("Aadress:")
                .setFontSize(10f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Toompea 1, Tallinn") //Driver:company_address
                .setFontSize(10f)
                .setBorder(Border.NO_BORDER));


        table.addCell(new Cell().add("Äriregistri nr:")
                .setFontSize(10f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("123456789") //Driver:reg_number
                .setFontSize(10f)
                .setBorder(Border.NO_BORDER));


        table.addCell(new Cell().add("KMKR nr. :")
                .setFontSize(10f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(" ") //Driver:kmkr_number !!! EE123456789
                .setFontSize(10f)
                .setBorder(Border.NO_BORDER));


        // Main table
        float columnWidth2[] = {400f, 100f};
        Table mainTable = new Table(columnWidth2);

        mainTable.addCell(new Cell().add("Selgitus")
                .setFontSize(12f)
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(Color.GRAY)
                .setBold());

        mainTable.addCell(new Cell().add("Summa")
                .setFontSize(12f)
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(Color.GRAY)
                .setBold());

        // Add cells for item
        mainTable.addCell(new Cell().add("Renditasu") // Transaction_type: description
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT));


        //add cells for summa
        mainTable.addCell(new Cell().add("230")
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.CENTER));


        //Total
        float columnTotal[] = {400f, 100f};
        Table totalTable = new Table(columnTotal);

        totalTable.addCell(new Cell().add(" Summa: ")
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
        );
        totalTable.addCell(new Cell().add(" 1111 ")   // Summa polei
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
        );

        totalTable.addCell(new Cell().add(" Käibemaks " + "  " + "%: ")   // 0 ili 20
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add(" 1111 ")   // (Summa polei)*0.2
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER));

        totalTable.addCell(new Cell().add(" Arve summa EUR: ")
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add(" 2222 ")   // (Summa polei)+ Käibemaks
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER));


        //total2
        float columnTotal2[] = {400f, 100f};
        Table total2Table = new Table(columnTotal2);

        total2Table.addCell(new Cell().add(" Eelmise perioodi ettemaks: ")
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        total2Table.addCell(new Cell().add(" 111 ")   //  + na nachalo perioda vystavlenia scheta
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER));

        total2Table.addCell(new Cell().add(" Eelmise perioodi võlgnevus:")
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        total2Table.addCell(new Cell().add(" 222 ")   // -(deolg)na nachalo perioda vystavlenia scheta
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER));

        total2Table.addCell(new Cell().add("Tasuda kokku: ")
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));
        total2Table.addCell(new Cell().add(" 2111 ")   // Total summa
                .setFontSize(10f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER));


        // Footer
        float columnFooter[] = {200f, 200f, 200f};
        Table footerTable = new Table(columnFooter);


        footerTable.addFooterCell(new Cell().add("CarAutoRent OÜ") // firm.firm_name
                .setFontSize(11f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("Lasnamäe 30a, Tallinn") // firm.post_address
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("SWEDBANK") // firm.bank
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));

        footerTable.addFooterCell(new Cell().add("Äriregistri nr.:" + " 126420912") // firm.reg_number
                .setFontSize(11f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("E-mail:" + " info@qtakso.ee") // firm.email
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("IBAN:" + " EE717700771008775640") // firm.iban
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));

        footerTable.addFooterCell(new Cell().add("KMKR :" + " ") // firm.vat_number
                .setFontSize(11f)
                .setBold()
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("Telefon: " + " (+372) 5358 5338") // firm.phone
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));
        footerTable.addFooterCell(new Cell().add("S.W.I.F.T.:" + " HABAEE 2X") // firm.swift !!!
                .setFontSize(11f)
                .setBorder(Border.NO_BORDER));

        document.add(tableLogo);
        document.add(new Paragraph("\n"));
        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(mainTable);
        document.add(new Paragraph("\n"));
        document.add(totalTable);
        document.add(new Paragraph("\n"));
        document.add(total2Table);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(footerTable);


        document.close();

        System.out.println("PDF Created");


    }

    public static void main(String[] args) throws IOException {


        EmailWithPDF demo = new EmailWithPDF();
        demo.email();


    }
}
