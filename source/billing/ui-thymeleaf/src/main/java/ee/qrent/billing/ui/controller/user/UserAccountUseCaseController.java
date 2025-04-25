package ee.qrent.billing.ui.controller.user;

import static ee.qrent.billing.ui.controller.ControllerUtils.USER_ROOT_PATH;

import ee.qrent.billing.user.api.in.query.GetRoleQuery;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.request.UserAccountAddRequest;
import ee.qrent.billing.user.api.in.request.UserAccountDeleteRequest;
import ee.qrent.billing.user.api.in.request.UserAccountUpdateRequest;
import ee.qrent.billing.user.api.in.usecase.UserAccountAddUseCase;
import ee.qrent.billing.user.api.in.usecase.UserAccountDeleteUseCase;
import ee.qrent.billing.user.api.in.usecase.UserAccountUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(USER_ROOT_PATH)
@AllArgsConstructor
public class UserAccountUseCaseController {

  private final UserAccountAddUseCase addUseCase;
  private final UserAccountUpdateUseCase updateUseCase;
  private final UserAccountDeleteUseCase deleteUseCase;
  private final GetUserAccountQuery userAccountQuery;
  private final GetRoleQuery roleQuery;

  private static void addUpdateRequestToModel(final Model model,
                                              final UserAccountUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  private static void addAddRequestToModel(final Model model) {
    model.addAttribute("addRequest", new UserAccountAddRequest());
  }

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model);
    model.addAttribute("roles", roleQuery.getAll());

    return "forms/addUser";
  }
  
  @PostMapping(value = "/add")
  public String addDriver(@ModelAttribute final UserAccountAddRequest addRequest,
                          final Model model) {
    addUseCase.add(addRequest);

    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addUser";
    }

    return "redirect:" + USER_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = userAccountQuery.getUpdateRequestById(id);
    model.addAttribute("roles", roleQuery.getAll());
    addUpdateRequestToModel(model, updateRequest);

    return "forms/updateUser";
  }

  @PostMapping("/update")
  public String updateDriver(final UserAccountUpdateRequest updateRequest,
           final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateUser";
    }
    return "redirect:" + USER_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new UserAccountDeleteRequest(id));
    model.addAttribute("objectInfo", userAccountQuery.getObjectInfo(id));

    return "forms/deleteUser";
  }

  @PostMapping("/delete")
  public String deleteDriver(final UserAccountDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + USER_ROOT_PATH;
  }
}
