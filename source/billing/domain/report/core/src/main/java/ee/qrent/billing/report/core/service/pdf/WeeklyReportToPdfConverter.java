package ee.qrent.billing.report.core.service.pdf;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.awt.Color.white;
import static java.lang.String.format;

import java.math.BigDecimal;

import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.Font.TIMES_ROMAN;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportToPdfConverter {

    private static String getTextOrEmpty(final String text) {
        if (text == null || text.isBlank()) {
            return "---";
        }
        return text;
    }


    @SneakyThrows
    public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
        final var weeklyReportPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
        final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
        final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
        final var header = getHeader(model.getFirstName(), model.getLastName(), model.getTaxNumber(), model.getCallSign());
        final var block1 = getBlock1(model.getAmount());
        final var block12 = getBlock12(model.getAmount());


        weeklyReportPdfDoc.open();
        weeklyReportPdfDoc.add(header);
        weeklyReportPdfDoc.add(block1);
        weeklyReportPdfDoc.add(block12);
        weeklyReportPdfDoc.close();
        writer.close();

        return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
    }


    @SneakyThrows
    private Table getHeader(
            final String firsrName, final String lastName, final Long taxNumber, final Integer callSign) {
        final var header = new Table(2);
        header.setPadding(0f);
        header.setSpacing(0f);
        header.setWidth(100f);
        header.setBorderColor(white);
        header.setHorizontalAlignment(RIGHT);
        header.setBorder(NO_BORDER);
        header.setBorder(NO_BORDER);

        final var logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
        logo.scaleAbsolute(150f, 60f);
        final var cell = new Cell(logo);
        cell.setRowspan(4);
        cell.setBorder(NO_BORDER);
        header.addCell(cell);

        final var cell1 =
                new Cell(
                        new Paragraph("Dear " + firsrName + lastName, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
        cell1.setBorder(NO_BORDER);
        header.addCell(cell1);

        final var cell2 =
                new Cell(
                        new Paragraph("Isikukood: " + taxNumber, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
        cell2.setBorder(NO_BORDER);
        header.addCell(cell2);

        final var cell3 =
                new Cell(
                        new Paragraph("Callsign: " + callSign, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
        cell3.setBorder(NO_BORDER);
        header.addCell(cell3);

        return header;
    }

    private Table getBlock1(final BigDecimal amount) {
        final var block1 = new Table(1);
        block1.setPadding(0f);
        block1.setSpacing(0f);
        block1.setWidth(100f);
        block1.setBorderColor(white);
        block1.setHorizontalAlignment(RIGHT);
        block1.setBorder(NO_BORDER);
        block1.setBorder(NO_BORDER);


        final var block11 = getChapterTable();
        block11.addCell(getChapterNumber("-"));
        block11.addCell(getChapterSummary("Согласно последним данным, внесенным в нашу программу, твои обязательства перед Q Takso Veod OÜ : \n" + amount +
                " \n за прошлую неделю {Week number} (24.03.25-30.0325) ** «1» / «2» / «3» **"));

        return block11;
    }

    private Table getBlock12(final BigDecimal amount) {
        final var block12 = new Table(1);
        block12.setPadding(0f);
        block12.setSpacing(0f);
        block12.setWidth(100f);
        block12.setBorderColor(white);
        block12.setHorizontalAlignment(RIGHT);
        block12.setBorder(NO_BORDER);
        block12.setBorder(NO_BORDER);

        final var block13 = getChapterTable();
        block13.addCell(getChapterNumber("-"));
        block13.addCell(getChapterSummary("Car : " +
                "\nDeposit obligation: " +
                "\nDeposit paid: " +
                "\nОбязательства за прошлую неделю : " +
                "\nБаланс на конец прошлой недели :" +
                "\nВивисы на коней прошлой недели : " +
                "\nБаланс на текущий момент : " +
                "\nВивисы на текущий момент : " +
                "\nБазовая стоимость аренды : " +
                "\nДВС : "));

        return block13;
    }







    protected static Table getChapterTable() {
        final var chapter = new Table(2);
        chapter.setWidths(new float[]{7, 100});
        chapter.setPadding(0f);
        chapter.setSpacing(0f);
        chapter.setWidth(100f);
        chapter.setBorderColor(white);
        chapter.setHorizontalAlignment(LEFT);
        chapter.setBorder(NO_BORDER);

        return chapter;
    }

    protected static Cell getChapterNumber(final String chapterNumber) {
        final var chapterCell =
                new Cell(new Paragraph(chapterNumber + ".", new Font(TIMES_ROMAN, 9, BOLD)));
        chapterCell.setBorder(NO_BORDER);
        chapterCell.setHorizontalAlignment(LEFT);

        return chapterCell;
    }

    protected static Cell getChapterSummary(final String chapterName) {
        final var chapterSummaryCell =
                new Cell(new Paragraph(chapterName, new Font(TIMES_ROMAN, 9, BOLD)));
        chapterSummaryCell.setBorder(NO_BORDER);
        chapterSummaryCell.setHorizontalAlignment(LEFT);

        return chapterSummaryCell;
    }


}

////////////////////////////////////// TO DO

  /*      private Table getBlock1 ( final BigDecimal amount ){
            final var block1 = new Table(1);
            block1.setPadding(0f);
            block1.setSpacing(0f);
            block1.setWidth(100f);
            block1.setBorderColor(white);
            block1.setHorizontalAlignment(RIGHT);
            block1.setBorder(NO_BORDER);
            block1.setBorder(NO_BORDER);


            final var chapter1 = getChapterTable();
            chapter1.addCell(getChapterNumber("-"));
            chapter1.addCell(getChapterSummary("Согласно последним данным, внесенным в нашу программу, твои обязательства перед Q Takso Veod OÜ " + amount +
                    "за прошлую неделю {Week number} (24.03.25-30.0325) ** «1» / «2» / «3» **"));
       *//* chapter1.addCell(getChapterNumber("1"));
        chapter1.addCell(
                getSubChapterText(
                        "** «1» ** – «были выполнены своевременно и в полном объеме – согласно условиям твоего договора. Твое сальдо на конец прошлой недели i** (24.03.25-30.0325) **i составило: 2,27 евро."));
        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " В результате на конец прошлой недели i** (24.03.25-30.0325) **i твое сальдо перед нами составило: «2,27 евро в виде предоплаты. Образовавшаяся на конец недели предоплата, " +
                                "будет учтена при рассчете суммы твоих общих обязательств следующей недели» / «0,00 евро в виде отсутствия обоюдных, востребованных обязательств»."
                                + "\n"
                                + "В знак нашей благодарности мы активировали все наши еженедельные бонусные кампании в твоем аккаунте на текущую неделю i** (31.03.25-06.04.25) **i. " +
                                "Соответствующие бонусные корректировки будут начислены в твой баланс в случае соблюдения тобой прочих условий.»"));

        chapter1.addCell(getChapterNumber("2"));
        chapter1.addCell(
                getSubChapterText(
                        " ** «2» ** – «были выполнены в полном объеме, но с опозданием. Твое сальдо на конец прошлой недели i** (24.03.25-30.0325) **i составило: 0 евро."));

        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " Это означает, что несмотря на то, что твои платежные обязательства перед Q Takso Veod OÜ были выполнены в полном объеме до конца прошлой недели " +
                                "i** (24.03.25-30.0325) **i , и тебе не будут начислены дополнительные виивисы / пени, наши еженедельные бонусные кампании не будут для дебя доступны. "));
        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        "Это так, поскольку первичным условием для участия в наших еженедельных бонусных кампаниях является своевременное, а именно – до вторника текущей недели" +
                                "включительно, выполнение тобой твоих договрных обязательств. Согласно подписанному тобой договору, под выполнением тобой твои договорных обязательств" +
                                " подразумевается оплата твоих текущих арендных обязательств в полном объеме, а также покрытие твоих задолженностей и прочих обязательств перед " +
                                "Q Takso Veod OÜ (если такие задолженности или обязательства имеются) в размере не меньшем, чем 25% от твоей текущей арендной платы еженедельно."));
        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " Мы будем благодарны, если ты закроешь свои обязательства по текущей неделе i** (31.03.25-06.04.25) **i без долгов и всрок. В таком случае бонусные кампании " +
                                "для тебя вновь будут активиованы, а виивисы / пени не будут начисены, даже несмотря на имеющуюся задолженность (если она будет иметься на тот момент).»"));

        chapter1.addCell(getChapterNumber("3"));
        chapter1.addCell(
                getSubChapterText(
                        " ** «3» ** – «не были выполнены своевременно и в полном объеме. Твое сальдо на конец прошлой недели i** (24.03.25-30.0325) **i составило: -38,16 евро.  "));
        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " В результате и у тебя образовалась новая задолженность в размере: -38,16 евро, которая прибавилась к уже существующей на тот момент задолженности в размере 0,00 евро. " +
                                "Как следствие, тебе будут начислены дополнительные виивисы / пени за просрочку твоих платежей в соответствии с условиями твоего договора, а наши еженедельные бонусных " +
                                "кампании не будут для дебя доступны."));

        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " Мы будем длагодарны, если ты закроешь свои обязательства по текущей неделе i** (31.03.25-06.04.25) **i без долгов и всрок. В таком случае еженедельные бонусные кампании " +
                                "для тебя вновь будут активиованы, а виивисы / пени не будут начисены, даже несмотря на имеющуюся задолженность (если она будет иметься на тот момент).»"));


        chapter1.addCell(getChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " В соответствии с этим твое сальдо перед Q Takso Veod OÜ по состоянию на утро понедельника текущей недели i** (31.03.25-06.04.25) **i составляют: -276,93 евро." +
                                " Оплаты этой суммы мы ждем до вторника текущей недели включительно.  "));

        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " Данные о корректировках с заработков или обязательств из приложений (Bolt, Forus) будут внесены в твой баланс до 12:00 вторника i** (01.04.25) **i этой недели. " +
                                "Свой обновленный отчет и сальдо ты получишь вскоре после этого."));

        chapter1.addCell(getSubChapterNumber("-"));
        chapter1.addCell(
                getSubChapterText(
                        " Актуальный на даный момент отчет о твоих обязательствах на текущую неделю i** (31.03.25-06.04.25) **i ты сможешь посмотреть ниже:"));
*//*


            //  return block1;


        }


*/

// TODO add template with data


