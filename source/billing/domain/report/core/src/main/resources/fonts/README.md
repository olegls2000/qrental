# Шрифты для PDF генерации

## Как добавить сторонний шрифт:

### 1. Подготовка файлов шрифтов
- Поместите файлы шрифтов (.ttf, .otf) в эту папку
- Рекомендуемые форматы: TTF (TrueType Font) или OTF (OpenType Font)
- Убедитесь, что шрифт поддерживает кириллицу

### 2. Примеры файлов шрифтов:
- `arial.ttf` - обычный Arial
- `arial-bold.ttf` - жирный Arial  
- `arial-italic.ttf` - курсивный Arial
- `times-new-roman.ttf` - Times New Roman
- `calibri.ttf` - Calibri

### 3. Использование в коде:

```java
// Импорт
import ee.qrent.billing.report.core.service.pdf.font.PdfFontManager;

// Создание шрифтов
Font normalFont = PdfFontManager.createFont(12);
Font boldFont = PdfFontManager.createBoldFont(12);
Font redBoldFont = PdfFontManager.createBoldFont(12, Color.RED);
Font underlinedFont = PdfFontManager.createBoldUnderlinedFont(12);
```

### 4. Где скачать шрифты:
- [Google Fonts](https://fonts.google.com/) - бесплатные шрифты
- [Font Squirrel](https://www.fontsquirrel.com/) - бесплатные шрифты
- [DaFont](https://www.dafont.com/) - бесплатные шрифты

### 5. Важные замечания:
- Шрифты встраиваются в PDF файл, поэтому размер PDF может увеличиться
- Убедитесь, что у вас есть права на использование шрифта
- Для кириллицы используйте шрифты с поддержкой Unicode

### 6. Альтернативный способ (если шрифт уже установлен в системе):
```java
// Регистрация системного шрифта
FontFactory.register("C:/Windows/Fonts/arial.ttf", "ArialCustom");
Font customFont = FontFactory.getFont("ArialCustom", 12, Font.BOLD);
```

