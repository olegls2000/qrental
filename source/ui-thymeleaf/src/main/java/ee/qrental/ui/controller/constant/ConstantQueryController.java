package ee.qrental.ui.controller.constant;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.ControllerUtils.CONSTANT_ROOT_PATH;


@Controller
@RequestMapping(CONSTANT_ROOT_PATH)
@AllArgsConstructor
public class ConstantQueryController {

  private final GetConstantQuery constantQuery;

  @GetMapping
  public String getConstantView(final Model model) {
    model.addAttribute("constants", constantQuery.getAll());

    return "constants";
  }
}
