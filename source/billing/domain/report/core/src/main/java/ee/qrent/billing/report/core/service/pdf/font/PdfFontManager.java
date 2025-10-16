package ee.qrent.billing.report.core.service.pdf.font;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import java.awt.Color;
import java.io.IOException;

/**
 * Менеджер шрифтов для PDF генерации
 */
public class PdfFontManager {
    
    private static final String DEFAULT_FONT_PATH = "/fonts/arial.ttf";
    private static final String BOLD_FONT_PATH = "/fonts/arial-bold.ttf";
    private static final String ITALIC_FONT_PATH = "/fonts/arial-italic.ttf";
    
    private static BaseFont defaultBaseFont;
    private static BaseFont boldBaseFont;
    private static BaseFont italicBaseFont;
    
    static {
        try {
            // Инициализация шрифтов при загрузке класса
            defaultBaseFont = createBaseFont(DEFAULT_FONT_PATH);
            boldBaseFont = createBaseFont(BOLD_FONT_PATH);
            italicBaseFont = createBaseFont(ITALIC_FONT_PATH);
        } catch (Exception e) {
            // Fallback на системные шрифты
            try {
                defaultBaseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                boldBaseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                italicBaseFont = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            } catch (Exception ex) {
                throw new RuntimeException("Не удалось инициализировать шрифты", ex);
            }
        }
    }
    
    /**
     * Создает BaseFont из файла шрифта
     */
    private static BaseFont createBaseFont(String fontPath) throws IOException {
        return BaseFont.createFont(
            fontPath,
            BaseFont.IDENTITY_H, // Поддержка кириллицы
            BaseFont.EMBEDDED    // Встраивание шрифта в PDF
        );
    }
    
    /**
     * Создает обычный шрифт
     */
    public static Font createFont(int size) {
        return new Font(defaultBaseFont, size, Font.NORMAL);
    }
    
    /**
     * Создает обычный шрифт с цветом
     */
    public static Font createFont(int size, Color color) {
        return new Font(defaultBaseFont, size, Font.NORMAL, color);
    }
    
    /**
     * Создает жирный шрифт
     */
    public static Font createBoldFont(int size) {
        return new Font(boldBaseFont, size, Font.BOLD);
    }
    
    /**
     * Создает жирный шрифт с цветом
     */
    public static Font createBoldFont(int size, Color color) {
        return new Font(boldBaseFont, size, Font.BOLD, color);
    }
    
    /**
     * Создает курсивный шрифт
     */
    public static Font createItalicFont(int size) {
        return new Font(italicBaseFont, size, Font.ITALIC);
    }
    
    /**
     * Создает курсивный шрифт с цветом
     */
    public static Font createItalicFont(int size, Color color) {
        return new Font(italicBaseFont, size, Font.ITALIC, color);
    }
    
    /**
     * Создает жирный подчеркнутый шрифт
     */
    public static Font createBoldUnderlinedFont(int size) {
        return new Font(boldBaseFont, size, Font.BOLD | Font.UNDERLINE);
    }
    
    /**
     * Создает жирный подчеркнутый шрифт с цветом
     */
    public static Font createBoldUnderlinedFont(int size, Color color) {
        return new Font(boldBaseFont, size, Font.BOLD | Font.UNDERLINE, color);
    }
    
    /**
     * Создает шрифт с кастомным BaseFont
     */
    public static Font createCustomFont(BaseFont baseFont, int size, int style) {
        return new Font(baseFont, size, style);
    }
    
    /**
     * Создает шрифт с кастомным BaseFont и цветом
     */
    public static Font createCustomFont(BaseFont baseFont, int size, int style, Color color) {
        return new Font(baseFont, size, style, color);
    }
}
