package ee.qrent.billing.contract.core.service.pdf.strategy;

import static com.lowagie.text.Font.*;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.awt.Color.white;
import static java.lang.String.format;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.SneakyThrows;

public class ContractToPdfConversionStrategyAfter11January2026ForFieFor6And4Weeks
    extends AbstractContractToPdfConversionStrategy {

  public ContractToPdfConversionStrategyAfter11January2026ForFieFor6And4Weeks(
      ContractLoadPort loadPort) {
    super(loadPort);
  }

  @Override
  public boolean canApply(final ContractPdfModel model) {

    return isContractAfterNew1ContractDateAndFie(model)
        && (isContractFor6Weeks(model) || isContractFor4Weeks(model));
  }

  @SneakyThrows
  @Override
  public InputStream getPdfInputStream(final ContractPdfModel model) {
    final var pdfDocument = getDocument();
    final var outputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(pdfDocument, outputStream);
    pdfDocument.open();
    pdfDocument.add(getHeaderTable(model));
    pdfDocument.add(new Paragraph(""));
    pdfDocument.add(getRenterTable(model));
    pdfDocument.add(getTenantTable(model));
   // addLhvChapterIfNecessary(model, pdfDocument);

    final var chapter1 = getChapterTable();
    chapter1.addCell(getChapterNumber("I"));
    chapter1.addCell(getChapterSummary("Üldsätted"));
    chapter1.addCell(getSubChapterNumber("1.1"));
    chapter1.addCell(
        getSubChapterText(
            "Allkirjastades käesoleva lepingu kinnitab Rentnik, et ta sõlmib lepingu füüsilise isikuna, kes tegutseb ettevõtjana ning teostab ettevõtlust FIE-na. " +
                    "Rentnik kasutab renditud sõidukit üksnes ettevõtlustulu teenimiseks tasulise sõiduteenuse osutamiseks või muuks majandus- või kutsetegevuseks, " +
                    "mis on otseselt seotud tema ettevõtlusega, ega kasuta sõidukit isiklikes ega olmelistes eesmärkides. Rentnik kinnitab, et ta ei tegutse tarbijana " +
                    "VÕS § 1 lg 5 ega § 143 tähenduses ning pooled kinnitavad üheselt, et käesolev leping ei ole tarbijaleping ega kuulu tarbijalepingutele kohalduva regulatsiooni alla. " +
                    "Rentnik vastutab käesoleva lepingu täitmise eest kogu oma varaga vastavalt äriseadustikule."));
    chapter1.addCell(getSubChapterNumber("1.1.1"));
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingu juurde kuuluvad lisadena kõik dokumendid, sealhulgas „Tüüptingimused“, üleandmise-vastuvõtmise akt, „TSK tingimused“ ja „Uus Juht“, " +
                    "mis on lepingu lahutamatud osad. Rentnik kinnitab, et on kõigi nimetatud lisade tingimustega tutvunud ja nendega täielikult nõus."));
    chapter1.addCell(getSubChapterNumber("1.1.2"));
    chapter1.addCell(
        getSubChapterText(
            "Koostöölepingu eritingimused on ülimuslikud Tüüptingimuste ees. Juhul kui dokumentide vahel esineb vastuolu," +
                    " kohaldatakse Koostöölepingu sätteid, välja arvatud seadusest tulenevalt teisiti."));
    chapter1.addCell(getSubChapterNumber("1.1.3"));
    chapter1.addCell(
        getSubChapterText(
            "Kõik käesoleva lepinguga seotud või sellele lisatud dokumendid, mis on loetletud käesoleva lepingu punktis 1.1.1, " +
                    "on käesoleva lepingu lahutamatu osa ning on pooltele siduvad kogu rendilepingu kehtivusaja jooksul."));

    chapter1.addCell(getSubChapterNumber("1.2"));
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa “Tüüptingimused” nr. 081225 lepingulisa on koostatud ja allkirjastatud 08.12.2025 (Signature Timestamp UTC - 08.12.2025 18:51:37 +00:00) ning on saadaval järgmisel lingil:"
                + "https://drive.google.com/file/d/1dW5zJ2N33d6k3W4TdmSMFMHGZD8VURso/view?usp=sharing"));

    chapter1.addCell(getSubChapterNumber("1.2.1"));
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa eestikeelne tekst on saadaval järgmisel lingil: "  // Ссылка на страничку на сайте с эстонским текстом из типовых условий.
                    + "www.qrent.ee/tuuptingimused/tt_pdf_est "));
    chapter1.addCell(getSubChapterNumber("1.2.2"));
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa venekeelne (по-русски) tekst on saadaval järgmisel lingil: " //  Ссылка на страничку на сайте с русским переводом.
                + "www.qrent.ee/tuuptingimused/tt_pdf_rus "));
    chapter1.addCell(getSubChapterNumber("1.2.3"));
    chapter1.addCell(
            getSubChapterText(
                    "Käesoleva lepingulisa inglisekeelne (in English) tekst on saadaval järgmisel lingil: " //  Ссылка на страничку на сайте с английским переводом.
                            + "www.qrent.ee/tuuptingimused/tt_pdf_rus "));
    chapter1.addCell(getSubChapterNumber("1.2.4"));
    chapter1.addCell(
        getSubChapterText(
            "Tõlked mis tahes keelde võivad olla ebatäpsed ja on mõeldud üksnes tutvumiseks."));

    chapter1.addCell(getSubChapterNumber("1.3"));
    chapter1.addCell(
        getSubChapterText(
            "Kõik käesoleva lepingu eritingimused on esitatud eraldi ja poolte vahel kokku lepitud. Pooled kinnitavad, et iga eritingimus on sama kohustusliku jõuga kui üldtingimused."));

    chapter1.addCell(getSubChapterNumber("1.4"));
    chapter1.addCell(
        getSubChapterText(
            "Kõik poolte kohustused, mis tulenevad “Tüüptingimused” lepingulisa tingimustest, on täitmiseks kohustuslikud nii Rentnikule, kui ka Rendileandjale."));

    chapter1.addCell(getSubChapterNumber("1.5"));
    chapter1.addCell(
        getSubChapterText(
            "Kui käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimuste vahel on vastuolu, kehtivad käesoleva lepingu sätted, välja arvatud juhul, kui seadus näeb ette teisiti."));

    chapter1.addCell(getSubChapterNumber("1.6"));
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimuste ebaõige täitmise või täitmata jätmise korral kannavad pooled vastutust käesoleva lepingu," +
                    " “Tüüptingimused” lepingulisa ja kehtivate õigusnormide järgi."));

    chapter1.addCell(getSubChapterNumber("1.7"));
    chapter1.addCell(
        getSubChapterText(
            "Rentnik kinnitab, et renditeenuse kasutamine toimub üksnes ettevõtlustulu teenimiseks ning on lahutamatult seotud tema FIE majandus- või kutsetegevusega. " +
                    "Rentnik ei kasuta renditud sõidukit isiklikes ega olmelistes eesmärkides ning ei tegutse tarbijana VÕS § 1 lg 5 ja § 143 tähenduses. " +
                    "Renditeenus ei ole tarbijaleping ning sellele ei kohaldata tarbijakaitselisi sätteid."));

    chapter1.addCell(getSubChapterNumber("1.8"));
    chapter1.addCell(
        getSubChapterText(
            "Oma allkirjaga kinnitab Rentnik, et on ta teadlik ja nõustub, et iga renditud auto täispika kalendrinädala " +
                    "(Esmaspäevast – Pühhapäevani kaasarvatud) rendimaksumus on määratud vastavas üleandmise-vastuvõtmise aktis ning et tuleb nimetatud tasu tasuda " +
                    "õigeaegselt ja täies ulatuses - jooksva rendinädala Teisipäeva kella 16:00’ni. Hilinenud makse loetakse oluliseks lepingurikkumiseks käesoleva lepingu mõttes." +
                    " Maksekorralduses märgitud selgitus ei mõjuta ettemaksu arvestamist vastavalt Tüüptingimuste p 2.1.5."));

    chapter1.addCell(getSubChapterNumber("1.8.1"));
    chapter1.addCell(
        getSubChapterText(
            "Rentnik kinnitab nõusolekut osaleda Rendileandja boonuskampaaniates, mille üldtingimused, arvestamise kord, eeltingimused, muutmise ja lõpetamise reeglid on sätestatud Tüüptingimuste peatükis XV." +
                    " Rentnik nõustub, et boonuste andmine ei ole Rendileandja kohustus ning kampaaniate kohaldamine toimub üksnes Tüüptingimustes määratud alustel."));

    chapter1.addCell(getSubChapterNumber("1.9"));
    chapter1.addCell(
        getSubChapterText(
            "Käesolev leping on tähtajatu, st sellel ei ole eelnevalt kindlaksmääratud lõpptähtaega, kuid see näeb ette kohustusliku minimaalse rendiperioodi pikkusega  "
                + getDuration(model)
                + " järjestikust täispikka kalendrinädalat. Minimaalse rendiperioodi kokkulepe ei ole vastuolus lepingu tähtajatu iseloomuga ning kujutab endast poolte vahel " +
                    "selgesõnaliselt kokku lepitud olulist tingimust. Rentnik on kohustatud tasuma minimaalse rendiperioodi eest täies ulatuses sõltumata sõiduki tegelikust " +
                    "kasutamisest, kasutamiskatkestustest, ennetähtaegsest tagastamisest või lepingu lõpetamisest mis tahes põhjusel. " +
                    "Minimaalse rendiperioodi arvutamise täpsem kord ning sellega seotud tingimused on kirjeldatud Tüüptingimuste punktides 2.22 ja 3.24."));

    chapter1.addCell(getSubChapterNumber("1.10"));
    chapter1.addCell(
        getSubChapterText(
            " Lepingu ülesütlemine on lubatud ainult kirjalikult, saates teate Rendileandja ametlikule e-posti aadressile." +
                    " Rentnik peab ülesütlemisest teatama vähemalt kaks (2) täispikka kalendrinädalat ette. Etteteatamine ei lühenda minimaalse rendiperioodi kestust ning Rentnik " +
                    "on kohustatud tasuma renditasu etteteatamistähtaja ja minimaalse rendiperioodi lõpuni olenemata sõiduki kasutamisest või tagastamisest. " +
                    "Ülesütlemise täpsem kord, etteteatamistähtaja arvestamine, rendihinna kohaldamine ning kasutusõigusega seotud tingimused on sätestatud Tüüptingimuste punktis 4.10."));

    pdfDocument.add(chapter1);

    final var chapter2 = getChapterTable();
    chapter2.addCell(getChapterNumber("II"));
    chapter2.addCell(getChapterSummary("Täiendava sisekindlustus (edaspidi TSK)"));
    chapter2.addCell(getSubChapterNumber("2.1"));
    chapter2.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa “TSK Tingimused” nr. 08122025 lepingulisa on koostatud ja allkirjastatud 08.12.2025 (Signature Timestamp UTC - 08.12.2025 18:54:19 +00:00) ning on saadaval järgmisel lingil: "
                + "https://drive.google.com/file/d/1AJY1dZzvXuNYjUtRQi8kPQGeBua_hHxw/view?usp=sharing"));

    chapter2.addCell(getSubChapterNumber("2.1.1"));
    chapter2.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa eestikeelne tekst on saadaval järgmisel lingil: "  //  Ссылка на страничку на сайте с эстонским текстом из типовых условий.
                    + "www.qrent.ee/tsk/tsk_pdf_est "));

    chapter2.addCell(getSubChapterNumber("2.1.2"));
    chapter2.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa venekeelne (по-русски) tekst on saadaval järgmisel lingil: "  //  Ссылка на страничку на сайте с русским переводом.
                + "www.qrent.ee/tsk/tsk_pdf_rus "));

    chapter2.addCell(getSubChapterNumber("2.1.3"));
    chapter2.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa inglisekeelne (in English) tekst on saadaval järgmisel lingil:" //  ССсылка на страничку на сайте с английским переводом.
                + "www.qrent.ee/tsk/tsk_pdf_rus "));

    chapter2.addCell(getSubChapterNumber("2.1.4"));
    chapter2.addCell(
        getSubChapterText(
            "Tõlked mis tahes keelde võivad olla ebatäpsed ja on mõeldud üksnes tutvumiseks."));

    chapter2.addCell(getSubChapterNumber("2.2"));
    chapter2.addCell(
        getSubChapterText(
            "Vastavalt “TSK Tingimused” lepingulisa tingimustele osutab Q Takso Veod OÜ täiendava sisekindlustuse teenust alates 02.06.2025 käesoleva lepingu alusel renditavatele autodele." +
                    " Selle teenuse tingimused on lühidalt kirjeldatud käesoleva lepingu paragrahvis ja üksikasjalikum ülalmainitud lepingulisas."));

    chapter2.addCell(getSubChapterNumber("2.3"));
    chapter2.addCell(
        getSubChapterText(
            "Maksimaalne frantsiisi summa rendiautole kahju tekitamisel rendiperioodi ajal on 600 eurot. See tingimus kehtib TSK alusel, mille tingimused on kirjeldatud \"TSK tingimused\" lepingulisas. "));
    chapter2.addCell(getSubChapterNumber("2.4"));
    chapter2.addCell(
        getSubChapterText(
            "TSK pakub sõiduki omanik - Q Takso Veod OÜ, ja see kehtib, \"TSK tingimused\" lepingulisas sätestatud tingimuste täitmisel."));
    chapter2.addCell(getSubChapterNumber("2.5"));
    chapter2.addCell(
        getSubChapterText(
            "Käesoleva lepingu allkirjastamisel kinnitab Rentnik, et on ta tutvunud \"TSK tingimused\" lepingulisas esitatud tingimustega ja on nendega nõus.  "));
    chapter2.addCell(getSubChapterNumber("2.6"));
    chapter2.addCell(
        getSubChapterText(
            "Rentnik kinnitab, et on ta teadlik sellest, et \"TSK tingimused\" lepingulisa sätestatud tingimuste rikkumisel, kaotab TSK oma kehtivuse. " +
                    "Rentniku kohustus hüvitada rendiperioodi ajal rendiautole tekitatud kahju ja selle ulatus arvutatakse siis vastavalt käesolevas lepingus " +
                    "ja lepingulisa \"Tüüptingimused\" tingimustes sätestatule. TSK kaotab kehtivuse kõigi rikkumiste puhul, sealhulgas kõik rikkumised," +
                    " mis on loetletud TSK Tingimuste vastavates punktides ning Koostöölepingu IV peatükis. TSK kehtivuse lõppemine ei eelda eraldi teadet Rentnikule; " +
                    "rikkumise tuvastamine on piisav alus kehtivuse lõppemiseks."));
    chapter2.addCell(getSubChapterNumber("2.7"));
    chapter2.addCell(
        getSubChapterText(
            "Allkirjastades käesoleva koostöölepingu, võtab Rentnik endale kohustuse tasuda TSK makseid vastavalt käesolevas lepingus ja " +
                    "selle \"TSK tingimused\" lepingulisas sätestatud tingimustele iganädalaselt ja täies mahus."));

    pdfDocument.add(chapter2);

    final var chapter3 = getChapterTable();
    chapter3.addCell(getChapterNumber("III"));
    chapter3.addCell(getChapterSummary("Rendilepingu tingimused"));
    chapter3.addCell(getSubChapterNumber("3.1"));
    chapter3.addCell(
        getSubChapterText(
            " Rendiauto (vaba auto Rendileandja autopargist) edastatakse Rendileandja poolt Rentnikule vastavalt üleandmise-vastuvõtmise aktile, mis on käesoleva lepingu lahutamatuks osaks."));
    chapter3.addCell(getSubChapterNumber("3.2"));
    chapter3.addCell(
        getSubChapterText(
            "Iga rendiauto täispika rendinädala maksumus lepitakse eraldi läbi ning sõltub autoomadustest ja kooskõlastatud renditingimustest." +
                    " Pooled fikseerivad rendinädala maksumuse eraldi üleandmise-vastuvõtmise aktis, mis on käesoleva lepingu lisa ja/või lisad."));
    chapter3.addCell(getSubChapterNumber("3.3"));
    chapter3.addCell(
        getSubChapterText(
            "Auto renditeenuse tagatisraha on 500 eurot, mida Rendileandjal on õigus nõuda osade kaupa või ühe summaga."
                + "Rendileandjal on õigus tasaarveldada rendilepingu lõpetamisel Rentniku täitmata kohustused tagatisrahaga."
                + "Rendileandjal on õigus tagatisrahast kinni pidada kõik võlgnevused ning sõidukile tekitatud kahjud vastavalt " +
                    "üleandmise-vastuvõtmise aktile ja/või kahjuaktile ning Tüüptingimuste VI–VIII peatükkidele."));
    chapter3.addCell(getSubChapterNumber("3.4"));
    chapter3.addCell(
        getSubChapterText(
            "Rentnik kohustub tasuma Rendileandjale ettemaksu iga täispika kalendri rendinädala eest, vastavalt summale," +
                    " mis on määratud üleandmise-vastuvõtmise aktile, kas ülekandega Rendileandja pangakontole (või muule Rendileandja esindaja poolt määratud kontole)" +
                    " asjakohase selgitusega „autorent + auto number“, või sularahas Rendileandja kontoris, mis asub aadressil Lasnamäe 30a, Tallinn." +
                    " Panga- või sularahaülekanne peab olema tehtud hiljemalt jooksva nädala teisipäevaks kella 16:00’ni."));
    chapter3.addCell(getSubChapterNumber("3.5"));
    chapter3.addCell(
        getSubChapterText(
            "Kui Rentnikul on eelnevast perioodist võlgnevus, rakendatakse järgmise rendinädala eest makstavale rendihinnale täiendav makse 25% ulatuses," +
                    " kuid mitte rohkem kui Rentniku hetke koguvõlgnevus. Täiendav makse arvestatakse üksnes rendiauto üleandmise-vastuvõtmise aktis märgitud baasilisest " +
                    "rendihinnast (enne boonuste ja allahindluste rakendamist) vastavalt Tüüptingimuste punktile 12.18."));
    chapter3.addCell(getSubChapterNumber("3.6"));
    chapter3.addCell(
        getSubChapterText(
            "Rentnik annab käesoleva lepingu allkirjastamisega oma kirjaliku nõusoleku, et Rendileandjal on võlgnevuse olemasolul õigus esitada taksotellimuste " +
                    "platvormidele taotlus Rentniku väljamaksmata teenitud vahendite kasutamiseks Rentniku rendi- ja võlakohustuste katteks vastavalt Tüüptingimuste punktile 12.16."));
    chapter3.addCell(getSubChapterNumber("3.7"));
    chapter3.addCell(
        getSubChapterText(
            "Rentnik annab käesoleva lepingu allkirjastamisega oma kirjaliku nõusoleku, et Rendileandjal on võlgnevuse olemasolul õigus taotleda taksotellimuste platvormidelt Rentniku " +
                    "väljamaksete ajutist piirangut viisil, et väljamaksed toimuvad kord nädalas, vastavalt Tüüptingimuste punktile 12.17."));
    chapter3.addCell(getSubChapterNumber("3.8"));
    chapter3.addCell(
        getSubChapterText(
            "Kõik Rentniku poolt Rendileandjale tehtud maksed arvestatakse rangelt vastavalt Tüüptingimuste punktis 12.14 sätestatud maksete arvestamise kohustuslikule ja lõplikule järjekorrale." +
                    " Rendileandjal on õigus teha kõik tasaarvestused üksnes nimetatud järjekorra alusel, sõltumata Rentniku maksekorralduses märgitud infost, soovist või märkustest."));

    pdfDocument.add(chapter3);


    final var chapter4 = getChapterTable();
    chapter4.addCell(getChapterNumber("IV"));
    chapter4.addCell(getChapterSummary("Eritingimused"));
    chapter4.addCell(getSubChapterNumber("4.1"));
    chapter4.addCell(
        getSubChapterText(
            "Käesoleva lepingu täitmisega seotud sõiduki kasutamise, hooldamise, tehniliste ja liiklusnõuete, tagastamise ja ülevaatuse, rikkumiste käsitlemise, " +
                    "teavitamiskohustuste, võlalimiidi arvestamise, sõiduki funktsionaalsuse peatamise, sõiduki eemaldamise, kahju hüvitamise ning muude renditeenuse " +
                    "osutamisega seotud kohustuste üksikasjalik kord tuleneb Tüüptingimustest ja nende peatükist XIV."));
    chapter4.addCell(getSubChapterNumber("4.2"));
    chapter4.addCell(
        getSubChapterText(
            "Rentnik kinnitab, et kõik käesoleva lepingu, Tüüptingimuste ja lisade rikkumised loetakse rikkumisteks Tüüptingimuste mõttes, ning kõik trahvid," +
                    " kuluhüvitised, lisatasud ja sanktsioonid kohaldatakse vastavalt Tüüptingimustele ja kehtivale hinnakirjale."));
    chapter4.addCell(getSubChapterNumber("4.3"));
    chapter4.addCell(
        getSubChapterText(
            "Rentnik vastutab sõidukile tekitatud kahju eest ulatuses ja korras, mis on sätestatud Tüüptingimustes ja nendega seotud dokumentides. "));
    chapter4.addCell(getSubChapterNumber("4.4"));
    chapter4.addCell(
        getSubChapterText(
            "Tasumata rahaliste kohustuste eest rakendub viivis 0,1% päevas tasumata summalt, arvestamise ja tasaarvestamise kord tuleneb Tüüptingimustest."));
    chapter4.addCell(getSubChapterNumber("4.5"));
    chapter4.addCell(
        getSubChapterText(
            "Olulise rikkumise korral on Rendileandjal õigus peatada sõiduki funktsionaalsus, piirata sõiduki kasutamist või eemaldada sõiduk kasutusest," +
                    " lähtudes Tüüptingimustest. Rikkumine võib olla käsitatav Rentniku ülesütlemisena Tüüptingimustes sätestatud korras, " +
                    "ning Rentnik kohustub tasuma kõik etteteatamistähtaja ja miinimumperioodiga seotud summad vastavalt käesoleva lepingu punktidele 1.13–1.14. "));
    chapter4.addCell(getSubChapterNumber("4.6"));
    chapter4.addCell(
        getSubChapterText(
            "Rentnik kinnitab, et sõiduki tagastamise, ülevaatuse ja hooldusele toimetamise nõuded ning nende tähtaegade rikkumise tagajärjed tulenevad Tüüptingimustest. "));

    chapter4.addCell(getSubChapterNumber("4.7"));
    chapter4.addCell(
        getSubChapterText(
            "Rentnik nõustub, et sõiduk võib olla varustatud GPS- ja muude elektrooniliste jälgimisvahenditega, mille kasutamise tingimused tulenevad Tüüptingimustest."));
    chapter4.addCell(getSubChapterNumber("4.8"));
    chapter4.addCell(
        getSubChapterText(
            "Isikuandmete töötlemise kord tuleneb Tüüptingimustest."));

    pdfDocument.add(chapter4);

    final var chapter5 = getChapterTable();
    chapter5.addCell(getChapterNumber("V"));
    chapter5.addCell(getChapterSummary("Lõppsätted"));
    chapter5.addCell(getSubChapterNumber("5.1"));
    chapter5.addCell(
        getSubChapterText(
            "Rendilepingust tulenevad vaidlused, milles Rentnik ja Rendileandja ei jõua kokkuleppele, lahendatakse Harju Maakohtus vastavalt seadusele. " +
                    "Vaidluse läbivaatamisel kohtus rakendatakse käesoleva lepingu tingimused."));
    chapter5.addCell(getSubChapterNumber("5.2"));
    chapter5.addCell(
        getSubChapterText(
            "Pooled lepivad kokku, et Lepingu tingimusteks ei loeta Poolte varasemaid tahteavaldusi, tegusid ega kokkuleppeid, mis ei ole Lepingus ega üldtingimustes otseselt sätestatud."));

    chapter5.addCell(getSubChapterNumber("5.3"));
    chapter5.addCell(
        getSubChapterText(
            "Kõik käesoleva lepingu tingimustes tehtud muudatused loetakse kehtivaks ainult siis, kui need on tehtud kirjalikult kehtiva lepingu lisana koos nende tingimustega " +
                    "nõustumise kinnitusega mõlema poole allkirjade kujul käesoleval dokumendil. Kõik muud arutelud ja kokkulepped loetakse tühiseks ega oma õiguslikku tähendust."));

    chapter5.addCell(getSubChapterNumber("5.4"));
    chapter5.addCell(
        getSubChapterText(
            "Rendileandja ja Rentniku vahel uue koostöölepingu sõlmimine muudab kõik varasemad nende vahel sõlmitud koostöölepinguid tühiseks välja arvatud varasemad võlakohustused, " +
                    "mis jäävad kehtima vastavalt punktile 5.5. käesoleva lepingu sõlmimise ning allkirjastamise kuupäevast."));

    chapter5.addCell(getSubChapterNumber("5.5"));
    chapter5.addCell(
        getSubChapterText(
            "Uue lepingu sõlmimine ei lõpeta ega vähenda varasemate lepingute alusel tekkinud rahalisi kohustusi. Kõik varasemad võlad, " +
                    "leppetrahvid, kahjuhüvitised, viivised ja muud tasud jäävad kehtima kuni nende täieliku tasumiseni."));

    chapter5.addCell(getSubChapterNumber("5.6"));
    chapter5.addCell(
        getSubChapterText(
            "Viited käesoleva lepingu tingimuste tõlgitud tekstidele on esitatud allpool. Tõlked mis tahes keelde võivad olla ebatäpsed ja on mõeldud üksnes tutvumiseks."));

    chapter5.addCell(getSubChapterNumber("5.6.1"));
    chapter5.addCell(
        getSubChapterText(
            "Käesoleva lepingu venekeelne (по-русски) tekst on saadaval järgmisel lingil:" ));        //    Ссылка на страничку на сайте с русским переводом.\n"));

    chapter5.addCell(getSubChapterNumber("5.6.2"));
    chapter5.addCell(
            getSubChapterText(
                    "Käesoleva lepingu inglisekeelne (in English) tekst on saadaval järgmisel lingil:"));     //    Ссылка на страничку на сайте с английским переводом.\n"));

    pdfDocument.add(chapter5);

    final var chapter6 = getChapterTable();
    chapter6.addCell(getChapterNumber("VI"));
    chapter6.addCell(getChapterSummary("Allkirjastamine"));
    chapter6.addCell(getSubChapterNumber("6.1"));
    chapter6.addCell(
            getSubChapterText(
                    "Leping allkirjastatakse digitaalselt mõlema poole poolt ning loetakse sõlmituks alates esimesest allkirjast."));

    pdfDocument.add(chapter6);







    //////// Start New Driver


    ////////////  End New driver

    final var signature = getOneColumnTable();
    final var signaturecell1 =
        new Cell(new Paragraph("RENDILEANDJA:  ", new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell1.setBorder(NO_BORDER);
    signaturecell1.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell1);

    final var signaturecell2 =
        new Cell(new Paragraph(model.getQFirmName(), new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell2.setBorder(NO_BORDER);
    signaturecell2.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell2);

    final var signaturecell3 =
        new Cell(new Paragraph("Rendileandja esindaja  ", new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell3.setBorder(NO_BORDER);
    signaturecell3.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell3);

    final var signaturecell4 =
        new Cell(
            new Paragraph(
                "__________________________________________________________________________________________  ",
                new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell4.setBorder(NO_BORDER);
    signaturecell4.setHorizontalAlignment(JUSTIFIED);
    signature.addCell(signaturecell4);
    signature.addCell(
        getSubChapterText(
            "Alloleva allkirjaga tõendan, et olen Koostöölepingu täielikult läbi lugenud, selle sisust ja mõttest aru saanud ning nõustun nende tingimustega. "));

    final var signaturecell5 =
        new Cell(new Paragraph("RENTNIK:  ", new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell5.setBorder(NO_BORDER);
    signaturecell5.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell5);

    final var signaturecell6 =
        new Cell(new Paragraph(model.getRenter(), new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell6.setBorder(NO_BORDER);
    signaturecell6.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell6);

    final var signaturecell7 =
        new Cell(new Paragraph("Rentnik esindaja  ", new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell7.setBorder(NO_BORDER);
    signaturecell7.setHorizontalAlignment(LEFT);
    signature.addCell(signaturecell7);

    final var signaturecell8 =
        new Cell(
            new Paragraph(
                "________________________________________________________________________      _______________  ",
                new Font(TIMES_ROMAN, 9, BOLD)));
    signaturecell8.setBorder(NO_BORDER);
    signaturecell8.setHorizontalAlignment(JUSTIFIED);
    signature.addCell(signaturecell8);

    final var signaturecell9 =
        new Cell(
            new Paragraph(
                "Nimi, allkiri                                                        Kuupäev             ",
                new Font(TIMES_ROMAN, 9, NORMAL)));
    signaturecell9.setColspan(2);
    signaturecell9.setBorder(NO_BORDER);
    signaturecell9.setHorizontalAlignment(RIGHT);
    signature.addCell(signaturecell9);

    pdfDocument.add(signature);

    pdfDocument.close();
    writer.close();

    return new ByteArrayInputStream(outputStream.toByteArray());
  }
}
