package ee.qrent.billing.ui.controller.user;

import static ee.qrent.billing.ui.controller.ControllerUtils.ROLE_ROOT_PATH;

import ee.qrent.billing.user.api.in.query.GetRoleQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ROLE_ROOT_PATH)
@AllArgsConstructor
public class RoleQueryController {

  private final GetRoleQuery roleQuery;

  @GetMapping
  public String getUserAccountView(final Model model) {
    model.addAttribute("roles", roleQuery.getAll());

    return "roles";
  }
}
