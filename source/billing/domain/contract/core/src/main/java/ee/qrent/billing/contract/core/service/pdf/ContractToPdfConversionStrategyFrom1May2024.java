package ee.qrent.billing.contract.core.service.pdf;

import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.*;
import static com.lowagie.text.alignment.HorizontalAlignment.LEFT;
import static java.awt.Color.white;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ContractToPdfConversionStrategyFrom1May2024
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
    chapter1.addCell(
        getSubChapterText(
            "Käesoleva lepingulisa “Tüüptingimused” nr.20042025 lepingulisa on koostatud ja allkirjastatud 20.04.2025 (Signature Timestamp UTC - 20.04.2025 13:04:49 +00:00) ning on saadaval järgmisel lingil:"
                + "www.qqqqqq.ee/tüüptingimused "));
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
        getSubChapterText(
            "Käesoleva lepingulisa “TSK Tingimused” nr.200425 lepingulisa on koostatud ja allkirjastatud 20.04.2025 (Signature Timestamp UTC - 24.04.2025 09:25:39 +00:00) ning on saadaval järgmisel lingil:\n"
                + "www.aaa.tsk \n"));
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
            "Samuti kinnitab Rentnik, et on ta teadlik sellest, et \"TSK tingimused\" lepingulisa sätestatud tingimuste rikkumisel, kaotab TSK oma kehtivuse. Rentniku kohustus hüvitada rendiperioodi ajal rendiautole tekitatud kahju ja selle ulatus arvutatakse siis vastavalt käesolevas lepingus ja lepingulisa \"Tüüptingimused\" tingimustes sätestatule."));
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
            "Rentnik kohustub tasuma Rendileandjale ettemaksu iga täispika kalendri rendinädala eest, vastavalt summale, mis on määratud üleandmise-vastuvõtmise aktile, kas sularahas Rendileandja kontoris, mis asub aadressil Lasnamäe 30a, Tallinn, või ülekandega Rendileandja pangakontole (või muule Rendileandja esindaja poolt määratud kontole) asjakohase selgitusega „autorent + auto number“. Panga- või sularahaülekanne peab olema tehtud hiljemalt jooksva nädala teisipäevaks kella 16:00’ni."));
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

    final var rendileandja2 = new Table(1);
    rendileandja2.setPadding(0f);
    rendileandja2.setSpacing(0f);
    rendileandja2.setWidth(100f);
    rendileandja2.setBorderColor(white);
    rendileandja2.setHorizontalAlignment(LEFT);
    rendileandja2.setBorder(NO_BORDER);
    rendileandja2.setBorder(NO_BORDER);

    final var rendileandja2cell2 =
        new Cell(
            new Paragraph(
                model.getQFirmName()
                    + "rendilepingu sõiduauto taksoteenuse ja majandustegevuse kasutamiseks tüüptingimused",
                new Font(TIMES_ROMAN, 10, BOLD)));
    rendileandja2cell2.setBorder(NO_BORDER);
    rendileandja2cell2.setHorizontalAlignment(LEFT);
    rendileandja2.addCell(rendileandja2cell2);

    final var rendileandja2cell3 =
        new Cell(new Paragraph("Kehtivad alates 01.01.2025", new Font(TIMES_ROMAN, 7, BOLD)));
    rendileandja2cell3.setBorder(NO_BORDER);
    rendileandja2cell3.setHorizontalAlignment(LEFT);
    rendileandja2.addCell(rendileandja2cell3);

    pdfDocument.add(rendileandja2);

    // end add header
    final var chapter1a = getChapterTable();
    chapter1a.addCell(getChapterNumber("I"));
    chapter1a.addCell(getChapterSummary("ÜLDSÄTTED"));
    chapter1a.addCell(getSubChapterNumber("1.1"));
    chapter1a.addCell(
        getSubChapterText(
            "Käesolevad juriidilise isiku Rendileandja renditeenuste kasutamise tingimused (tingimused), sätestavad: "));
    chapter1a.addCell(getSubChapterNumber("1.1.1"));
    chapter1a.addCell(getSubChapterText("sõiduki rentimist"));
    chapter1a.addCell(getSubChapterNumber("1.1.2"));
    chapter1a.addCell(getSubChapterText("sõiduki ja vara kasutamise tingimused ja nõuded"));
    chapter1a.addCell(getSubChapterNumber("1.1.3"));
    chapter1a.addCell(getSubChapterText("Rentniku vastutuse tingimused ja piirid"));
    chapter1a.addCell(getSubChapterNumber("1.1.4"));
    chapter1a.addCell(getSubChapterText("maksetingimused"));
    chapter1a.addCell(getSubChapterNumber("1.1.5"));
    chapter1a.addCell(getSubChapterText("mistahes muud suhted seoses teenuste kasutamisega."));
    chapter1a.addCell(getSubChapterNumber("1.2"));
    chapter1a.addCell(
        getSubChapterText(
            "Alates vastavalt VÕS § 339 rendilepingu sätestatule sõlmivad Rentnik ja Rendileandja lepingulise õigussuhte, mida reguleerivad käesolevad tingimused"
                + "(sealhulgas selle lisad), hinnakiri, teenuse hinnad ja muud sõiduki rentimise eritingimused (leping).\n"));
    chapter1a.addCell(getSubChapterNumber("1.3"));
    chapter1a.addCell(
        getSubChapterText(
            "Enne sõiduki renti võtmist peab Rentnik tutvuma hinnakirja ja muude renditingimustega. Rendilepingu sõlmimisel loetakse, et Rentnik on teenuse"
                + "hindadest, hinnakirjast ja muudest rentimistingimustest teadlik ja on nendega nõustunud."));
    chapter1a.addCell(getSubChapterNumber("1.4"));
    chapter1a.addCell(
        getSubChapterText(
            "Kui tingimuste sätestatud allikates esineb vastuolusid või lahknevusi, tõlgendatakse ja kohaldatakse lepingut järgmise prioriteetsuse alusel:"));
    chapter1a.addCell(getSubChapterNumber("1.4.1"));
    chapter1a.addCell(
        getSubChapterText(
            "rendilepingust toodud eritingimused, teenuse hinnad ja muud konkreetse sõiduki renditingimused;"));
    chapter1a.addCell(getSubChapterNumber("1.4.2"));
    chapter1a.addCell(getSubChapterText("käesolevad tingimused;"));
    pdfDocument.add(chapter1a);

    final var chapter2a = getChapterTable();
    chapter2a.addCell(getChapterNumber("II"));
    chapter2a.addCell(getChapterSummary("MÕISTED"));
    chapter2a.addCell(getSubChapterNumber("2.1"));
    chapter2a.addCell(getSubChapterText("Rendileandja – " + model.getQFirmName()));
    chapter2a.addCell(getSubChapterNumber("2.2"));
    chapter2a.addCell(
        getSubChapterText(
            "Hinnakiri - lepingus ja/või eritingimustes ja/või auto üleandmise aktis fikseeritud rendi hind, trahvid, muud tasud ja lõivud. "
                + "Käesolevate tingimustega nõustudes nõustub Rentnik samal ajal ka käesolevate tingimuste lahutamatuks osaks oleva hinnakirjaga."));
    chapter2a.addCell(getSubChapterNumber("2.3"));
    chapter2a.addCell(
        getSubChapterText(
            "Liikluseeskirjad - asjaomases riigis kehtivad liikluseeskirjad ja nendega seotud õigusaktide sätted."));
    chapter2a.addCell(getSubChapterNumber("2.4"));
    chapter2a.addCell(
        getSubChapterText(
            "Rentnik - Rendileandja klient (füüsiline ja/või juriidiline isik), kes nõustub käesolevate tingimustega ja kasutab vastavalt "
                + "lepingule rendiauto teenuseid. Rentnikul on õigus kasutada sõidukit ainult siis, kui ta on vähemalt 21 aastat vana ja omab mootorsõiduki juhistaaži vähemalt kaks aastat. "));
    chapter2a.addCell(getSubChapterNumber("2.5"));
    chapter2a.addCell(
        getSubChapterText(
            "Kasutusperiood - ajavahemik sõiduki üleandmise-vastuvõtmise akti alusel võtmise hetkest kuni sõiduki tagastamise"
                + " vastuvõtmise akti allkirjastamiseni. Kasutusperioodist lepitakse eraldi kokku lepingu eritingimustes. "
                + "Kasutusperiood on arvestatud kalendri täisnädala kaupa ja rendinädalaid peetakse ajaperioodi alates uue "
                + "nädala Esmaspäeva kella 10:00’ni kuni järgmise nädala Esmaspäeva kella 10:00’ni."));
    chapter2a.addCell(getSubChapterNumber("2.6"));
    chapter2a.addCell(
        getSubChapterText(
            "Teenused - Rendileandja poolt Rentnikule osutatavad autorendi teenused vastavalt VÕS  § 399 sätestatule, "
                + "mille käigus Rendileandja annab teisele isikule (rentnikule) kasutamiseks rendilepingu eseme ning võimaldab "
                + "talle rendilepingu esemest korrapärase majandamise reeglite järgi saadava vilja. Rentnik on kohustatud maksma selle eest tasu (renti)."));
    chapter2a.addCell(getSubChapterNumber("2.7"));
    chapter2a.addCell(
        getSubChapterText(
            "Üleandmise-vastuvõtmise akt - kirjalik dokument, millega fikseeritakse rendiauto registreerimisnumbrit,"
                + " üleandmise-vastuvõtmise kuupäeva, nädala rendihinda, auto seisundit (vigastusi ja puudusi) üleandmise ajal,"
                + " ning Rendileandja ja Rentniku nõusolekut nimetatud üleandmise-vastuvõtmise akti andmetega, mis kinnitatakse poolte allkirjadega."));
    chapter2a.addCell(getSubChapterNumber("2.8"));
    chapter2a.addCell(
        getSubChapterText(
            "Rendiauto kahjustuste fikseerimise akt on kirjalik dokument millega fikseeritakse rendiesemele tekitatud "
                + "vigastused selle rentniku kasutamise aja jooksul. Akt sisaldab vigastuste kirjeldust ja määrab nende asukohti. Võimalusel lisatakse aktile kahjustuste fotosid."));
    chapter2a.addCell(getSubChapterNumber("2.9"));
    chapter2a.addCell(
        getSubChapterText(
            "Rendiauto (sõiduk) - vaba auto Rendileandja autopargist. Rendilepingu kohaselt kohustub Rendileandja andma "
                + "Rentnikule kasutamiseks rendilepingu ning käesoleva tingimuste sätestatu alusel vaba sõidukit oma autopargist - rendilepingu eseme ehk Rendiauto."
                + "Rentnik on kohustatud maksma selle eest tasu (Renti) Rendileandjale kogu Rendiperioodi eest."));
    chapter2a.addCell(getSubChapterNumber("2.10"));
    chapter2a.addCell(
        getSubChapterText(
            "Rent - tasu rendiauto kasutamise perioodi eest vastavalt lepingus, aktis ja tüüptingimustes toodule."));
    chapter2a.addCell(getSubChapterNumber("2.11"));
    chapter2a.addCell(
        getSubChapterText(
            "Rendi arvutamise põhimõtted - lepinguline nädala renditasu suurus üleandmise-vastuvõtmise hetkeks "
                + "sõltub üleantava auto omadustest ja määratakse eraldi iga auto üleandmise-vastuvõtmise aktis. "
                + "Üleandmise-vastuvõtmise aktis määratud renditasu on aktuaalne vaid täisrendinädala eest tasumise puhul. Täpsemalt sätestatud p.12.3."));
    chapter2a.addCell(getSubChapterNumber("2.12"));
    chapter2a.addCell(
        getSubChapterText(
            "Leping - autorendi teenuste osutamise leping. Rentniku ja Rendiandja vahel sõlmitud teenuste osutamise"
                + " leping loetakse sõlmituks alates allakirjutamise hetkest. Lepingut reguleerivad sätted, nagu on sätestatud käesolevate tingimuste p. 1.2."));
    chapter2a.addCell(getSubChapterNumber("2.13"));
    chapter2a.addCell(
        getSubChapterText("Käendus - erikokkuleppe sõlmitud lepingus eritingimusena."));
    chapter2a.addCell(getSubChapterNumber("2.14"));
    chapter2a.addCell(
        getSubChapterText(
            "Võlalimiit –  võla summa mille ületamisel loetakse leping oluliselt rikutuks."));
    chapter2a.addCell(getSubChapterNumber("2.15"));
    chapter2a.addCell(
        getSubChapterText(
            "Muude käesolevates tingimustes kasutatud mõistete tähendus vastab tingimuste p. 1.2 nimetatud allikates sätestatud tähendusele."));
    pdfDocument.add(chapter2a);

    final var chapter3a = getChapterTable();
    chapter3a.addCell(getChapterNumber("III"));
    chapter3a.addCell(getChapterSummary("SÕIDUKI KASUTUSTINGIMUSED"));
    chapter3a.addCell(getSubChapterNumber("3.1"));
    chapter3a.addCell(
        getSubChapterText(
            "Rendileandja kohustub tagama, et rendiauto on heas sõidukorras ja selle vahetuks otstarbeks kasutamiseks "
                + "ja käitamiseks sobilik, võttes arvesse sõiduki tavapärast kulumist."));
    chapter3a.addCell(getSubChapterNumber("3.2"));
    chapter3a.addCell(
        getSubChapterText(
            "Defektideks ei loeta rikkeid ja talitlushäireid, mis ei mõjuta praegu ega lähitulevikus liiklusohutust "
                + "(nt: kriimustused seadmete sise- ja välispindadel, varuosad, multimeediaseadmete talitlushäireid, andurite rikked)."
                + " Puudusi fikseeritakse üleandmise-vastuvõtmise aktis."));
    chapter3a.addCell(getSubChapterNumber("3.3"));
    chapter3a.addCell(getSubChapterText("Renditeenuse kasutamisel peab Rentnik muuhulgas:"));
    chapter3a.addCell(getSubChapterNumber("3.3.1"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnik on kohustatud vaatama sõiduki üle enne selle vastuvõtmist ja veenduma selle sobilikkuses ja korrasolekus,"
                + " tegema vastavasisulise märke autol olemasolevatest kahjustustest üleandmise-vastuvõtmise aktil ja panna oma "
                + "allkirja nende kinnitamiseks. Rentniku allkiri kinnitab tema nõusolekut ainult üleandmise-vastuvõtmise aktil märgitud kahjustuste olemasoluga."));
    chapter3a.addCell(getSubChapterNumber("3.3.2"));
    chapter3a.addCell(
        getSubChapterText(
            "täitma sõiduki käitamisnõudeid, sealhulgas mistahes käesolevates tingimustes nimetamata nõudeid, mis on selliste esemete kasutamisel tavapärased; "));
    chapter3a.addCell(getSubChapterNumber("3.3.3"));
    chapter3a.addCell(
        getSubChapterText(
            "sõitma tähelepanelikult, ettevaatlikult, viisakalt ja ohutult, austades teisi liiklejaid ja inimesi,"
                + " võttes kõik vajalikud ettevaatusabinõud ja ohustamata teiste liiklejate, teiste inimeste või nende vara ja keskkonna ohutust;"));
    chapter3a.addCell(getSubChapterNumber("3.3.4"));
    chapter3a.addCell(
        getSubChapterText(
            "käituma piisavalt ettevaatlikult, mõistlikult, vastutustundlikult ja teadlikult;"));
    chapter3a.addCell(getSubChapterNumber("3.3.5"));
    chapter3a.addCell(
        getSubChapterText(
            "olema täiesti kaine (0,00 promilli) ja mitte vaimset seisundit mõjutavate ainete mõju all;"));
    chapter3a.addCell(getSubChapterNumber("3.3.6"));
    chapter3a.addCell(
        getSubChapterText(
            "mitte juhtima sõidukit haige või väsinuna, kui sõiduki juhtimine võib ohustada liiklusohutust või "
                + "kui esineb mõni muu põhjus, miks Rentnik ei saa sõidukit vastavalt õigusaktides sätestatud nõuetele ohutult juhtida;"));
    chapter3a.addCell(getSubChapterNumber("3.3.7"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust lubada teistel isikutel sõidukit juhtida, kontrollida või kasutada, "
                + "sõidukit edasi rentida, käesolevates tingimustes sätestatud mistahes õigusi või kohustusi üle anda;"));
    chapter3a.addCell(getSubChapterNumber("3.3.8"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust kopeerida, muuta või kustutada sõiduki süsteemis olevaid andmeid, "
                + "omastada, hävitada või muul viisil kahjustada sõidukis olevaid sõiduki dokumente (nt tehnilist passi);"));
    chapter3a.addCell(getSubChapterNumber("3.3.9"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikule on keelatud anda üle autoga seotud dokumendid ning poolte suhted reguleerivad dokumendid kolmandatele isikutele ilma teise poole nõusolekuta."));
    chapter3a.addCell(getSubChapterNumber("3.3.10"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust sõidukit lahti monteerida, remontida või muuta;"));
    chapter3a.addCell(getSubChapterNumber("3.3.11"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul  ei ole õigust vedada sõidukis plahvatusohtlikke, tuleohtlikke, mürgiseid aineid või "
                + "inimelule või -tervisele kahjulikke aineid jms ega õigust kasutada sõidukis või sõiduki lähedal kütteseadmeid, lahtist tuld või muid võimalikke tuleallikaid;"));
    chapter3a.addCell(getSubChapterNumber("3.3.12"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust kasutada sõidukit eesmärkidel, milleks see ei ole ette nähtud või kohandatud, "
                + "nt kauba vedamiseks või kasutada sõidukit suurema koorma vedamiseks (raskete veoste vedamiseks jne),"
                + " suurte loomade vedamiseks, samuti metsas, veekogudes ja muudel maastikel sõitmiseks, sõidukit üle koormata, koormat mitte nõuetekohaselt kinnitada ja paigutada;"));
    chapter3a.addCell(getSubChapterNumber("3.3.13"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust kasutada sõidukit võidusõitudel, võistlustel ega muudel spordi või võidusõiduga seotud eesmärkidel;"));
    chapter3a.addCell(getSubChapterNumber("3.3.14"));
    chapter3a.addCell(
        getSubChapterText(
            "Rentnikul ei ole õigust kasutada sõidukit õppesõidukina ega kasutada sõidukit teiste sõidukite vedamiseks; "));
    chapter3a.addCell(getSubChapterNumber("3.3.15"));
    chapter3a.addCell(getSubChapterText("täitma liikluseeskirju;"));
    chapter3a.addCell(getSubChapterNumber("3.3.16"));
    chapter3a.addCell(
        getSubChapterText(
            "kaitsma sõidukit, kasutama sõidukit ja selles olevat vara hoolikalt, võtma kõik mõistlikud meetmed sõiduki"
                + " turvalisuse tagamiseks (st lukustama sõiduki, sulgema aknad, lülitama tuled ja muusikaseadme välja jne);"));
    chapter3a.addCell(getSubChapterNumber("3.3.17"));
    chapter3a.addCell(
        getSubChapterText(
            "tagama, et A. sõidukis ei suitsetata ja B. väikseid lemmikloomi veetakse selleks ette nähtud spetsiaalses transpordikastis;"));
    chapter3a.addCell(getSubChapterNumber("3.3.18"));
    chapter3a.addCell(
        getSubChapterText(
            "enne sõidukiga sõitma asumist peab Rentnik veenduma, et sõidukil ei ole ilmseid rikkeid ja/või defekte ning nende "
                + "olemasolu korral viivitamatult teavitama Rendileandja taasesitamist võimaldavas vormis;"));
    chapter3a.addCell(getSubChapterNumber("3.3.19"));
    chapter3a.addCell(getSubChapterText("täitma muid õigusaktides sätestatud nõudeid. "));
    chapter3a.addCell(getSubChapterNumber("3.3.20"));
    chapter3a.addCell(
        getSubChapterText(
            "Sõiduki võib kasutada ainult Eesti territooriumil. Rentnikul on lubatud kasutada sõidukit väljaspool Eesti "
                + "territooriumi üksnes Rendileandja eelneval kirjalikul nõusolekul. Ettevõte otsustab nõusoleku andmise pärast seda,"
                + " kui on hinnanud Rentnikul taotluse individuaalselt."));
    pdfDocument.add(chapter3a);

    final var chapter4a = getChapterTable();
    chapter4a.addCell(getChapterNumber("IV"));
    chapter4a.addCell(getChapterSummary("SÕIDUKI KÄTTESAAMINE JA TAGASTAMINE"));
    chapter4a.addCell(getSubChapterNumber("4.1"));
    chapter4a.addCell(
        getSubChapterText(
            "Sõiduki valjastamise koht on Lasnamäe 30a, Tallinn. Sõiduk antakse Rendileandja poolt üle Rentnikule kirjaliku poolte poolt allakirjutatud akti alusel."));
    chapter4a.addCell(getSubChapterNumber("4.2"));
    chapter4a.addCell(
        getSubChapterText(
            "Sõiduki kasutamise periood ei ole piiratud. Pooled võivad lepingu eritingimustes leppida kokku minimaalsest ja maksimaalsest renditeenuse kestusest."));
    chapter4a.addCell(getSubChapterNumber("4.3"));
    chapter4a.addCell(
        getSubChapterText(
            "Sõiduki tagastamiskoht on Lasnamäe 30a, Tallinn. Sõiduk tagastatakse Rendileandjale Rendileandja ja Rentniku poolt kirjaliku allkirjastatud akti alusel."));
    chapter4a.addCell(getSubChapterNumber("4.4"));
    chapter4a.addCell(
        getSubChapterText(
            "Rentnik on kohustatud kirjaliku üleandmise -vastuvõtmise akti alusel tagastama sõiduki lepingus määratud Rendiperioodi "
                + "lõppemisel kokkulepitud kohas ja ettenähtud ajal. Sõidukit ei tohi maha jätta."));
    chapter4a.addCell(getSubChapterNumber("4.5"));
    chapter4a.addCell(
        getSubChapterText(
            "Rentnik vastutab täies ulatuses ka puuduste eest, mis on tekkinud sõidukil, mis on tagastatud Rendileandjale ilma "
                + "kirjaliku üleandmise-vastuvõtmise aktita, kui ei ole tõendatud vastupidi ja/või algses üleandmise aktis märgitud."));
    chapter4a.addCell(getSubChapterNumber("4.6"));
    chapter4a.addCell(
        getSubChapterText(
            "Tagatise summa arvestatakse lähtudes iga rendiauto omadusest eraldi, lepitakse kokku lepingutingimustega ning märgitakse "
                + "lepingus või üleandmise aktis. Tagatise summa määr on 300 eurot, mis muutub tasumiseks kohustuslikuks viivitamatult "
                + "peale rendilepingu allkirjastamist. Tagatise summa tagastatakse rentnikule mitte varem kui kolm nädalat parast auto rendiandjale tagastamist."));
    chapter4a.addCell(getSubChapterNumber("4.7"));
    chapter4a.addCell(
        getSubChapterText(
            "Rentnik peab tagastama rendiauto seisukorras, mis ei ole kehvem kui seisukord, milles selle kätte saadi ja võttes arvesse "
                + "selle kulumist. Sõiduki kulumine määratakse vastavalt Eesti Liisinguühingute Liidu poolt koostatud ja avaldatud "
                + "„Sõidukite loomuliku ja ebaloomuliku kulumise määramise juhendile“, mis on avaldatud liidu kodulehel "
                + ""
                + "https://www.liisingliit.ee/regulatsioon/juhendid-ja-aktid (juhend loetakse tingimuste lahutamatuks osaks), ja riikliku tehnoülevaatuse eeskirjale."));
    chapter4a.addCell(getSubChapterNumber("4.8"));
    chapter4a.addCell(getSubChapterText("Kulumine ei hõlma:"));
    chapter4a.addCell(getSubChapterNumber("4.8.1"));
    chapter4a.addCell(
        getSubChapterText(
            "purunenud, deformeerunud või muul viisil mehaaniliselt või termiliselt kahjustatud osi, seadmeid ja mehhanisme;"));
    chapter4a.addCell(getSubChapterNumber("4.8.2"));
    chapter4a.addCell(
        getSubChapterText(
            "sõiduki keres olevaid mõlke, värvikihis olevaid pragusid ja nähtavaid kriimustusi;"));
    chapter4a.addCell(getSubChapterNumber("4.8.3"));
    chapter4a.addCell(
        getSubChapterText("värvikihi kulumist sõiduki tugeva pesemise/puhastamise tõttu;"));
    chapter4a.addCell(getSubChapterNumber("4.8.4"));
    chapter4a.addCell(
        getSubChapterText(
            "kehva kvaliteediga remonditöid ja/või remonditöödest tulenevaid defekte (vaatamata sellele, et "
                + "Rentnikul ei ole õigust sõidukit ise ega kolmandate isikute kaudu remontida);"));
    chapter4a.addCell(getSubChapterNumber("4.8.5"));
    chapter4a.addCell(getSubChapterText("sõidukikere akendel olevaid pragusid;"));
    chapter4a.addCell(getSubChapterNumber("4.8.6"));
    chapter4a.addCell(
        getSubChapterText(
            "sõiduki valest kasutamisest ja/või puhastamisest tingitud kriimustusi sõidukikere akendel;"));
    chapter4a.addCell(getSubChapterNumber("4.8.7"));
    chapter4a.addCell(
        getSubChapterText(
            "salongi kahjustusi ja määrdumist, näiteks põlenud või määrdunud istmeid, katkiseid armatuurlaua "
                + "osi või muid plastosi, pagasiruumi luuki, akende avamise käepidemeid jms;"));
    chapter4a.addCell(getSubChapterNumber("4.8.8"));
    chapter4a.addCell(getSubChapterText("sõidukikere geomeetria vigastusi."));
    pdfDocument.add(chapter4a);

    final var chapter5 = getChapterTable();
    chapter5.addCell(getChapterNumber("V"));
    chapter5.addCell(getChapterSummary("SÜNDMUSED KASUTUSEPERIOODIL"));
    chapter5.addCell(getSubChapterNumber("5.1"));
    chapter5.addCell(
        getSubChapterText(
            "Kui sõiduk läheb rikki, sõiduki armatuurlaual hakkab põlema mistahes hoiatus, kuulda on kahtlast "
                + "kummalist heli või sõidukit ei ole võimalik enam ohutult kasutada ja juhtida, peab Rentnik koheselt: \n"
                + "a) lõpetama sõiduki kasutamise;\n"
                + "b) teavitama Rendileandja sellest telefoni teel ja\n"
                + "c) täitma muid Rendileandja juhiseid.\n"));
    chapter5.addCell(getSubChapterNumber("5.2"));
    chapter5.addCell(
        getSubChapterText(
            "Liiklusõnnetuse korral või sõiduki, kolmandate isikute või nende vara vigastamisel, "
                + "Rendileandja või selle vara mistahes muul viisil kahjustamisel peab Rentnik Rendileandjat "
                + "sellest koheselt teavitama ja vajadusel teavitama asjakohaseid riigiasutusi või teenistusi "
                + "(politsei, tuletõrje jne), täitma liiklusõnnetuse deklaratsiooni ja/või tegema muid vajalikke toiminguid, "
                + "mida tuleb vastavalt kohaldatavatele õigusaktidele teha, samuti toiminguid, mida tuleb teha sõidukile,"
                + " muule varale ja/või isikutele suurema kahju tekkimise vältimiseks või kahju vähendamiseks."));
    chapter5.addCell(getSubChapterNumber("5.3"));
    chapter5.addCell(
        getSubChapterText(
            "Liiklusõnnetuse korral, milles pole Rentnik süüdi, tuleb liiklusõnnetuses osalenud juhtidel "
                + "täita nõuetekohaselt LE vorm nr.1 või kutsuda sündmuskohale politsei, et liiklusõnnetuse asjaolusid fikseerida."
                + " Selle LE vorm nr.1 või politsei poolt koostatud sündmuskoha skeemi koopia mitteesitamisel Rendileandjale,"
                + "on Rentnik täies ulatuses vastutav Rendileandjale tekitatud kahjude eest."));
    chapter5.addCell(getSubChapterNumber("5.4"));
    chapter5.addCell(
        getSubChapterText(
            "Kahju tekkimisel Rendileandjale või kolmandatele isikutele või dokumentide või esemete kadumisel"
                + " on Rentnik kohustatud esitama Rendileandjale kirjaliku seletuse juhtunu kohta hiljemalt 24 tunni möödumisel. "
                + "Kui puudub muu võimalus kirjaliku seletuse andmiseks, võib seda esitada Rendileandjale erandkorras ka "
                + "lepingus toodud e-posti aadressile, varustades seletuse digitaalallkirjaga."));
    pdfDocument.add(chapter5);

    final var chapter6 = getChapterTable();
    chapter6.addCell(getChapterNumber("VI"));
    chapter6.addCell(getChapterSummary("VASTUTUS"));
    chapter6.addCell(getSubChapterNumber("6.1"));
    chapter6.addCell(getSubChapterText("Hüvitamise üldsätted"));
    chapter6.addCell(getSubChapterNumber("6.1.1"));
    chapter6.addCell(
        getSubChapterText(
            "Rentnik kui suurema ohu allika valdaja vastutab kogu sõiduki kasutusperioodi jooksul täielikult tingimuste "
                + "ja õigusaktide rikkumise eest ning Rendileandjale, sõidukile ja/või kolmandatele isikutele tekitatud kahju eest."));
    chapter6.addCell(getSubChapterNumber("6.1.2"));
    chapter6.addCell(
        getSubChapterText(
            "Rentnik vastutab koos Rentnikuga sõidukit kasutavate isikute (nt kaassõitjate) ohutuse, tervise ja elu eest,"
                + " samuti enda vara või teiste isikute vara kahjustamise, hävimise või kaotsimineku eest, kui kohaldatavates õigusaktides ei ole sätestatud teisiti."));
    chapter6.addCell(getSubChapterNumber("6.1.3"));
    chapter6.addCell(
        getSubChapterText(
            "Mitte ükski käesolevate tingimuste säte ei piira Rendileandjal õigust nõuda võlakohustuste täitmist "
                + "kolmandate isikute poolt (vastavalt lepinguvälisele vastutusele), kes põhjustasid oma tegevuse või"
                + " tegevusetusega Rendileandjale kahju, kuid selline Rendileandja õigus ei piira mingil juhul Rentniku eelnevalt nimetatud vastutust."));
    chapter6.addCell(getSubChapterNumber("6.1.4"));
    chapter6.addCell(
        getSubChapterText(
            "Käesolevate tingimuste tähenduses on Rendileandja poolt kantud kahju(d) järgmine(sed) (sealhulgas, kuid mitteainult):\n"
                + "a.\tRendileandja mainele, firmaväärtusele ja heale nimele, kaubamärgile ja ärinimele, Rendileandja üldjuhtimise põhimõtetele, samuti Rendileandja sotsiaalsele kuvandile tekitatud kahju;\n"
                + "b.\tsõidukile (sealhulgas selle väärtuse vähenemine), selle Rendileandjale või teistele isikutele kuuluvatele osadele ja varale, sealhulgas sõiduki lisatarvikutele tekitatud kahju;\n"
                + "c.\tkõik sõiduki transportimise, turvalisuse, puhastamise, parkimise, remonditöödega seotud kulud (nii tegelikult kantud kui ka veel kandmata kulud, mille sõltumatu hindaja on kindlaks teinud ja hinnanud kui vajalikud sõiduki remondikulud);\n"
                + "d.\tkahju hindamise, kindlakstegemise, kahjukäsitluse ja asjaajamisega seotud kulud;\n"
                + "e.\tsõiduki müügi, võõrandamisega seotud kulud;\n"
                + "f.\tvõlgnevuse sissenõudmise kulud;\n"
                + "g.\tkaudne kahju (nt saamata jäänud tulu, sõiduki jõude seismise aeg);\n"
                + "h.\tkahju ärahoidmise või vähendamisega seotud kulud;\n"
                + "i.\tkindlustushüvitised, mis jäävad Rendileandjale välja maksmata Rentniku poolt tingimuste rikkumise tõttu.\n"));
    chapter6.addCell(getSubChapterNumber("6.1.5"));
    chapter6.addCell(
        getSubChapterText(
            "Ilma et see piiraks käesolevate tingimuste mistahes sätteid, vastutab Rentnik täielikult sõiduki kahjustamise eest:\n"
                + "a.\tRentnik vastutab kogu kahju eest, mis on tema tegevuse ja või tegevusetuse tulemusena on tekitatud sõidukile (avarii, väline kahju, kriimustamised, deformeerimised jne. "
                + "Nimekiri ei ole lõplik).  Kahju hüvitamiseks pooled lähtuvad VÕS sätestatutest ja üleandmise-vastuvõtmise aktis märgitud andmetest.\n"
                + "b.\tkui sõiduk, selle lisatarvikud või nende mõni osa varastatakse või saab kahjustada, sest juht jättis aknad või katuseluugid lahti, katuse peale tõmbamata, uksed lukustamata jne;\n"
                + "c.\tkui sõiduk või selle mõni osa varastatakse või saab kahjustada isiku poolt, kes kasutas sõidukit koos Rentnikuga või Rentniku teadmisel ja tahtel.\n"));
    chapter6.addCell(getSubChapterNumber("6.1.6"));
    chapter6.addCell(
        getSubChapterText(
            "Juhul kui tingimusi rikub või muid ebaseaduslikke tegevusi või tegematajätmisi teeb ja/või kahju Rendileandjale ja/või teistele isikutele,"
                + " sealhulgas Rentnikule endale põhjustab kolmas isik, kellele Rentnik lubab, annab nõusoleku, annab üle või muul viisil võimaldab või teeb võimalikuks või mistahes muul viisil "
                + "loob oma aktiivse või passiivse tegevuse ja/või tegevusetusega otseselt või kaudselt, tahtlikult või hooletuse tõttu kolmandale isikule või kolmandate isikute rühmale võimaluse"
                + " pääseda sõidukisse, juhtida ja/või kasutada muul viisil sõidukit, selle lisatarvikuid ja/või  ei takista seda:\n"
                + "a.\t kannab Rentnik kõiki kolmandate isikute tegevusest või tegevusetusest tingitud tingimuste ja seaduste rikkumisest tulenevaid riske, vastutust ja kahju ja/või "
                + "vastutab Rendileandjale ja/või kolmandatele isikutele põhjustatud kahju eest;\n"
                + "b.\tkohaldatakse Rentniku suhtes tingimustes sätestatud trahve, kahjuhüvitisi ja muud vastutust ning nendest tulenevaid tagajärgi, lugedes sellised tegevused ja "
                + "rikkumised Rentnik enda poolt toime panduks ja kahju Rentnik enda poolt põhjustatuks;\n"
                + "c.\tLepingus nimetatud trahvid, parkimistrahvid, kiiruseületamise trahvid ja muud järelevalve organite poolt määratud trahvid Rentnik kohustatud tasuma 7 (seitsme) "
                + "päeva jooksul pärast Rentnikule trahvi teatavaks tegemisest ning kättetoimetamisest. Kättetoimetamine võib olla elektronile, sms, e-kiri, füüsiliselt kätte andmine jne.\n"));
    pdfDocument.add(chapter6);

    final var chapter7 = getChapterTable();
    chapter7.addCell(getChapterNumber("VII"));
    chapter7.addCell(getChapterSummary("TRAHVID"));
    chapter7.addCell(getSubChapterNumber("7.1"));
    chapter7.addCell(
        getSubChapterText(
            "Tingimustes käsitletakse trahve ja nende määramise tingimusi, põhimõtteid ja korda, "
                + "kuid konkreetsed trahviliigid ja nende konkreetsed summad on toodud lepingus ja või käesolevates tingimustes."));
    chapter7.addCell(getSubChapterNumber("7.2"));
    chapter7.addCell(
        getSubChapterText(
            "Igal juhul Rentnik peab enne rendilepingu sõlmimist tutvuma kohaldatava ja hetkel kehtiva trahvinimekirjaga."));
    chapter7.addCell(getSubChapterNumber("7.3"));
    chapter7.addCell(
        getSubChapterText(
            "Tingimustes käsitletavad ja lepingus loetletud trahvid loetakse Rendileandja poolt määravaks leppetrahviks."));
    chapter7.addCell(getSubChapterNumber("7.4"));
    chapter7.addCell(
        getSubChapterText(
            "Kõik tingimustes ja lepingus nimetatud trahvid, välja arvatud tingimustes konkreetselt sätestatud erandid,"
                + " on kõikehõlmavad, st need sisaldavad Rendileandjale tekitatud kahju. Lisaks sellele peab Rentnik trahvi "
                + "maksmisel hüvitama Rendileandjale kõik täiendavad summad või täiendavad kahjuliigid, mida tasutud trahv ei kata."
                + " Trahvi tasumine ei vabasta Rentnikku kohustusest hüvitada kõik muud Rendileandja poolt kantud kahjud, "
                + "mida tasutud trahv ei kata. Lisaks ei vabasta trahvi määramine Rentnikku kohustusest täita muid käesolevates tingimustes"
                + " ja/või õigusaktides sätestatud kohustusi ulatuses, milles Rentniku poolt tasutud trahv ei kata või asenda selliseid kohustusi "
                + "vastavalt nende olemusele või sisule. Kõik erinevad Rentniku individuaalsetest tegevustest põhjustatud kahjuliigid määratletakse "
                + "ja neid hinnatakse eraldi, isegi kui need on põhjustatud samal ajal. Rentniku sellistest individuaalsetest tegevustest põhjustatud"
                + " individuaalsete/erinevate kahjuliikide hüvitamine (trahvi tasumine ja/või kahju hüvitamine) ei ole vastastiku kaasav ja seda"
                + " kohaldatakse iga kahjuliigi ja seda kahjuliiki põhjustanud Rentniku vastavate tegevuste suhtes eraldi."));
    chapter7.addCell(getSubChapterNumber("7.5"));
    chapter7.addCell(
        getSubChapterText(
            "Rentnik peab maksma Rendileandjale hinnakirjas toodud summas trahvi muu hulgas järgmistel juhtudel"
                + " (allpool toodud juhtude loetelu on toodud üksnes illustreerimiseks ja trahvide ammendav loetelu on toodud hinnakirjas):"));
    chapter7.addCell(getSubChapterNumber("7.5.1"));
    chapter7.addCell(
        getSubChapterText(
            "sõiduki, selle osade, lisatarvikute, lisaseadmete (sealhulgas sõiduki võtme) või seadmete kahjustamise või kaotsimineku korral;"));
    chapter7.addCell(getSubChapterNumber("7.5.2"));
    chapter7.addCell(getSubChapterText("sõidukis suitsetamise korral;"));
    chapter7.addCell(getSubChapterNumber("7.5.3"));
    chapter7.addCell(getSubChapterText("ohtliku, hoolimatu või hooletu sõitmise korral;"));
    chapter7.addCell(getSubChapterNumber("7.5.4"));
    chapter7.addCell(
        getSubChapterText(
            "määrdunud, musta sõiduki korral, kui sõiduk on määrdunum kui sõidukite tavapärase kasutamise korral"
                + " (näiteks maastikul, metsas, veekogudes, madalsoodes, mägedel, ainult eritranspordi või spetsiaalselt "
                + "ettevalmistatud sõidukitega ligipääsetavates kohtades sõitmisel või liikluseeskirjade rikkumisel);"));
    chapter7.addCell(getSubChapterNumber("7.5.5"));
    chapter7.addCell(
        getSubChapterText(
            "alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete ja muude vaimset seisundit mõjutavate ainete mõju "
                + "all sõitmise korral (või kui Rentnik tarvitas alkoholi või muid joovastavaid aineid pärast liiklusõnnetust "
                + "enne liiklusõnnetuse asjaolude tuvastamist või vältis vere alkoholisisalduse testi või joobetesti tegemist "
                + "(käesolevate tingimuste tähenduses tähendab vere alkoholisisaldus või joove õigusaktides sätestatut)."
                + " Rentnik peab maksma Rendileandjale alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete või "
                + "muude vaimset seisundit mõjutavate ainete mõju all sõitmise eest hinnakirjas nimetatud summas trahvi"
                + " ka juhul, kui Rentnik andis sõiduki üle või tegi sõiduki juhtimise muul viisil võimalikuks teisele isikule, "
                + "kui isik oli alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete või muude vaimset seisundit "
                + "mõjutavate ainete mõju all või kui isik vältis vere alkoholisisalduse testi või joobetesti tegemist;"));
    chapter7.addCell(getSubChapterNumber("7.5.6"));
    chapter7.addCell(
        getSubChapterText(
            "sõiduki või selle juurde kuuluva vara seadusevastase omastamise või kaotsimineku korral;"));
    chapter7.addCell(getSubChapterNumber("7.5.7"));
    chapter7.addCell(
        getSubChapterText("käesolevate tingimuste või õigusaktide muude sätete rikkumise korral."));
    pdfDocument.add(chapter7);

    final var chapter8 = getChapterTable();
    chapter8.addCell(getChapterNumber("VIII"));
    chapter8.addCell(getChapterSummary("TRAHVIDE ERISÄTTED"));
    chapter8.addCell(getSubChapterNumber("8.1"));
    chapter8.addCell(
        getSubChapterText(
            "Hinnakirjas märgitud trahv alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete ja muude vaimset "
                + "seisundit mõjutavate ainete mõju all sõitmise eest (või kui Rentnik tarvitas alkoholi või muid joovastavaid"
                + " aineid pärast liiklusõnnetust enne liiklusõnnetuse asjaolude tuvastamist või vältis vere alkoholisisalduse "
                + "testi või joobetesti tegemist) loetakse Rentniku ja Rendileandja poolt eelnevalt kokkulepitud leppetrahviks "
                + "kahju põhjustamise eest Rendileandja mainele, firmaväärtusele ja heale nimele, kaubamärkidele ja ärinimele, "
                + "Rendileandja üldjuhtimise põhimõtetele, samuti Rendileandja sotsiaalsele kuvandile, samuti on see mõeldud "
                + "kõikide muude Rendileandjale tekitatud ebamugavuste, piirangute jms eest, mis on tingitud sellest, et Rentnik"
                + " ei täida nõuetekohaselt tingimustes sätestatud nõudeid või jätab need täitmata. Eelnevalt nimetatud trahv "
                + "tagab ka seda, et Rentnik täidab nõuetekohaselt kohustust mitte sõita alkoholijoobes (üle 0,00 promilli) "
                + "või narkootiliste ainete või muude vaimset seisundit mõjutavate ainete mõju all, nagu on täpsemalt "
                + "kirjeldatud käesolevates tingimustes, ning täidab sellega seotud ennetavat funktsiooni."));
    chapter8.addCell(getSubChapterNumber("8.2"));
    chapter8.addCell(
        getSubChapterText(
            "Liiklusõnnetusest või kolmandaisiku õigusvastasest käitumisest tekkinud kahju kannab Rentnik ulatuses, "
                + "mida ei kanna kindlustus (s.h. omavastutuse määra). Kui kindlustusfirma keeldub kindlustushüvitise "
                + "väljamaksmisest või kui kahjujuhtum ei ole kindlustusjuhtum, kohustub Rentnik tasuma Rendileandjale sõiduki täisväärtuse ja hüvitama tekkinud kahjud."));
    chapter8.addCell(getSubChapterNumber("8.3"));
    chapter8.addCell(
        getSubChapterText(
            "Kui rentnik annab sõidukit üle kolmandale isikule, kannab Rentnik täielikult Rendileandjale või kolmandatele isikutele tekkinud kahju."));
    chapter8.addCell(getSubChapterNumber("8.4"));
    chapter8.addCell(
        getSubChapterText(
            "Kui Rendiauto on varastatud, ärandatud või röövitud, on Rentnik vastutav sõiduki täisväärtuse ulatuses ja kohustub hüvitama Rendileandjale tekitatud kahjud."));
    chapter8.addCell(getSubChapterNumber("8.5"));
    chapter8.addCell(
        getSubChapterText(
            "Kui Rendileandjale tagastatud sõiduk vajab remonti, kannab Rentnik iga remondipäeva eest lepingus kokkulepitud rendipäeva hinnale lisaks ka remondikulud."));
    chapter8.addCell(getSubChapterNumber("8.6"));
    chapter8.addCell(
        getSubChapterText(
            "Ebakvaliteetsest kütusest tekkinud kahjud kannab Rentnik. Kui sõiduki kütusepaaki on täidetud vale kütusega, "
                + "hüvitab Rentnik Rendileandjale sellest tingitud kahju vastavalt kahju kõrvaldava teenusepakkuja poolt väljastatud arvele, lähtudes tegelikult tekitatud kahjust."));
    chapter8.addCell(getSubChapterNumber("8.7"));
    chapter8.addCell(
        getSubChapterText(
            "Sõiduki dokumentide või puuduliku varustusega sõiduki tagastamisel tasub Rentnik Rendileandjale leppetrahvi"
                + " 250.-EUR iga kaotatud või puuduva dokumendi või eseme kohta ja hüvitab tekkinud kahjud."));
    chapter8.addCell(getSubChapterNumber("8.8"));
    chapter8.addCell(
        getSubChapterText(
            "Sõiduki võtmete mittetagastamisel või kaotamisel tasub Rentnik Rendileandja kahju vastavalt uue "
                + "võtme soetamise ning tarkvara seadistamise arvele, mida üldjuhul väljastab autoesindus."));
    chapter8.addCell(getSubChapterNumber("8.9"));
    chapter8.addCell(
        getSubChapterText(
            "Rentniku poolt sõiduki hävitamisel tasub Rentnik Rendileandjale leppetrahvi sõiduki soetusmaksumuse ulatuses."));
    chapter8.addCell(getSubChapterNumber("8.10"));
    chapter8.addCell(
        getSubChapterText(
            "Rentnik on kohustatud lähtuma p.6.1.6/c - ning tasuma vara kasutamise käigus põhjustatud seaduserikkumiste "
                + "korral kõik trahvid ja nõuded ning leppetrahvid vastavalt seaduses sätestatud korrale ning Parkimistrahvid "
                + "kaasa arvatud nendega seotud kulud, millest Rentniku poolt Rendileandjat ei ole teavitatud ega tasutud 7 "
                + "päeva jooksul, nõutakse hiljem Rentnikult sisse kolmekordselt."));
    chapter8.addCell(getSubChapterNumber("8.11"));
    chapter8.addCell(
        getSubChapterText(
            "Kui Rentnik osaleb Rendisõidukiga liiklusõnnetuses, mille tõttu Rendileandja kindlustusriski koefitsent suureneb, tasub Rentnik ühekordset leppetrahvi 300.-EUR."));
    chapter8.addCell(getSubChapterNumber("8.12"));
    chapter8.addCell(
        getSubChapterText(
            "Rentniku süül liiklusõnnetuse korral hüvitab Rentnik Rendileandjale tekitatud kahju täies mahus."));
    chapter8.addCell(getSubChapterNumber("8.13"));
    chapter8.addCell(
        getSubChapterText(
            "Selguse huvides peab ettevõte tagama, et kõik sõidukid on kindlustatud kohustusliku mootorsõidukite "
                + "kasutamise tsiviilvastutuskindlustusega, mis vastab Eesti liikluskindlustuse seadusele või samalaadsele "
                + "kohustuslikku liikluskindlustust reguleerivale muu riigi õigusaktile. Ettevõte võib, aga ei ole kohustatud "
                + "pakkuma sõidukitele täiendavat kindlustust (nt vabatahtlikku liikluskindlustust KASKO). Rentnikul on õigus "
                + "vormistada vabatahtlikku KASKO autokindlustust enda nimelt."));
    pdfDocument.add(chapter8);

    final var chapter9 = getChapterTable();
    chapter9.addCell(getChapterNumber("IX"));
    chapter9.addCell(getChapterSummary("KAHJU HINDAMINE. KAHJUKÄSITLUS"));
    chapter9.addCell(getSubChapterNumber("9.1"));
    chapter9.addCell(
        getSubChapterText(
            "Kui Rendileandja kannab kahju (välja arvatud juhul, kui kahjusumma sisaldub poolte vahel eelnevalt "
                + "kokkulepitud leppetrahvis (trahvides), mille summad on märgitud hinnakirjas ja või muus kokkuleppes),"
                + " määratakse Rendileandja poolt kantud kahju(de) summa kahjuhindaja ja/või muude teenuseosutajate kaasamise teel."));
    chapter9.addCell(getSubChapterNumber("9.2"));
    chapter9.addCell(
        getSubChapterText(
            "Sõidukile tekitatud kahju ja Rendileandja poolt kantud kahju(d) määratakse kindlaks vastavalt "
                + "sõidukite ja muu vara hindamise ja väärtuse määramise metoodikale, kahju hindamise metoodikale ja "
                + "eeskirjadele, mida kahjukäsitluse ekspert peab vastavalt Eesti Vabariigis kehtivatele õigusaktidele sõiduki kahju hindamisel järgima."));
    chapter9.addCell(getSubChapterNumber("9.3"));
    chapter9.addCell(
        getSubChapterText(
            "Rentnik võib 7 (seitsme) päeva jooksul esitada oma põhjendatud vastuväited Rendileandja või Rendileandja poolt kaasatud "
                + "kahjukäsitluse eksperdi poolt teostatud kahju hindamisele, esitades Rentniku poolt kaasatud sõltumatu sertifitseeritud "
                + "(litsentseeritud) hindaja kahju hindamise ja väärtuse hindamise aruande, mis vastab sellise hindamise ja dokumentide "
                + "suhtes kohaldatavatele õigusnõuetele (edaspidi nimetatud „alternatiivne kahjuaruanne“). Rentniku esitatud alternatiivset "
                + "kahjuaruannet ja muid Rentniku poolt Rendileandjale esitatud dokumente hinnatakse koos muu Rendileandja ja Rendileandja "
                + "poolt kaasatud kahjukäsitluse eksperdi ja muude teenuseosutajate poolt kogutud ja koostatud teabega. Kui pooltel on "
                + "kahjusumma osas mistahes lahkarvamusi, esitab küsimuses lõpliku järelduse Rendileandja poolt kaasatud kahjukäsitluse "
                + "ekspert, kelle järeldused on Rendileandjale ja Rentnikule kohustuslikud. Rentnik katab kõik alternatiivse kahjuaruande ja "
                + "Rentniku või kolmandate isikute poolt palgatud sõltumatu kahjuhindaja tööga seotud kulud."));
    chapter9.addCell(getSubChapterNumber("9.4"));
    chapter9.addCell(
        getSubChapterText(
            "Rentnik katab Rendileandja poolt kantud kahju hindamise, korrigeerimise ja haldamise kulud, samuti kõik alternatiivse või"
                + " täiendava uurimise või kahju hindamisega seotud kulud."));
    chapter9.addCell(getSubChapterNumber("9.5"));
    chapter9.addCell(
        getSubChapterText(
            "Rentnik kannab kõik riigi poolt liikluseeskirjade rikkumise eest määratud trahvid isegi juhul, kui sõidukit ei juhtinud Rentnik."));
    chapter9.addCell(getSubChapterNumber("9.6"));
    chapter9.addCell(
        getSubChapterText(
            "Tingimustes sätestatud juhtudel katab Rentnik ka Rendileandja poolt kantud kulud seoses Rentnik poolt põhjustatud kahju "
                + "või võlgnevuse haldamisega, välja arvatud juhul, kui sellised kulud katab juba hinnakirjas märgitud trahvisumma."));
    pdfDocument.add(chapter9);

    final var chapter10 = getChapterTable();
    chapter10.addCell(getChapterNumber("X"));
    chapter10.addCell(getChapterSummary("VÄÄRTEOTRAHVID, MAKSUD JA TASUD"));
    chapter10.addCell(getSubChapterNumber("10.1"));
    chapter10.addCell(
        getSubChapterText(
            "Kõik haldus- või muud liiki trahvid, maksud, tasud, muud tasumisele kuuluvad summad, mis tulenevad sõiduki ebaõigest "
                + "ja (või) ebaseaduslikust kasutamisest või õigusaktide rikkumisest Rentnik poolt, kannab Rentnik. Juhul, kui "
                + "haldus- või muud liiki trahvid, maksud, tasud, muud tasumisele kuuluvad summad nõutakse sisse Rendileandjalt, "
                + "on Rendileandjal regressiõigus, et need summad Rentnikult täies ulatuses automaatselt kätte saada ja tagasi nõuda."
                + " Pärast politseilt ja teistelt pädevatelt asutustelt ning juriidilistelt isikutelt liiklusrikkumiste või muude"
                + " rikkumiste, päringute või taotluste kohta teabe saamist annab ettevõte nendele teavet konkreetse Rentnik kui "
                + "isiku kohta, kes kasutas vastavat sõidukit konkreetsel teenuste kasutamise ajal."));
    pdfDocument.add(chapter10);

    final var chapter11 = getChapterTable();
    chapter11.addCell(getChapterNumber("XI"));
    chapter11.addCell(getChapterSummary("RENDILEANDJA VASTUTUS"));
    chapter11.addCell(getSubChapterNumber("11.1"));
    chapter11.addCell(
        getSubChapterText(
            "Rendileandja vastutab käesolevates tingimustes sätestatud kohustuste täitmise eest ja "
                + "hüvitab Rentnikule Rendileandja kohustuste mittenõuetekohase täitmise tõttu tekkinud kahju üksnes juhul, kui kahju on tekkinud Rendileandja süül."));
    chapter11.addCell(getSubChapterNumber("11.2"));
    chapter11.addCell(
        getSubChapterText(
            "Ilma et see piiraks ülaltoodud sätteid, ei vastuta Rendileandja kohaldatava seadusega lubatud ulatuses järgmise eest:"));
    chapter11.addCell(getSubChapterNumber("11.2.1"));
    chapter11.addCell(
        getSubChapterText(
            "kahju, mida Rentnik kandis hilinemise (nt teatud kohta saabumisel hilinemine jms), "
                + "teatud kuupäeva või kellaaja unustamise tõttu seoses renditeenuste kasutamisega või renditeenuste kasutamise võimatuse tõttu;"));
    chapter11.addCell(getSubChapterNumber("11.2.2"));
    chapter11.addCell(
        getSubChapterText(
            "kahju, mida Rentnik põhjustas renditeenuste kasutamisega kolmandatele isikutele või nende varale;"));
    chapter11.addCell(getSubChapterNumber("11.2.3"));
    chapter11.addCell(
        getSubChapterText("kahju Rentniku varale, tervisele või elule renditeenuste kasutamisel;"));
    chapter11.addCell(getSubChapterNumber("11.2.4"));
    chapter11.addCell(
        getSubChapterText(
            "saamata jäänud tulu, sissetulek, äritegevus, kokkulepete või lepingute sõlmimise võimalus, "
                + "tarkvara, andmete või teabe kasutamise võimaluse kahjustumine või kaotsiminek, maine kaotamine või kahjustumine;"));
    chapter11.addCell(getSubChapterNumber("11.2.5"));
    chapter11.addCell(
        getSubChapterText(
            "kahju, mida Rentnik kandis seetõttu, et ei saanud sõidukit liiklusõnnetuse tõttu või muudel Rendileandjast sõltumatutel põhjustel kasutada;"));
    pdfDocument.add(chapter11);

    final var chapter12 = getChapterTable();
    chapter12.addCell(getChapterNumber("XII"));
    chapter12.addCell(getChapterSummary("TEENUSTE HIND. LISATASUD. MAKSETINGIMUSED"));
    chapter12.addCell(getSubChapterNumber("12.1"));
    chapter12.addCell(
        getSubChapterText(
            "Rendiauto lepinguline nädala renditasu suurus lepitakse kokku üleandmise-vastuvõtmise hetkel ja "
                + "sõltub üleantava auto omadustest ja määratakse eraldi auto üleandmise-vastuvõtmise aktis."));
    chapter12.addCell(getSubChapterNumber("12.2"));
    chapter12.addCell(
        getSubChapterText(
            "Üleandmise-vastuvõtmise aktis määratud renditasu on aktuaalne vaid täisrendinädala eest tasumise puhul."));
    chapter12.addCell(getSubChapterNumber("12.3"));
    chapter12.addCell(
        getSubChapterText(
            "Täisrendinädalat peetakse ajaperioodi iga kalendrinädala Esmaspäeva kella 10:00'st kuni järgneva kalendrinädala "
                + "kella 10:00'ni - nimelt seitse päeva, millest on Pühapäev tasuta ja võib olla kasutatud puhkepäevana."
                + " Iga osalise autorendinädala ööpäeva maksumus, mida peetakse iga kalendripäeva kella 10:00'st kuni"
                + " järgneva kalendripäeva kella 10:00'ni ajaperioodi, on võrdne täisrendinädala rendimaksumusest kuuendikuga."));
    chapter12.addCell(getSubChapterNumber("12.4"));
    chapter12.addCell(
        getSubChapterText(
            "Rentnik kohustub maksma tasu rendiperioodi eest. Renditasu ja muu rendiauto kasutamisest tulenevad kohustused "
                + "hilinenud tasumise puhul on rentnik kohustatud tasuma viivist 0,1% kalendripäevas tasumata summalt."));
    chapter12.addCell(getSubChapterNumber("12.5"));
    chapter12.addCell(
        getSubChapterText(
            "Rendileandjal on õigus omal äranägemisel määrata Rentnikule renditeenuste kasutamiseks maksimaalne võlalimiit."
                + " Rendiandjal on ainuõigus ühepoolselt omal äranägemisel seda limiiti igal ajal muuta, tühistada, "
                + "vähendada või suurendada teavitades sellest Rentniku."));
    chapter12.addCell(getSubChapterNumber("12.6"));
    chapter12.addCell(
        getSubChapterText(
            "Iganädalased rendimaksed kujunevad lepingu ja selles sätestatud maksetingimuste ning sõiduki üleandmise-vastuvõtmise akti alusel."
                + " (Auto rendileping taksoteenuste kasutamiseks ja äritegevuseks). Üürnikule väljastatud arved on"
                + " informatiivsed ja kujutavad endast aruannet möödunud perioodi kohta."));
    chapter12.addCell(getSubChapterNumber("12.7"));
    chapter12.addCell(
        getSubChapterText(
            "Rendileandja väljastab arve möödunud perioodi/nädala eest võttes arvesse Rentniku täidetud ja/või "
                + "täitmata jäetud kohustused viivitusega 2 nädalad. Arve annab informatsiooni täidetud ja või täitmata jäetud lepinguliste kohustuse kohta."));
    chapter12.addCell(getSubChapterNumber("12.8"));
    chapter12.addCell(
        getSubChapterText(
            "Rendileandja väljastab ja esitab Rentnikule arved kõikide hinnakirjas märgitud trahvide, "
                + "lisatasude ja muude summade eest vastavalt õigusaktides sätestatud korrale."));
    chapter12.addCell(getSubChapterNumber("12.9"));
    chapter12.addCell(
        getSubChapterText(
            "Rendileandja poolt väljastatud arvete saamisel peab Rentnik 3 (kolme) päeva jooksul kontrollima, "
                + "et arved on õiged ja mittevastavuste avastamisel ettevõttest teavitama. Rentnik peab "
                + "esitama kõik arvel toodud teabega seotud nõuded 3 (kolme) päeva jooksul pärast arve saamist."
                + " Kui Rentnik ei esita mistahesnõudeid ülalnimetatud tähtaja jooksul, loetakse, et Rentnik aktsepteerib väljastatud arve tingimusteta."));
    chapter12.addCell(getSubChapterNumber("12.10"));
    chapter12.addCell(
        getSubChapterText(
            "Kui kliendile osutatud teenuste eest on kõikidel nimetatud perioodidel täielikult tasutud, on Rentnikule saadetud teates märgitud, "
                + "et tema poolt tasumisele kuuluv summa on 0,00 EUR. Muudel juhtudel tuleb Üürnikule saadetavas teates näidata "
                + "tema poolt tasumisele kuuluva teenuste maksumuse jääk koos võimalike viivistega."));
    chapter12.addCell(getSubChapterNumber("12.11"));
    chapter12.addCell(
        getSubChapterText(
            "Kui Rentnik ei tasu osutatud teenuste eest õigeaegselt ega ei tee seda Rendileandja kehtestatud mõistliku lisatähtaja jooksul,"
                + " on Rendileandjal õigus volitada inkassofirmat võlgnevuse sissenõudmiseks või oma nõudeõiguse loovutamiseks "
                + "Rendileandja vastu. Ettevõte võib võlgade sissenõudmise, haldamise, hindamise ja kahjude haldamise eesmärgil "
                + "või muul sarnasel eesmärgil edastada Rendileandja poolt salvestatud Rentniku isikuandmeid valitsusasutustele "
                + "(sh kohtutele) ja/või kohtutäituritele, teistele isikutele ja asutustele, kellel on selleks õigus. selliste andmete vastuvõtmiseks ja töötlemiseks."));
    chapter12.addCell(getSubChapterNumber("12.12"));
    chapter12.addCell(
        getSubChapterText(
            "Kõik Rentnik poolt käesolevate tingimuste alusel Rendileandjale tasumisele kuuluvad summad tasutakse, debiteeritakse ja tasaarvestatakse järgmises järjekorras:"));
    chapter12.addCell(getSubChapterNumber("12.12.1"));
    chapter12.addCell(getSubChapterText("trahvid ja viivised;"));
    chapter12.addCell(getSubChapterNumber("12.12.2"));
    chapter12.addCell(getSubChapterText("muud Rendileandjale makstavad tasud, maksud ja maksed;"));
    chapter12.addCell(getSubChapterNumber("12.12.3"));
    chapter12.addCell(getSubChapterText("võlgnevus osutatud teenuste eest;"));
    chapter12.addCell(getSubChapterNumber("12.12.4"));
    chapter12.addCell(getSubChapterText("jooksev renditasu."));
    pdfDocument.add(chapter12);

    final var chapter13 = getChapterTable();
    chapter13.addCell(getChapterNumber("XIII"));
    chapter13.addCell(getChapterSummary("LEPINGU LÕPETAMINE"));
    chapter13.addCell(getSubChapterNumber("13.1"));
    chapter13.addCell(
        getSubChapterText("Rentnikul on õigus leping igal ajal mistahes põhjusel lõpetada. "));
    chapter13.addCell(getSubChapterNumber("13.2"));
    chapter13.addCell(
        getSubChapterText(
            "Lepingu lõpetamisel on Rentnik kohustatud tagastama renditud auto kirjaliku üleandmise-vastuvõtmise akti alusel."));
    chapter13.addCell(getSubChapterNumber("13.3"));
    chapter13.addCell(
        getSubChapterText(
            "Auto tagastamisel teostavad Rentnik ja Rendileandja tagastava vara ülevaatuse ning fikseerivad "
                + "auto seisundi ning võimalikud puudused ja erinevused võrreldes algse vara üleandmise aktiga."));
    chapter13.addCell(getSubChapterNumber("13.4"));
    chapter13.addCell(
        getSubChapterText(
            "Rendileandjal on õigus lõpetada Rentnikuga sõlmitud lepingu samal päeval järgmistel juhtudel:"));
    chapter13.addCell(getSubChapterNumber("13.4.1"));
    chapter13.addCell(getSubChapterText("sõidukit juhtis isik, kellel puudus selleks õigus;"));
    chapter13.addCell(getSubChapterNumber("13.4.2"));
    chapter13.addCell(
        getSubChapterText(
            "sõidukit kasutatakse eesmärkidel, milleks see ei ole mõeldud või ette nähtud; "));
    chapter13.addCell(getSubChapterNumber("13.4.3"));
    chapter13.addCell(
        getSubChapterText(
            "juht juhtis sõidukit alkoholijoobes (üle 0,00 promilli) või narkootiliste ainete või "
                + "muude vaimset seisundit mõjutavate ainete mõju all (ka juhul, kui Rentnik tarvitas alkoholi "
                + "või muid joovastavaid aineid pärast liiklusõnnetust enne liiklusõnnetuse asjaolude "
                + "tuvastamist või vältis vere alkoholisisalduse testi või joobetesti tegemist);"));
    chapter13.addCell(getSubChapterNumber("13.4.4"));
    chapter13.addCell(
        getSubChapterText(
            "Rentnik põhjustas sõidukile kahju tahtlikult või raske hooletuse tõttu "
                + "(nt suure kiiruse ületamise, ohtliku või hoolimatu sõitmise, muu liikluseeskirjade raske rikkumise tõttu); "));
    chapter13.addCell(getSubChapterNumber("13.4.5"));
    chapter13.addCell(getSubChapterText("hoolimatu ja ohtliku sõitmise korral;"));
    chapter13.addCell(getSubChapterNumber("13.4.6"));
    chapter13.addCell(getSubChapterText("Rentnik lahkub õnnetuspaigast;"));
    chapter13.addCell(getSubChapterNumber("13.4.7"));
    chapter13.addCell(
        getSubChapterText(
            "Rentnik ei täida liikluspolitsei või muude pädevate asutuste juhiseid;"));
    chapter13.addCell(getSubChapterNumber("13.4.8"));
    chapter13.addCell(getSubChapterText("Rentnik kasutab sõidukit kuriteo toimepanemiseks;"));
    chapter13.addCell(getSubChapterNumber("13.4.9"));
    chapter13.addCell(
        getSubChapterText(
            "Rentnik ei teavita liiklusõnnetusest ettevõtet, politseid, tuletõrjet ja/või muud pädevat asutust või teenistust;"));
    chapter13.addCell(getSubChapterNumber("13.4.10"));
    chapter13.addCell(
        getSubChapterText(
            "Rentnik rikub lepingulisi renditasumise kohustusi ning ületab tema võlg 240 eurot. Rendileandjal koos mõjus "
                + "rendilepingu p. 3.4 on õigus lepingu lõpetada ning auto viivitamatult enda valduse saada. "));
    chapter13.addCell(getSubChapterNumber("13.4.11"));
    chapter13.addCell(
        getSubChapterText(
            "Rendileandja võib lepingu VÕS § 316 alusel erakorraliselt üles öelda, kui ta on andnud Rentnikule kirjalikku "
                + "taasesitamist võimaldavas vormis vähemalt 14-päevase täiendava tähtaja, hoiatades, "
                + "et antud tähtaja jooksul võlgnevuse tasumata jätmise korral ütleb ta lepingu üles."));
    chapter13.addCell(getSubChapterNumber("13.4.12"));
    chapter13.addCell(
        getSubChapterText(
            "Juhul, kui Rentnik ei tasu võlgnevuse täiendava tähtaja jooksul, loeb üürileandja pärast selle möödumist lepingu ülesöelduks."));
    chapter13.addCell(getSubChapterNumber("13.4.13"));
    chapter13.addCell(
        getSubChapterText(
            "Juhul, kui Rentnik viivitab tasumisega, võib üürileandja üürilepingu VÕS § 316 alusel üles öelda"
                + " täiendavat tähtaega andmata, kui rentnik on võlgnevusele eelnenud aasta jooksul vähemalt "
                + "kahel korral täitnud kohustused alles täiendava tähtaja jooksul või pärast seda."));
    chapter13.addCell(getSubChapterNumber("13.4.14"));
    chapter13.addCell(
        getSubChapterText(
            "Rentnik rikub raskelt käesolevaid tingimusi ja/või jätkab käesolevate tingimuste rikkumist ja/või "
                + "esinevad muud objektiivsed asjaolud, mille tõttu kujutab Rentnik Rendileandja arvates ohtu teistele "
                + "Rentnikutele, klientidele, ühiskonnale, Rendileandjale, sõidukile;"));
    chapter13.addCell(getSubChapterNumber("13.4.15"));
    chapter13.addCell(getSubChapterText("Õigusaktides sätestatud tingimustel."));
    chapter13.addCell(getSubChapterNumber("13.5"));
    chapter13.addCell(getSubChapterText("Üürileping lõpeb Rentniku surmaga."));
    pdfDocument.add(chapter13);

    final var chapter14 = getChapterTable();
    chapter14.addCell(getChapterNumber("XIV"));
    chapter14.addCell(getChapterSummary("LÕPPSÄTTED"));
    chapter14.addCell(getSubChapterNumber("14.1"));
    chapter14.addCell(
        getSubChapterText(
            "Rendileandjal on õigus käesolevad tingimused ühepoolselt muuta, teavitades sellest Rentnikku"
                + " lepingus märgitud e-posti teel. Tingimuste muudatused jõustuvad 5 (viie) päeva "
                + "möödumisel nendest Rentnikule teatavaks tegemisest. Kui Rentnik jätkab üürilepingujärgsete "
                + "teenuste saamist vastavalt muutunud tingimustele, loetakse üürnik muudatustega nõustunuks."));
    chapter14.addCell(getSubChapterNumber("14.2"));
    chapter14.addCell(
        getSubChapterText(
            "Käesolevate tingimuste tähenduses loetakse, et on Rentnik nõuetekohaselt kirjalikult "
                + "teavitatud Lepingus märgitud e-posti aadressil Rentnikule e-kirja saatmisele järgneval päeval."));
    chapter14.addCell(getSubChapterNumber("14.3"));
    chapter14.addCell(
        getSubChapterText(
            "Rendileandjal on õigus kõik või osa käesolevatest tingimustest ja/või lepingust tulenevaid õigusi ja"
                + " kohustusi ühepoolselt üle anda kolmandale isikule, informeerides sellest Rentnikku eelnevalt kirjalikult, saates üldteate e-posti teel."));
    chapter14.addCell(getSubChapterNumber("14.4"));
    chapter14.addCell(
        getSubChapterText(
            "Kõik pooltevahelised vaidlused ja erimeelsused lahendatakse Eesti Vabariigi pädevas kohtus."));
    chapter14.addCell(getSubChapterNumber("14.5"));
    chapter14.addCell(
        getSubChapterText(
            "Käesolevaid tingimusi tõlgendatakse ja rakendatakse kooskõlas Eesti Vabariigi seadustega."));
    chapter14.addCell(getSubChapterNumber("14.6"));
    chapter14.addCell(
        getSubChapterText(
            "Rentnik võib käesolevaid tingimusi puudutavate küsimustega pöörduda Rendileandja poole, kasutades lepingus märgitud andmeid."));
    pdfDocument.add(chapter14);

    final var chapter15 = getChapterTable();
    chapter15.addCell(getChapterSummary("Lisa"));
    final var body15acell2 =
        new Cell(new Paragraph("nr 1.  Trahvid ja kahjutasud", new Font(TIMES_ROMAN, 9, BOLD)));
    body15acell2.setBorder(NO_BORDER);
    body15acell2.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell2);

    final var body15acell3 = new Cell(new Paragraph("A.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell3.setBorder(NO_BORDER);
    body15acell3.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell3);
    chapter15.addCell(
        getSubChapterText(
            "Kui sõiduk antakse Rentnikule üle seest ja väljast puhtana ja pestuna, kohustub rentnik tagastama sõiduki samas seisukorras. "
                + "Pesemata sõiduki tagastamisel tuleb Rentnikul tasuda trahvi välispesu vajaduse eest 60.- EUR, salongi puhastamise vajaduse eest 180.- EUR "
                + "ja vajadusel pakiruumi puhastamise vajaduse eest 40.- EUR."));

    final var body15acell5 = new Cell(new Paragraph("B.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell5.setBorder(NO_BORDER);
    body15acell5.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell5);

    final var body15acell6 =
        new Cell(
            new Paragraph(
                "Juhul kui sõiduk vajab keemilist puhastust, tuleb Rentnikul tasuda trahvi keemilise puhastuse vajaduse eest 360.- EUR.",
                new Font(TIMES_ROMAN, 8, NORMAL)));
    body15acell6.setBorder(NO_BORDER);
    body15acell6.setHorizontalAlignment(JUSTIFIED);
    chapter15.addCell(body15acell6);

    final var body15acell7 = new Cell(new Paragraph("C.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell7.setBorder(NO_BORDER);
    body15acell7.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell7);
    chapter15.addCell(getSubChapterText("sõidukis suitsetamise eest 500 EUR"));
    final var body15acell9 = new Cell(new Paragraph("D.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell9.setBorder(NO_BORDER);
    body15acell9.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell9);
    chapter15.addCell(
        getSubChapterText(
            "sõiduki juhtimise eest alkoholijoobes (üle 0,00 promilli), narkootiliste ja muude psühhotroopsete ainete mõju"
                + " all (või kui tarvitasite alkoholi või muid joovastavaid aineid pärast liiklusõnnetust, enne kui õnnetuse "
                + "asjaolud välja selgitati, või vältisite vere alkoholisisalduse mõõtmist või joobetesti tegemist "
                + "(vere alkoholisisaldust ja joovet mõistetakse nii, nagu on määratletud õigusaktides). "
                + "Viidatud summas trahvi alkoholijoobes (üle 0,00 promillise), narkootiliste ja muude psühhotroopsete ainete mõju "
                + "all sõiduki juhtimise eest peate meile tasuma ka nendel juhtudel, kui võõrandasite Sõiduki või muul "
                + "viisil võimaldasite teisel isikul seda juhtida, kui ta oli alkoholijoobes (üle 0,00 promilli), narkootiliste"
                + " ja muude psühhotroopsete ainete mõju all, või kui see isik vältis vere alkoholisisalduse mõõtmist või joobetesti tegemist: 2000 EUR "));
    final var body15acell11 = new Cell(new Paragraph("E.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell11.setBorder(NO_BORDER);
    body15acell11.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell11);
    chapter15.addCell(
        getSubChapterText(
            "Ebakvaliteetsest kütusest tekkinud kahjud kannab Rentnik vastavalt arvele, mida väljastab teenuse osutaja remondi teostamiseks."));
    final var body15acell13 = new Cell(new Paragraph("F.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell13.setBorder(NO_BORDER);
    body15acell13.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell13);
    chapter15.addCell(
        getSubChapterText(
            "Sõiduki dokumentide mittetagastamisel või puuduliku varustusega sõiduki tagastamisel tasub Rentnik Rendileandjale leppetrahvi"
                + " 250.-EUR iga kaotatud või puuduva dokumendi või eseme kohta."));
    final var body15acell15 = new Cell(new Paragraph("G.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell15.setBorder(NO_BORDER);
    body15acell15.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell15);
    chapter15.addCell(
        getSubChapterText(
            "Võtmete kaotamise eest kannab Rentnik trahvi vastavalt esinduse poolt väljastatud arvele, mis sisaldab uue võtme "
                + "ja auto signalisatsiooni ning keskluku ümberprogrammeerimist ja seadistamist."));
    final var body15acell17 = new Cell(new Paragraph("H.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell17.setBorder(NO_BORDER);
    body15acell17.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell17);
    chapter15.addCell(
        getSubChapterText(
            "Rentniku poolt ja süül sõiduki hävitamisel tasub Rentnik Rendileandjale leppetrahvi sõiduki turuväärtuse ulatuses."));
    final var body15acell19 = new Cell(new Paragraph("I.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell19.setBorder(NO_BORDER);
    body15acell19.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell19);
    chapter15.addCell(
        getSubChapterText(
            "Kui aga sõiduk saab kahjustada liiklusõnnetuses, mille põhjustab Rentnik (või muu isik, kellele Rentnik võimaldas sõidukit kasutada) ebakaines olekus, spordivõistlustel osaledes või muul viisil Tingimusi rikkudes, peab Rentnik tekitatud kahjud hüvitama täies ulatuses."));
    final var body15acell21 = new Cell(new Paragraph("J.", new Font(TIMES_ROMAN, 9, NORMAL)));
    body15acell21.setBorder(NO_BORDER);
    body15acell21.setHorizontalAlignment(LEFT);
    chapter15.addCell(body15acell21);
    chapter15.addCell(
        getSubChapterText(
            "Kui Rendileandjale tagastatud sõiduk vajab remonti, kannab Rentnik iga remondipäeva eest lepingus kokkulepitud rendipäeva hinnale lisaks ka remondikulud vastavalt üleandmise-vastuvõtmise aktis märgitud nädala hinnale ning remondiarvele."));
    pdfDocument.add(chapter15);

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
