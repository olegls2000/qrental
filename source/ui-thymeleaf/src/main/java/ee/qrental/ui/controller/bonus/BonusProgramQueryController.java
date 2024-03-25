package ee.qrental.ui.controller.bonus;


import ee.qrental.bonus.api.in.query.GetBonusProgramQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.util.ControllerUtils.BONUS_PROGRAM_PATH;


@Controller
@RequestMapping(BONUS_PROGRAM_PATH)
@AllArgsConstructor
public class BonusProgramQueryController {

  private final GetBonusProgramQuery bonusProgramQuery;

  @GetMapping
  public String getBonusProgramView(final Model model) {
    model.addAttribute("bonusPrograms", bonusProgramQuery.getAll());

    return "bonusPrograms";
  }
}
