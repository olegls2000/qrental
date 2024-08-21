package ee.qrental.transaction.api.in.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionTypeConstant {
    public static final String TRANSACTION_TYPE_NAME_WEEKLY_RENT = "weekly rent";
    public static final String TRANSACTION_TYPE_NO_LABEL_FINE = "no label fine";
    public static final String TRANSACTION_TYPE_BONUS = "bonus";
    public static final String TRANSACTION_TYPE_SELF_RESPONSIBILITY = "self responsibility";
    public static final String TRANSACTION_TYPE_SELF_RESPONSIBILITY_OVERPAYMENT = "self responsibility overpayment";
}
