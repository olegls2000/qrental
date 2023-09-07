package ee.qrental.ui.controller.user;

import static ee.qrental.ui.controller.util.ControllerUtils.ROLE_ROOT_PATH;

import ee.qrental.user.api.in.query.GetRoleQuery;
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
