package ee.qrental.ui.controller.user;

import static ee.qrental.ui.controller.util.ControllerUtils.USER_ROOT_PATH;

import ee.qrental.user.api.in.query.GetUserAccountQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(USER_ROOT_PATH)
@AllArgsConstructor
public class UserAccountQueryController {

  private final GetUserAccountQuery userAccountQuery;

  @GetMapping
  public String getUserAccountView(final Model model) {
    model.addAttribute("users", userAccountQuery.getAll());

    return "users";
  }
}
