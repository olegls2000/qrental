package ee.qrental.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QStringUtils {
    public static String contract(final String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength - 2) + "..";
        }
        return value;
    }

}
