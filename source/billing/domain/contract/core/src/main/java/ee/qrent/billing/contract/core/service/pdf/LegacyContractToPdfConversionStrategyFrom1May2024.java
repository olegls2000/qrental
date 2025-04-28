package ee.qrent.billing.contract.core.service.pdf;

import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.lang.String.format;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class LegacyContractToPdfConversionStrategyFrom1May2024
        extends AbstractContractToPdfConversionStrategy {

  @Override
  public boolean canApply(final ContractPdfModel contract) {
    final var contractCreatedDate = contract.getCreated();

    return contractCreatedDate.isAfter(NEW_CONTRACTS_START_DATE)
            || contractCreatedDate.isEqual(NEW_CONTRACTS_START_DATE);
  }

  @SneakyThrows
  @Override
  public InputStream getPdfInputStream(final ContractPdfModel model) {
    final var pdfDocument = new Document(A4, 40f, 40f, 50f, 50f);
    final var outputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(pdfDocument, outputStream);
    pdfDocument.open();
    pdfDocument.add(getHeaderTable(model));
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(getRenterTable(model));
    pdfDocument.add(getTenantTable(model));
    addLhvChapterIfNecessary(model, pdfDocument);

    final var chapter1 = getChapterTable();
    chapter1.addCell(getChapterNumber("I"));
    chapter1.addCell(getChapterSummary("Üldsätted"));
    chapter1.addCell(getSubChapterNumber("1.1"));
    chapter1.addCell(
            getSubChapterText(
                    "Lepingu põhitingimused ja kasutatavate mõistete selgitused on toodud lepingu üldtingimustes ja nende lisades, mis on käesoleva lepingu lahutamatuks osaks. "
                            + "Rentnik kinnitab, et on tutvunud käesoleva lepingu tingimustega, “Tüüptingimused” lepingulisa tingimustega ja “TSK Tingimused” lepingulisa tingimustega, mõistab neid ja on nendega nõus."));
    chapter1.addCell(getSubChapterNumber("1.2"));
    //TODO  www.111.222.333
    chapter1.addCell(
            getSubChapterText(
                    "Käesoleva lepingulisa “Tüüptingimused” nr. 25042025 lepingulisa on koostatud ja allkirjastatud 25.04.2025 (Signature Timestamp UTC - 25.04.2025 08:41:26 +00:00) ning on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tüüptingimused/tt_dig_allkiri_est"));

    chapter1.addCell(getSubChapterNumber("1.2.1"));
    // TODO  www.111.222.333
    chapter1.addCell(
            getSubChapterText(
                    "Käesoleva lepingulisa eestikeelne PDF on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tüüptingimused/tt_pdf_est"));

    chapter1.addCell(getSubChapterNumber("1.2.2"));
    // TODO  www.111.222.333
    chapter1.addCell(
            getSubChapterText(
                    "Käesoleva lepingulisa venekeelne (по-русски) PDF on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tüüptingimused/tt_pdf_rus"
                            + "(tõlge võib olla ebatäpne ja on üksnes informatiivse tähendusega)"));

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
                    "Rendileandjal on õigus igal ajal muuta käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimused, teavitades sellest Rentnikku kaks täispika kalendrinädalat muudatuste jõustumise ette. "));
    chapter1.addCell(getSubChapterNumber("1.7"));
    chapter1.addCell(
            getSubChapterText(
                    "Kõik käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimuste muudatused jõustuvad kaks täispika kalendrinädalat pärast nendest Rentniku teavitamist, välja arvatud juhul, kui muudatuses on sätestatud teisiti."));

    chapter1.addCell(getSubChapterNumber("1.8"));
    chapter1.addCell(
            getSubChapterText(
                    "Rentnik on kohustatud tutvuma käesoleva lepingu ja “Tüüptingimused” lepingulisa muudatustega ning kinnitama nendega nõustumist kahe täispika kalendrinädala jooksul pärast teavitamise saamist. Kui Rentnik ei nõustu muudatustega, on tal õigus lepingu üles öelda käesoleva lepingu punktis 1.14 sätestatud tingimustel."));

    chapter1.addCell(getSubChapterNumber("1.9"));
    chapter1.addCell(
            getSubChapterText(
                    " Käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimuste ebaõige täitmise või täitmata jätmise korral kannavad pooled vastutust käesoleva lepingu, “Tüüptingimused” lepingulisa ja kehtivate õigusnormide järgi."));

    chapter1.addCell(getSubChapterNumber("1.10"));
    chapter1.addCell(
            getSubChapterText(
                    " Kui ei ole Rentnik käesoleva lepingu ja “Tüüptingimused” lepingulisa tingimuste muudatustega tutvunud ega pole oma nõusolekut muudatustega kahe täispika kalendrinädalate jooksul alates teavitamisest kinnitanud, loetakse, et on ta need tingimused vastu võtnud."));

    chapter1.addCell(getSubChapterNumber("1.11"));
    chapter1.addCell(
            getSubChapterText(
                    " Rendileandja ja Rentnik sõlmivad käesoleva lepingu oma majandustegevuse raames, mille alusel on Rentnikul õigus võtta renti igat hõivatumata autot Rendileandja autopargist oma tulu teenimise eesmärgil, s.o taksoteenuste osutamiseks. Vastavalt käesolevale rendilepingule kohustub Rendileandja andma Rentnikule kasutada käesolevas lepingus, selle “Tüüptingimused” lepingulisas ning üleandmise-vastuvõtmise aktis määratud rendieseme (hõivatumata auto Rendileandja autopargist, kui see on olemas). Selle eest on Rentnik kohustatud tasuma Rendileandjale renditasu (rent) kogu rendiperioodi eest."));

    chapter1.addCell(getSubChapterNumber("1.12"));
    chapter1.addCell(
            getSubChapterText(
                    "Oma allkirjaga kinnitab Rentnik, et on ta teadlik ja nõustub, et iga renditud auto täispika kalendrinädala rendimaksumus on määratud vastavas üleandmise-vastuvõtmise aktis ning et tuleb nimetatud tasu tasuda õigeaegselt ja täies ulatuses (kehtiva rendinädala Teisipäeva kella 16:00’ni)."));

    chapter1.addCell(getSubChapterNumber("1.12.1"));
    chapter1.addCell(
            getSubChapterText(
                    "Rentnik kinnitab oma allkirjaga tema nõusolekut osaleda Rendileandja korraldatavates boonuskampaaniates. Nende boonuskampaaniate toimumise ajad ja tingimused ei ole eelnevalt kindlaks määratud "
                            + "ning Rentnik nõustub, et Rendileandja võib nende toimumisel ja Rentniku vastuväidete puudumisel teha korrigeerivaid kandeid Rentniku saldole. Rentnik tunnistab, et selliste boonuskannete tegemine ei ole Rendileandja kohustus ning Rendileandja võib neid igal ajal omal äranägemisel alustada, jätkata või lõpetada. Samal ajal, käesoleva kalendrinädala mistahes boonuskampaania aktiveerimise tingimusteks on Rentnikupoolne õigeaegne ja täielik eelmise nädala renditasu tasumine ning käesolevas lepingus määratud võlakohustuste osa hüvitamine (hüvitamise kord on kirjeldatud punktides 3.4 ja 3.5)."));
    // TODO 12 4
    chapter1.addCell(getSubChapterNumber("1.13"));
    chapter1.addCell(
            getSubChapterText(
                    "Käesolev leping on tähtajatu. Samal ajal, Rendileandja poolt käesoleva koostöölepingu alusel osutatava renditeenuse miinimumperiood on kaksteist (neli) järjestikut täispika kalendrinädalat, mille eest peab renditeenuse maksumus olema tasutud. Renditeenuse osutamise miinimumperioodi arvestus algab kuupäevast, mis on määratud käesoleva lepingu alusel koostatud esimese rendiauto üleandmise-vastuvõtmise aktis, juhul kui see akt allkirjastatakse lepingu sõlmimise päeval või hiljem. Kui Rentnikul on lepingu allkirjastamise hetkel Rendileandja rendiauto juba kasutuses, algab miinimumperioodi arvestus käesoleva lepingu allkirjastamise kuupäevast."));
    // TODO 12 4
    chapter1.addCell(getSubChapterNumber("1.14"));
    chapter1.addCell(
            getSubChapterText(
                    "Rentnikul on õigus lepingu igal ajal ja mistahes põhjusel lõpetada. Lepingu lõpetamiseks kohustub Rentnik teavitama Rendileandjat kirjalikult, kasutades rendilepingus määratud e-posti aadressi, vähemalt neli (kaks) täispika kalendrinädalat enne rendiauto tagastamist. Auto tagastamise tingimused on kirjeldatud “Tüüptingimused” lepingulisa punktides 4.3 ja 4.4. Pärast seda, kui on Rentnik Rendileandja käesoleva rendilepingu lõpetamise soovist teavitanud, tekib Rentnikul õigus lõpetada lepingualuse autorenditeenuse kasutamise nelja (kahe) täispika kalendrinädala möödumisel. Selle ajani peab Rentnik tasuma renditasu tema lepingualuse rendiauto eest täies ulatuses, käesolevas punktis nimetatud rendiperioodi jooksul (neli (kaks) täispika kalendrinädalat) ja vastavalt punktides 3.4 ja 3.5 sätestatud tingimustele. Rentnikul on õigus kasutada rendiautot kogu nimetatud perioodi vältel vastavalt lepingu punktides 4.4, 4.4.1 ja 4.4.2 kirjeldatud tingimustele. Kui Rentnik tagastab rendiauto enne tähtaega, kohustub ta tasuma kogu kasutamata rendiperioodi eest vastavalt selle auto üleandmise-vastuvõtmise aktis määratud rendimaksumusele."));
    pdfDocument.add(chapter1);

    final var chapter2 = getChapterTable();
    chapter2.addCell(getChapterNumber("II"));
    chapter2.addCell(getChapterSummary("Täiendava sisekindlustus (edaspidi TSK)"));
    chapter2.addCell(getSubChapterNumber("2.1"));
    chapter2.addCell(
            // TODO  www.111.222.333
            getSubChapterText(
                    " Käesoleva lepingulisa “TSK Tingimused” nr.250425 lepingulisa on koostatud ja allkirjastatud 25.04.2025 (Signature Timestamp UTC - 25.04.2025 08:38:34 +00:00) ning on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tsk/tsk_dig_allkiri_est"));

    chapter2.addCell(getSubChapterNumber("2.1.1"));
    chapter2.addCell(
            // TODO  www.111.222.333
            getSubChapterText(
                    "Käesoleva lepingulisa eestikeelne PDF on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tsk/tsk_pdf_est"));

    chapter2.addCell(getSubChapterNumber("2.1.2"));
    chapter2.addCell(
            // TODO  www.111.222.333
            getSubChapterText(
                    "Käesoleva lepingulisa venekeelne (по-русски) PDF on saadaval järgmisel lingil:"
                            + "www.qrent.ee/tsk/tsk_pdf_rus"
                            + "(tõlge võib olla ebatäpne ja on üksnes informatiivse tähendusega)"));

    chapter2.addCell(getSubChapterNumber("2.2"));
    chapter2.addCell(
            getSubChapterText(
                    "Vastavalt “TSK Tingimused” lepingulisa tingimustele osutab Q Takso Veod OÜ täiendava sisekindlustuse teenust alates 02.06.2025 käesoleva lepingu alusel renditavatele autodele. Selle teenuse tingimused on lühidalt kirjeldatud käesoleva lepingu paragrahvis ja üksikasjalikum ülalmainitud lepingulisas."));
    chapter2.addCell(getSubChapterNumber("2.3"));
    chapter2.addCell(
            getSubChapterText(
                    "Maksimaalne frantsiisi summa rendiautole kahju tekitamisel rendiperioodi ajal on 600 eurot. See tingimus kehtib TSK alusel, mille tingimused on kirjeldatud \"TSK tingimused\" lepingulisas."));
    chapter2.addCell(getSubChapterNumber("2.4"));
    chapter2.addCell(
            getSubChapterText(
                    "TSK pakub sõiduki omanik - Q Takso Veod OÜ, ja see kehtib, \"TSK tingimused\" lepingulisas sätestatud tingimuste täitmisel."));
    chapter2.addCell(getSubChapterNumber("2.5"));
    chapter2.addCell(
            getSubChapterText(
                    "Käesoleva lepingu allkirjastamisel kinnitab Rentnik, et on ta tutvunud \"TSK tingimused\" lepingulisas esitatud tingimustega ja on nendega nõus. "));
    chapter2.addCell(getSubChapterNumber("2.6"));
    chapter2.addCell(
            getSubChapterText(
                    "Samuti kinnitab Rentnik, et on ta teadlik sellest, et \"TSK tingimused\" lepingulisa sätestatud tingimuste rikkumisel, kaotab TSK oma kehtivuse. Rentniku kohustus hüvitada rendiperioodi ajal rendiautole " +
                            "tekitatud kahju ja selle ulatus arvutatakse siis vastavalt käesolevas lepingus ja lepingulisa \"Tüüptingimused\" tingimustes sätestatule."));
    chapter2.addCell(getSubChapterNumber("2.7"));
    chapter2.addCell(
            getSubChapterText(
                    "Allkirjastades käesoleva koostöölepingu, võtab Rentnik endale kohustuse tasuda TSK makseid vastavalt käesolevas lepingus ja selle \"TSK tingimused\" lepingulisas sätestatud tingimustele iganädalaselt ja täies mahus."));

    pdfDocument.add(chapter2);

    final var chapter3 = getChapterTable();
    chapter3.addCell(getChapterNumber("III"));
    chapter3.addCell(getChapterSummary("Rendilepingu tingimused"));
    chapter3.addCell(getSubChapterNumber("3.1"));
    chapter3.addCell(
            getSubChapterText(
                    "Rendiauto (hõivatumata auto Rendileandja autopargist) edastatakse Rendileandja poolt Rentnikule vastavalt üleandmise-vastuvõtmise aktile, mis on käesoleva lepingu lahutamatuks osaks."));
    chapter3.addCell(getSubChapterNumber("3.2"));
    chapter3.addCell(
            getSubChapterText(
                    "Iga rendiauto täispika rendinädala maksumus lepitakse eraldi läbi ning sõltub autoomadustest ja kooskõlastatud renditingimustest. Pooled fikseerivad rendinädala maksumuse eraldi üleandmise-vastuvõtmise aktis, mis on käesoleva lepingu lisa ja/või lisad."));
    chapter3.addCell(getSubChapterNumber("3.3"));
    chapter3.addCell(
            getSubChapterText(
                    "3 Auto renditeenuse tagatisraha on 500 eurot, mida Rendileandjal on õigus nõuda osade kaupa või ühe summaga. Rendileandjal on õigus tasaarveldada rendilepingu lõpetamisel Rentniku täitmata kohustused tagatisrahaga."));
    chapter3.addCell(getSubChapterNumber("3.4"));
    chapter3.addCell(
            getSubChapterText(
                    "Rentnik kohustub tasuma Rendileandjale ettemaksu iga täispika kalendri rendinädala eest, vastavalt summale, mis on määratud üleandmise-vastuvõtmise aktile, kas sularahas Rendileandja kontoris, mis asub aadressil Lasnamäe 30a," +
                            " Tallinn, või ülekandega Rendileandja pangakontole (või muule Rendileandja esindaja poolt määratud kontole) asjakohase selgitusega „autorent + auto number“. Panga- või sularahaülekanne peab olema tehtud hiljemalt jooksva nädala teisipäevaks kella 16:00’ni."));
    chapter3.addCell(getSubChapterNumber("3.5"));
    chapter3.addCell(
            getSubChapterText(
                    "Juhul, kui on Rentnikul eelmise perioodi võlgnevusi, kohustub ta tasuma kehtiva nädalase rendihinna lisaks 25% käesoleva võlgnevuse osaliseks katteks."));
    chapter3.addCell(getSubChapterNumber("3.6"));
    chapter3.addCell(
            getSubChapterText(
                    "Käesoleva lepingu allkirjastamisega nõustub Rentnik, et Rendileandjal on õigus nõuda Rentniku eelnevalt teenitud, mittekasutatud ja mitteäravõetud vahendeid Rentniku kontolt taksotellimuste vahendamise rakendustes, nagu näiteks Bolt, Forus, Uber, kuid mitte ainult need, et katta Rentniku Rendileandja ees olevaid autorendi- ja võlakohustusi. Rendileandja kohustub tagama, et seda õigust ei kasutata ebaseaduslikel ega muudel eesmärkidel, mis ei vasta käesolevas paragrahvis kirjeldatud eesmärkidele, ning vajadusel esitab Rentnikule või rakenduse-põhisele teenusepakkujale arvestuse, mille järgi on nõutud vahendid kasutatud. Võimalikuks nõudmiseks kokkulepitud vahendite summa on määratud punktides 3.4 ja 3.5, eelnevalt kokkulepitud Rentniku ja Rendileandja vahel ning Rentnik kinnitab, et on sellega nõus. Käesolev õigus kehtib vaid käesoleva lepingu kehtivuse ajal ja Rentniku poolt Rentileandja auto rentimisel, kui pooled ei ole kokku leppinud teisiti."));
    chapter3.addCell(getSubChapterNumber("3.7"));
    chapter3.addCell(
            getSubChapterText(
                    "Käesoleva lepingu allkirjastamisega nõustub Rentnik järgmiste tingimustega:"));
    chapter3.addCell(getSubChapterNumber("3.7.1"));
    chapter3.addCell(
            getSubChapterText(
                    "Rendileandjal on õigus taotleda raha väljavõtmise piiramist Rentniku bilanssilt taksoteenuste rakendustes (nt Bolt, Forus, Uber jne) ühe korraga nädalas – ainult nädala esimesel tööpäeval (tavaliselt esmaspäeval), välja arvatud juhul, kui ametlikud puhkepäevad määravad teisiti. Piirangut saab taotleda ainult Rendileandja algatusel, ning Rentnik ei saa ise taotleda selliseid muudatusi oma konto toimimises käesoleva lepingu kehtivuse ajal ja tema poolt Rentileandja auto rentimisel. Rentnik kinnitab, et ta aktsepteerib käesolevas paragrahvis kirjeldatud tingimused ja on nendega tingimusteta nõus;"));
    chapter3.addCell(getSubChapterNumber("3.7.2"));
    chapter3.addCell(
            getSubChapterText(
                    "Seda piirangut saab rakendada ühepoolselt ilma Rentnikule täiendavalt teavitamata;"));
    chapter3.addCell(getSubChapterNumber("3.7.3"));
    chapter3.addCell(
            getSubChapterText(
                    "Rendileandja kohustub mitte kuritarvitama seda õigust ja kasutama seda ainult Rentniku lepinguliste finantskohustuste täitmise tagamiseks;"));
    chapter3.addCell(getSubChapterNumber("3.7.4"));
    chapter3.addCell(
            getSubChapterText(
                    "Rentnikul ei ole õigust nõuda hüvitist võimalike kaudsete kahjude eest (nt kaotatud kasumi eest), mis on seotud selle piiranguga."));
    pdfDocument.add(chapter3);

    final var chapter4 = getChapterTable();
    chapter4.addCell(getChapterNumber("IV"));
    chapter4.addCell(getChapterSummary("Eritingimused"));
    chapter4.addCell(getSubChapterNumber("4.1"));
    chapter4.addCell(
            getSubChapterText(
                    "Rendiauto kütus ei sisaldu rendihinnas. Korralist tehnilist hooldust teostab Rendileandja."));
    chapter4.addCell(getSubChapterNumber("4.2"));
    chapter4.addCell(
            getSubChapterText(
                    "Rentnik vastutab kahju eest vastavalt “Tüüptingimused” lepingulisa tingimustele ptk VI, VII ja VIII sätestatud järgi."));
    chapter4.addCell(getSubChapterNumber("4.3"));
    chapter4.addCell(
            getSubChapterText(
                    "Viivis tasumata summa eest on 0,1% kalendripäevas kogu tasumata summalt.Viivis arvestatakse iga tasumisele järgneva kalendripäeva eest summalt, mis on tasumata, määraga 0,1% päevas."));
    chapter4.addCell(getSubChapterNumber("4.4"));
    chapter4.addCell(
            getSubChapterText(
                    "Rentniku kohustuste võlalimiit Rendileandja ees on 240 eurot, mille ületamisel on Rentnikul tema võlgnevuse kõrvaldamiseks 7 päeva pärast esimest Esmaspäeva, millal on käesolev võlg tekkinud. Juhul, kui Rentniku võlgnevus ei ole 7 päeva möödumisel kõrvaldatud, tekib Rendileandjal õigus käesoleva lepingualuse renditeenuse osutamine peatada ja lugeda lepingu Rendija poolt oluliselt rikutuks."));
    chapter4.addCell(getSubChapterNumber("4.4.1"));
    chapter4.addCell(
            getSubChapterText(
                    " Rentnikupoolsel käesoleva lepingu punktis 4.4 ja “Tüüptingimused” lepingulisa punktis III sätestatud tingimuste olulisel rikkumisel jätab Rendileandja endale õigust oma äranägemisel otsustada, kas peatada rendiauto funktsionaalsuse Rentniku rahalise olukorra paranemiseni või lõpetada Rentnikule osutatava renditeenuse ja kõrvaldada rendiauto tema kasutusest."));
    chapter4.addCell(getSubChapterNumber("4.4.2.1"));
    chapter4.addCell(
            getSubChapterText(
                    " Rentnik on kohustatud toimetama rendiauto ülevaatuseks 3 tunni jooksul peale vastava nõude saamist Rentniku kontorisse aadressil Lasnamäe 30a, Tallinn, kui selline nõue on esitatud Rentnikul hiljemalt kella 15:00’ni tööpäeval või hiljemalt kella 11:00’ni laupäeval. Kui nõue esitati hiljem kui eelnevas lauses näidatud ajaks, on Rentnik kohustatud tooma sõiduki kontrolliks järgmisel päeval v.a. Põhapäeva. Kui Rentnik ei täida seda tingimust õigeks ajaks vääramatu jõu puudumisel, on Rendileandjal õigus nõuda Rentnikult trahvi summas 30 eurot selle ja iga sellise kordava juhtumi eest."));
    chapter4.addCell(getSubChapterNumber("4.4.2.2"));
    chapter4.addCell(
            getSubChapterText(
                    " Rentnik on kohustatud tagastama Sõiduki Rendileandjale samadel tingimustel, mis on sätestatud käesoleva lepingu punktis 4.4.2.1, vastava nõude saamisel juhul, kui Rentnik on oluliselt rikkunud käesoleva lepingu punktis 4.4 sätestatud renditingimused. Kui Rentnik ei täida seda tingimust õigeks ajaks vääramatu jõu puudumisel, on Rendileandjal õigus nõuda Rentnikult trahvi summas 120 eurot iga ööpäeva eest, mis on möödunud alates sellise nõude saamisest Rentniku poolt."));
    chapter4.addCell(getSubChapterNumber("4.4.2.3"));
    chapter4.addCell(
            getSubChapterText(
                    "Rentnik on kohustatud toimetama rendiauto tehnilisele hooldusele SMS-sõnumis ja/või e-kirjas märgitud ajaks, mis on talle saadetud käesolevas lepingus märgitud kontaktandmete alusel. Hooldus toimub aadressil Lasnamäe 30a, Tallinn, Brisko OÜ töökojas. Pooled on kokku leppinud, et eelmainitud teade ei tohi olla saadetud varem kui 18 tundi enne hoolduse toimumise aega. Juhul kui Rentnik ei saa tuua sõidukit hooldusesse määratud ajaks, on ta kohustatud sellest teatama hiljemalt 3 tunni jooksul pärast vastava teate saamist ja mitte hiljem kui teate saamise päeval kell 18:00. Kui Rentnik ei täida seda tingimust õigeks ajaks vääramatu jõu puudumisel, on Rendileandjal õigus nõuda Rentnikult trahvi summas 30 eurot."));
    // TODO 12 4
    chapter4.addCell(getSubChapterNumber("4.4.3"));
    chapter4.addCell(
            getSubChapterText(
                    " Käesoleva lepingu allkirjastamisega kinitab Rentnik, et on ta teadlik ja nõus järgmise tingimusega: Käesolevas lepingus punktis 4.4.1 kirjeldatud tingimuste loomine ja järgneva rendiauto kõrvaldamine Rentniku kasutusest võrdsustatakse Rentniku poolt lepingu lõpetamisest teatamisega, mis on kirjeldatud käesoleva lepingu punktis 1.14. Seetõttu jõustub käesoleva lepingu punktis 1.14 toodud tingimus, mis kohustab Rentnikku tasuma renditasu järgnevate nelja (kahe) täispika kalendrinädala eest pärast eespool kirjeldatud Rentniku poolt lepingu lõpetamisest teatamist."));
    chapter4.addCell(getSubChapterNumber("4.4.4"));
    chapter4.addCell(
            getSubChapterText(
                    " Peale selle, Rentnikupoolse eelnevas punktis 4.4.1 kirjeldatud tingimuste loomise ja sellele järgneva rendiauto kõrvaldamise eest määratakse rahatrahv summas 500 eurot, kui rendiauto kõrvaldamise ajal asub Tallinnas. Kui rendiauto kõrvaldamise ajal asub Tallinnast väljaspool, lisatakse trahvi summale veel 2 eurot iga kilomeetri eest Tallinna piirist auto asukohani. Kui auto kõrvaldamise ajal ei ole sõiduk liiklemiskõlblik, lisatakse ülaltoodud summadele evakuaatori tasu."));
    chapter4.addCell(getSubChapterNumber("4.4.5"));
    chapter4.addCell(
            getSubChapterText(
                    " Rendiauto võib olla tagastatud Rentnikule kasutamiseks, kui kõrvaldamise põhjuseks oli vaid võlgnevus Rendileandja ees, mis hiljem hüvitati. Sellisel juhul ei kompenseerita Rentnikule aega, mille jooksul rendiauto oli Rendileandja valduses, vaid peab see olema tasutud vastavalt käesoleva lepingu punktides 3.4 ja 3.5 kirjeldatud tingimustele. Sel juhul tagastatakse auto Rentnikule “Tüüptingimused” lepingulisa punktis 4.1 määratud väljastamiskohas."));
    chapter4.addCell(getSubChapterNumber("4.5"));
    chapter4.addCell(
            getSubChapterText(
                    " Pooled on leppinud kokku eraldi, et käesolev leping on sõlmitud Rentniku majandustegevuse raames ning sellele ei kohaldata VÕS sätestatud tarbija õigusi."));
    // TODO UUS rentnik 111111111
    chapter4.addCell(getSubChapterNumber("4.6.1"));
    chapter4.addCell(
            getSubChapterText(
                    " Rentniku käesoleva lepingualune seaduslik esindaja või tegelik kasusaaja (Uus Rentnik 12345678910) avaldab ja kinnitab oma allkirjaga tingimusteta, et ta käendab käesolevas lepingus (majandustegevuse raames) tekkivaid kohustusi. Käendaja tagab nimetatud lepingus sätestatud kohustuste täitmise antava käendusega. Pooled kinnitavad, et nad ei käsitle käesoleva lepingu alusel antud käendust tarbijakäendusena võlaõigusseaduse tähenduses. Käendaja vastutab Rendileandja ees täies ulatuses solidaarselt, tagades kõiki Rendileandja nõudeid Rentniku vastu, mis tekivad või võivad tekkida käesoleva lepingu alusel."));
    chapter4.addCell(getSubChapterNumber("4.6.2"));
    chapter4.addCell(
            getSubChapterText(
                    "Pooled on leppinud kokku, et käendaja maksimaalne vastutuse piir mootorsõidukile tekitatud kahjustuste eest on kuni kaksteist tuhat eurot, juhul kui autole tekitatud kahjustused ei kuulu täiendava sisemise kindlustuse (TSK) alla ega ole piiratud maksimaalse frantsiisi summaga, mis on 600 eurot. See vastutuse piirang kehtib ainult siis, kui kõik järgmised tingimused on täidetud: "));
    chapter4.addCell(getSubChapterNumber("4.6.2.1"));
    chapter4.addCell(
            getSubChapterText(
                    "Avarii toimus Eestis – liiklusõnnetus juhtus rendiautoga Eesti piirides."));
    chapter4.addCell(getSubChapterNumber("4.6.2.2"));
    chapter4.addCell(
            getSubChapterText(
                    "Kahjuna käsitletakse ainult kahjustusi, mis on tekkinud otseselt Rentniku rendiautole."));
    chapter4.addCell(getSubChapterNumber("4.6.2.3"));
    chapter4.addCell(
            getSubChapterText(
                    "Õnnetus on dokumentaalselt tõendatud – olemas on tõendid (fotod, videomaterjal, avariiakt, teise poole allkirjastatud vastuvõtukiri jms).     "));
    chapter4.addCell(getSubChapterNumber("4.6.2.4"));
    chapter4.addCell(
            getSubChapterText(
                    " Rendiandja on teavitatud 24 tunni jooksul ja kõik materjalid on saadetud tema e-postile.    "));
    chapter4.addCell(getSubChapterNumber("4.6.3"));
    chapter4.addCell(
            getSubChapterText(
                    "Juhtumid, kus vastutuse piirang EI KEHTI (rendnik vastutab kahju täissumma eest):     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.1"));
    chapter4.addCell(getSubChapterText(" Auto juht oli alkoholi- või uimastimõju all.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.2"));
    chapter4.addCell(
            getSubChapterText(
                    "Kahju on tekitatud tahtlikult või ülima hooletuse tõttu (nt. agressiivne sõidustiil koos sagedase sõiduradade vahetamisega ja möödasõitudega."));
    chapter4.addCell(getSubChapterNumber("4.6.3.3"));
    chapter4.addCell(
            getSubChapterText("Korduv liiklusreeglite rikkumine (nt pidev kiiruseületamine).     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.4"));
    chapter4.addCell(getSubChapterText(" Reeglite rikkumine oli teadlik ja tahtlik.  "));
    chapter4.addCell(getSubChapterNumber("4.6.3.5"));
    chapter4.addCell(
            getSubChapterText(
                    "Sõidukit juhtis isik, kes ei ole Rendilepingus märgitud Rentnik ega omanda lepingu alusel õigust sõidukit juhtida.  "));
    chapter4.addCell(getSubChapterNumber("4.6.3.6"));
    chapter4.addCell(
            getSubChapterText("Autot kasutati lepingutingimustele vastuolulisel eesmärgil. "));
    chapter4.addCell(getSubChapterNumber("4.6.3.7"));
    chapter4.addCell(getSubChapterText(" Rendniku juhiluba oli aegunud või kehtetu.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.8"));
    chapter4.addCell(
            getSubChapterText(
                    "Autot kasutati vigaste pidurite või kulumisele jooksnud rehvidega (talvel – alla 4 mm, suvel – alla 2 mm), kui rendnik ei teavitanud rendiandjat ette ega saanud remondikeeldust.      "));
    chapter4.addCell(getSubChapterNumber("4.6.3.9"));
    chapter4.addCell(
            getSubChapterText(
                    "Õnnetus toimus tehnilise vea tõttu, millest rendnik teadis või pidi teadma ning ei ole sellest Rendileandjale teatanud.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.10"));
    chapter4.addCell(
            getSubChapterText(
                    "Rendnik lahkus õnnetuspaigast, teavitamata politseid või Päästeametit.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.11"));
    chapter4.addCell(
            getSubChapterText(
                    "Õiged dokumendid (avariiakt, vastuvõtukiri jne) ei ole koostatud.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.12"));
    chapter4.addCell(
            getSubChapterText("Rendnik alustas parandustöid ilma rendiandja nõusolekuta.     "));
    chapter4.addCell(getSubChapterNumber("4.6.3.13"));
    chapter4.addCell(
            getSubChapterText(
                    " Kui esineb vähemalt üks nendest (punktid 4.6.3.1-4.6.3.12) rikkumistest, kaotab rendnik õiguse vastutuse piirangule ja on kohustatud hüvitama kogu kahju ulatuses.    "));
    chapter4.addCell(getSubChapterNumber("4.6.4"));
    chapter4.addCell(
            getSubChapterText(
                    "Käendus tagab nii põhi- kui ka kõrvalkohustuste täitmise, sealhulgas:     "));
    chapter4.addCell(getSubChapterNumber("4.6.4.1"));
    chapter4.addCell(getSubChapterText("Intresside, viiviste ja trahvide tasumise,     "));
    chapter4.addCell(getSubChapterNumber("4.6.4.2"));
    chapter4.addCell(getSubChapterText("Kahju hüvitamise,     "));
    chapter4.addCell(getSubChapterNumber("4.6.4.3"));
    chapter4.addCell(
            getSubChapterText("Lepingu ülesütlemise või tagasivõtmisega seotud kulud,     "));
    chapter4.addCell(getSubChapterNumber("4.6.4.4"));
    chapter4.addCell(getSubChapterText("Võla sissenõudmise kulud     "));
    chapter4.addCell(getSubChapterNumber("4.7"));
    chapter4.addCell(
            getSubChapterText(
                    "Käesolevale lepingule ei kohaldata võlaõigusseaduse (VÕS) § 143 sätestatut.     "));
    chapter4.addCell(getSubChapterNumber("4.8"));
    chapter4.addCell(
            getSubChapterText(
                    "Käendus hõlmab mitte ainult põhivõla, vaid kõik lisafinantskohustused, mis tekivad lepingu tingimuste täitmata jätmise tõttu. "
                            + "Samas on pooled kokku leppinud, et VÕS § 143 sätted (mis võivad piirata vastutust) ei kohaldu. Käendusega tagatakse täielik finantstagatis "
                            + "kõigi lepingust tulenevate kohustuste täitmiseks, sealhulgas kaasnevate kulude ja kahjude hüvitamiseks. Lepingu eritingimusena välistatakse"
                            + " VÕS § 143 kohaldamine, mis võimaldab kitsendamatu vastutuse rakendamise.     "));
    chapter4.addCell(getSubChapterNumber("4.9"));
    chapter4.addCell(
            getSubChapterText(
                    "Rentniku kohustused muutuvad sissenõutavaks vastavalt lepingus sätestatud üldtingimustele.     "));
    chapter4.addCell(getSubChapterNumber("4.10"));
    chapter4.addCell(
            getSubChapterText("Selle lepingu allkirjastamisega kinnitab Rentnik, et:     "));
    chapter4.addCell(getSubChapterNumber("4.10.1"));
    chapter4.addCell(
            getSubChapterText(
                    "Teda on teavitatud ja ta nõustub, et kasutatav sõiduk on varustatud GPS-seadmega;     "));
    chapter4.addCell(getSubChapterNumber("4.10.2"));
    chapter4.addCell(
            getSubChapterText("See seade võimaldab vajadusel kindlaks teha sõiduki asukoha;     "));
    chapter4.addCell(getSubChapterNumber("4.10.3"));
    chapter4.addCell(
            getSubChapterText("Seadmest saadud andmeid võib kasutada kohtumenetluses.     "));

    pdfDocument.add(chapter4);

    final var chapter5 = getChapterTable();
    chapter5.addCell(getChapterNumber("V"));
    chapter5.addCell(getChapterSummary("Lõppsätted"));
    chapter5.addCell(getSubChapterNumber("5.1"));
    chapter5.addCell(
            getSubChapterText(
                    "Rendilepingust tulenevad vaidlused, milles Rentnik ja Rendileandja ei jõua kokkuleppele, lahendatakse Harju Maakohtus vastavalt seadusele. Vaidluse läbivaatamisel kohtus rakendatakse käesoleva lepingu tingimused."));
    chapter5.addCell(getSubChapterNumber("5.2"));
    chapter5.addCell(
            getSubChapterText(
                    "Pooled lepivad kokku, et Lepingu tingimusteks ei loeta Poolte varasemaid tahteavaldusi, tegusid ega kokkuleppeid, mis ei ole Lepingus ega üldtingimustes otseselt sätestatud."));

    chapter5.addCell(getSubChapterNumber("5.3"));
    chapter5.addCell(
            getSubChapterText(
                    "Kõik käesoleva lepingu tingimustes tehtud muudatused loetakse kehtivaks ainult siis, kui need on tehtud kirjalikult kehtiva lepingu lisana koos nende tingimustega nõustumise kinnitusega "
                            + "mõlema poole allkirjade kujul käesoleval dokumendil. Kõik muud arutelud ja kokkulepped loetakse tühiseks."));

    chapter5.addCell(getSubChapterNumber("5.4"));
    chapter5.addCell(
            getSubChapterText(
                    "Rendileandja ja Rentniku vahel uue koostöölepingu sõlmimine muudab kõik varasemad nende vahel sõlmitud koostöölepinguid tühiseks käesoleva lepingu sõlmimise ning allkirjastamise kuupäevast."));

    pdfDocument.add(chapter5);




    final var signature = getChapterTable();
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
            new Cell(new Paragraph(model.getRenterName(), new Font(TIMES_ROMAN, 9, BOLD)));
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
