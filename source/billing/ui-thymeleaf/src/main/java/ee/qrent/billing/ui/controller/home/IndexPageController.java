package ee.qrent.billing.ui.controller.home;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.HOME_ROOT_PATH;

@Controller
@RequestMapping(HOME_ROOT_PATH)
@AllArgsConstructor
public class IndexPageController {
  private final QDateFormatter qDateFormatter;
  private final GetCarQuery carQuery;
  private final GetDriverQuery driverQuery;

  @GetMapping
  public String getFreeCarView(final Model model) {
    model.addAttribute("cars", carQuery.getAvailableCars());
    model.addAttribute("drivers", driverQuery.getDriversWithZeroMatchCountForLatestCalculation());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "index";
  }

  /*@GetMapping
  public String getCarsNoInsurance(final Model model) {
    final var carsNoInsurance = carQuery.getCarsNoInsurance();
    model.addAttribute("carsNoInsurance", carsNoInsurance);
   *//*  addCarCounts(model);
    addDateFormatter(model);*//*

    return "index";
   *//* return "carsNoInsurance";*//*
  }

  @GetMapping
  public String getFreeCars(final Model model) {
    final var freeCars = carQuery.getFreeCars();
    model.addAttribute("freeCars", freeCars);
    *//*addCarCounts(model);
    addDateFormatter(model);*//*
    return "index";
  *//*  return "freeCars";*//*
  }*/
}
