package ee.qrental.ui.controller.constant;

import static ee.qrental.ui.controller.util.ControllerUtils.WEEK_ROOT_PATH;

import ee.qrental.constant.api.in.query.qweek.GetQWeekQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(WEEK_ROOT_PATH)
@AllArgsConstructor
public class QWeekQueryController {

  private final GetQWeekQuery qWeekQuery;

  @GetMapping
  public String getConstantView(final Model model) {
    model.addAttribute("weeks", qWeekQuery.getAll());

    return "constants";
  }
}
