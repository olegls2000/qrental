package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.awt.Color.BLACK;
import static java.awt.Color.white;
import static java.lang.String.format;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.TIMES_ROMAN;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportMondayPdfConverter implements WeeklyReportPdfConversionStrategy {

  private static String getTextOrEmpty(final String text) {
    if (text == null || text.isBlank()) {
      return "---";
    }
    return text;
  }

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.MONDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var weeklyReportPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    final var header =
        getHeader(
            model.getFirstName(), model.getLastName(), model.getTaxNumber(), model.getCallSign());
        final var block1 = getBlock1(model);
//        final var conditionalBlock = getConditionalBlock(model);
//        final var block20 = getBlock20(model);
        final var block22 = getBlock22(model);
        final var block25 = getBlock25(model);
        final var txTypesBlock = getTransactionTypesBlock(model);
 

    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(header);
    weeklyReportPdfDoc.add(block1);
//        weeklyReportPdfDoc.add(conditionalBlock);
//        weeklyReportPdfDoc.add(block20);
        weeklyReportPdfDoc.add(block22);
        weeklyReportPdfDoc.add(block25);
        weeklyReportPdfDoc.add(txTypesBlock);
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

  @SneakyThrows
  private Table getHeader(
            final String firstName, final String lastName, final Long taxNumber, final Integer callSign) {
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
            new Paragraph(
                                "Dear " + firstName + lastName, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell1.setBorder(NO_BORDER);
    header.addCell(cell1);

    final var cell2 =
        new Cell(
            new Paragraph("Isikukood: " + taxNumber, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell2.setBorder(NO_BORDER);
    header.addCell(cell2);

    final var cell3 =
        new Cell(new Paragraph("Callsign: " + callSign, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell3.setBorder(NO_BORDER);
    header.addCell(cell3);

    return header;
  }

    private Table getBlock1(final WeeklyReportPdfModel model) {
    final var block1 = new Table(1);
    block1.setPadding(0f);
    block1.setSpacing(0f);
    block1.setWidth(100f);
    block1.setBorderColor(white);
    block1.setHorizontalAlignment(RIGHT);
    block1.setBorder(NO_BORDER);
    block1.setBorder(NO_BORDER);

        final var weekNumber = getIsoWeek(model.getPreviousWeekStart());
        final var rangeText =
                format("%s-%s", formatDate(model.getPreviousWeekStart()), formatDate(model.getPreviousWeekEnd()));

        block1.addCell(getChapterNumber("-"));
        block1.addCell(
                getChapterSummary(
                        "Согласно последним данным, внесенным в нашу программу, твой баланс составляет: "
                                + formatAmount(model.getAmount())
                                + " за прошлую неделю "
                                + weekNumber
                                + " ("
                                + rangeText
                ));

        return block1;
    }

//    private Table getConditionalBlock(final WeeklyReportPdfModel model) {
//        final var balanceAmountSunday = model.getBalanceAmountSunday();
//
//        if (balanceAmountSunday == null) {
//            return getBlock12(model); // default case
//        }
//
//        final int comparison = balanceAmountSunday.compareTo(BigDecimal.ZERO);
//
//        if (comparison > 0) {
//            return getBlock8(model); // Amount Sunday > 0
//        } else if (comparison == 0) {
//            return getBlock12(model); // Amount Sunday = 0
//        } else {
//            return getBlock16(model); // Amount Sunday < 0
//        }
//    }

//    private Table getBlock8(final WeeklyReportPdfModel model) {
//        final var block = getChapterTable();
//
//        block.addCell(getChapterNumber("1"));
//        block.addCell(
//                getChapterSummary(
//                        " – «были выполнены своевременно и в полном объеме – согласно условиям твоего договора. Твое сальдо на конец прошлой недели "
//                                + formatDate(model.getPreviousWeekStart())
//                                + "-"
//                                + formatDate(model.getPreviousWeekEnd())
//                                + " составило: "
//                                + formatAmount(model.getBalanceAmountSunday())
//                                + "."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "В результате на конец прошлой недели "
//                                + formatDate(model.getPreviousWeekStart())
//                                + "-"
//                                + formatDate(model.getPreviousWeekEnd())
//                                + " твое сальдо перед нами составило: «"
//                                + formatAmount(model.getBalanceAmountSunday())
//                                + " в виде предоплаты. Образовавшаяся на конец недели предоплата, будет учтена при рассчете суммы твоих общих обязательств следующей недели» / «0,00 € в виде отсутствия обоюдных, востребованных обязательств»."
//                                + "\n"
//                                + "В знак нашей благодарности мы активировали все наши еженедельные бонусные кампании в твоем аккаунте на текущую неделю "
//                                + formatDate(model.getCurrentWeekStart())
//                                + "-"
//                                + formatDate(model.getCurrentWeekEnd())
//                                + ". Соответствующие бонусные корректировки будут начислены в твой баланс в случае соблюдения тобой прочих условий.»"));
//
//        return block;
//    }
//
//    private Table getBlock12(final WeeklyReportPdfModel model) {
//        final var block = getChapterTable();
//
//        block.addCell(getChapterNumber("1"));
//        block.addCell(
//        getChapterSummary(
//                        " – «были выполнены в полном объеме, но с опозданием. Твое сальдо на конец прошлой недели "
//                                + formatDate(model.getPreviousWeekStart())
//                                + "-"
//                                + formatDate(model.getPreviousWeekEnd())
//                                + " составило: "
//                                + formatAmount(model.getBalanceAmountSunday())
//                                + "."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "Это означает, что несмотря на то, что твои платежные обязательства перед Q Takso Veod OÜ были выполнены в полном объеме до конца прошлой недели "
//                                + formatDate(model.getPreviousWeekStart())
//                                + "-"
//                                + formatDate(model.getPreviousWeekEnd())
//                                + ", и тебе не будут начислены дополнительные виивисы / пени, наши еженедельные бонусные кампании не будут для дебя доступны."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "Это так, поскольку первичным условием для участия в наших еженедельных бонусных кампаниях является своевременное, а именно – до вторника текущей недели включительно, выполнение тобой твоих договрных обязательств. Согласно подписанному тобой договору, под выполнением тобой твои договорных обязательств подразумевается оплата твоих текущих арендных обязательств в полном объеме, а также покрытие твоих задолженностей и прочих обязательств перед Q Takso Veod OÜ (если такие задолженности или обязательства имеются) в размере не меньшем, чем 25% от твоей текущей арендной платы еженедельно."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "Мы будем благодарны, если ты закроешь свои обязательства по текущей неделе "
//                                + formatDate(model.getCurrentWeekStart())
//                                + "-"
//                                + formatDate(model.getCurrentWeekEnd())
//                                + " без долгов и всрок. В таком случае бонусные кампании для тебя вновь будут активиованы, а виивисы / пени не будут начислены, даже несмотря на имеющуюся задолженность (если она будет иметься на тот момент).»"));
//
//        return block;
//    }
//
//    private Table getBlock16(final WeeklyReportPdfModel model) {
//        final var block = getChapterTable();
//
//        block.addCell(getChapterNumber("1"));
//        block.addCell(
//                getChapterSummary(
//                        " – «не были выполнены своевременно и в полном объеме. Твое сальдо на конец прошлой недели "
//                                + formatDate(model.getPreviousWeekStart())
//                                + "-"
//                                + formatDate(model.getPreviousWeekEnd())
//                                + " составило: "
//                                + formatAmount(model.getBalanceAmountSunday())
//                                + "."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "В результате и у тебя образовалась новая задолженность в размере: "
//                                + formatAmount(model.getBalanceAmountSunday())
//                                + ", которая прибавилась к уже существующей на тот момент задолженности в размере 0,00 €. Как следствие, тебе будут начислены дополнительные виивисы / пени за просрочку твоих платежей в соответствии с условиями твоего договора, а наши еженедельные бонусных кампании не будут для дебя доступны."));
//
//        block.addCell(getSubChapterNumber("-"));
//        block.addCell(
//                getSubChapterText(
//                        "Мы будем длагодарны, если ты закроешь свои обязательства по текущей неделе "
//                                + formatDate(model.getCurrentWeekStart())
//                                + "-"
//                                + formatDate(model.getCurrentWeekEnd())
//                                + " без долгов и всрок. В таком случае еженедельные бонусные кампании для тебя вновь будут активиованы, а виивисы / пени не будут начислены, даже несмотря на имеющуюся задолженность (если она будет иметься на тот момент).»"));
//
//        return block;
//    }

//    private Table getBlock20(final WeeklyReportPdfModel model) {
//        final var block = getChapterTable();
//
//        block.addCell(getChapterNumber("-"));
//        block.addCell(
//                getChapterSummary(
//                        "В соответствии с этим твое сальдо перед Q Takso Veod OÜ по состоянию на утро понедельника текущей недели "
//                                + formatDate(model.getCurrentWeekStart())
//                                + "-"
//                                + formatDate(model.getCurrentWeekEnd())
//                                + " составляют: "
//                                + formatAmount(model.getBalanceAmountAtCalculationMoment())
//                                + ". Оплаты этой суммы мы ждем до вторника текущей недели включительно."));
//
//        return block;
//    }

    private Table getBlock22(final WeeklyReportPdfModel model) {
        final var block = getChapterTable();
        
        final var tuesday = model.getCurrentWeekStart() != null ? model.getCurrentWeekStart().plusDays(1) : null;
        
        block.addCell(getChapterNumber("-"));
        block.addCell(
        getChapterSummary(
                        "Данные о корректировках с заработков или обязательств из приложений (Bolt, Forus) будут внесены в твой баланс до 12:00 вторника "
                                + (tuesday != null ? formatDate(tuesday) : "")
                                + " этой недели. Свой обновленный отчет и сальдо ты получишь вскоре после этого."));
        
        return block;
    }

    private Table getBlock25(final WeeklyReportPdfModel model) {
        final var block = getChapterTable();
        
        final var currentRange = model.getCurrentWeekStart() != null && model.getCurrentWeekEnd() != null
                ? format("%s-%s", formatDate(model.getCurrentWeekStart()), formatDate(model.getCurrentWeekEnd()))
                : "";
        
        block.addCell(getChapterNumber("-"));
        block.addCell(
                getChapterSummary(
                        "Актуальный на данный момент отчет о твоих обязательствах на текущую неделю "
                                + currentRange
                                + " ты сможешь посмотреть ниже:"));
        
        block.addCell(getSubChapterNumber("-"));
        block.addCell(
                getSubChapterText(
                        "Машина : " + getTextOrEmpty(model.getCarRegistrationNumber())
                                + "\nДепозит : " + formatAmount(model.getDepositObligation())
                                + "\nОплаченный депозит : " + formatAmount(model.getDepositPaid())
                                + "\nОбязательства за прошлую неделю ("
                                + formatDate(model.getPreviousWeekStart())
                                + "-"
                                + formatDate(model.getPreviousWeekEnd())
                                + "): "
                                + formatAmount(model.getFeeAmountSunday())
                                + "\nБаланс на конец прошлой недели: "
                                + formatAmount(model.getBalanceAmountSunday())
                                + "\nВивисы на конец прошлой недели: ---"
                                + "\nБаланс на текущий момент ("
                                + formatAmount(model.getBalanceAmountSunday())
                                + "-"
                                + formatDate(model.getCurrentWeekEnd())
                                + "): "
                                + formatAmount(model.getBalanceAmountAtCalculationMoment())
                                + "\nВивисы на текущий момент: ---"));
    //                            + "\nБазовая стоимость аренды: ---"
    //                            + "\nДВС: ---"));

        return block;
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

    protected static Table getDataTable() {
        final var chapter = new Table(3);
        chapter.setWidths(new float[]{7, 100, 10});
        chapter.setPadding(0f);
        chapter.setSpacing(0f);
        chapter.setWidth(100f);
        chapter.setBorderColor(white);
        chapter.setHorizontalAlignment(CENTER);
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

    private static String formatDate(final LocalDate date) {
        if (date == null) {
            return "";
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        return date.format(formatter);
    }

    private static int getIsoWeek(final LocalDate date) {
        if (date == null) {
            return 0;
        }
        final WeekFields wf = WeekFields.of(Locale.getDefault());
        return date.get(wf.weekOfWeekBasedYear());
    }

    private static String formatAmount(final BigDecimal amount) {
        if (amount == null) {
            return "0.00 €";
        }
        return String.format(Locale.US, "%,.2f €", amount);
    }

    protected static Cell getSubChapterNumber(final String subChapterNumber) {
        final var subChapterCell =
                new Cell(new Paragraph(subChapterNumber + ".", new Font(TIMES_ROMAN, 8, BOLD)));
        subChapterCell.setBorder(NO_BORDER);
        subChapterCell.setHorizontalAlignment(LEFT);

        return subChapterCell;
    }

    protected static Cell getSubChapterText(final String subChapterText) {
        final var subChapterTextCell =
                new Cell(new Paragraph(subChapterText, new Font(TIMES_ROMAN, 8, BOLD)));
        subChapterTextCell.setBorder(NO_BORDER);
        subChapterTextCell.setHorizontalAlignment(LEFT);

        return subChapterTextCell;
    }

    private Table getTransactionTypesBlock(final WeeklyReportPdfModel model) {
        final var block = getDataTable();
        block.addCell(getChapterNumber(""));
        block.addCell(getChapterSummary("Детализация транзакций за прошлую неделю"));
        block.addCell(getChapterSummary(" "));

        final var map = model.getTransactionTypesVsAmount();
        if (map == null || map.isEmpty()) {
            block.addCell(getSubChapterNumber(""));
            block.addCell(getSubChapterText("Нет данных по типам транзакций"));
            return block;
        }

        for (final var entry : map.entrySet()) {
            final var type = entry.getKey();
            final var amount = entry.getValue();
            block.addCell(getSubChapterNumber(""));
            block.addCell(getSubChapterNumber(type));
            block.addCell(getSubChapterText(formatAmount(amount)));
        }

        return block;
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
