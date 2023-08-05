package ee.qrental.ui.controller;

import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

@UtilityClass
public class ControllerUtils {
  public static final String BALANCE_ROOT_PATH = "/balances";
  public static final String CALL_SIGN_ROOT_PATH = "/call-signs";
  public static final String CALL_SIGN_LINK_ROOT_PATH = "/call-sign-links";
  public static final String CAR_ROOT_PATH = "/cars";
  public static final String DRIVER_ROOT_PATH = "/drivers";
  public static final String FIRM_ROOT_PATH = "/firms";
  public static final String LINK_ROOT_PATH = "/links";
  public static final String CONSTANT_ROOT_PATH = "/constants";
  public static final String INVOICE_ROOT_PATH = "/invoices";
  public static final String TRANSACTION_ROOT_PATH = "/transactions";
  public static final String TRANSACTION_TYPE_ROOT_PATH = "/transaction-types";
  
  public static void setQDateFormatter(final Model model){
    model.addAttribute("qDateFormatter", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

  }
}
