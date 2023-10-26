package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.CAR_LINK_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CAR_LINK_ROOT_PATH)
@AllArgsConstructor
public class CarLinkQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetCarLinkQuery linkQuery;

  @GetMapping
  public String getLinkView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var links = linkQuery.getAll();
    model.addAttribute("links", links);

    return "carLinks";
  }
}
