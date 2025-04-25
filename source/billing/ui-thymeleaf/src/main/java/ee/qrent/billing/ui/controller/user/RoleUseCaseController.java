package ee.qrent.billing.ui.controller.user;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.user.api.in.query.GetRoleQuery;
import ee.qrent.billing.user.api.in.request.RoleAddRequest;
import ee.qrent.billing.user.api.in.request.RoleDeleteRequest;
import ee.qrent.billing.user.api.in.request.RoleUpdateRequest;
import ee.qrent.billing.user.api.in.usecase.RoleAddUseCase;
import ee.qrent.billing.user.api.in.usecase.RoleDeleteUseCase;
import ee.qrent.billing.user.api.in.usecase.RoleUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ROLE_ROOT_PATH)
@AllArgsConstructor
public class RoleUseCaseController {

  private final RoleAddUseCase addUseCase;
  private final RoleUpdateUseCase updateUseCase;
  private final RoleDeleteUseCase deleteUseCase;
  private final GetRoleQuery roleQuery;

  private static void addUpdateRequestToModel(Model model, final RoleUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  private static void addAddRequestToModel(Model model) {
    model.addAttribute("addRequest", new RoleAddRequest());
  }

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model);

    return "forms/addRole";
  }

  @PostMapping(value = "/add")
  public String addRole(@ModelAttribute final RoleAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addRole";
    }

    return "redirect:" + ROLE_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = roleQuery.getUpdateRequestById(id);
    addUpdateRequestToModel(model, updateRequest);

    return "forms/updateRole";
  }

  @PostMapping("/update")
  public String updateRole(final RoleUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateRole";
    }
    return "redirect:" + ROLE_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new RoleDeleteRequest(id));
    model.addAttribute("objectInfo", roleQuery.getObjectInfo(id));

    return "forms/deleteRole";
  }

  @PostMapping("/delete")
  public String deleteRole(final RoleDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteRole";
    }

    return "redirect:" + ROLE_ROOT_PATH;
  }
}
