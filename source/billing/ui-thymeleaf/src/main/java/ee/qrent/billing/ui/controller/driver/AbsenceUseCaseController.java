package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;
import static java.util.Arrays.asList;

import ee.qrent.common.core.enums.AbsenceReason;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceAddRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceDeleteRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.AbsenceAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.AbsenceDeleteUseCase;
import ee.qrent.billing.contract.api.in.usecase.AbsenceUpdateUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ABSENCE_ROOT_PATH)
@AllArgsConstructor
public class AbsenceUseCaseController {

  private final AbsenceAddUseCase addUseCase;
  private final AbsenceUpdateUseCase updateUseCase;
  private final AbsenceDeleteUseCase deleteUseCase;
  private final GetAbsenceQuery absenceQuery;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form/{driverId}")
  public String addForm(final Model model, @PathVariable("driverId") long driverId) {
    final var addRequest = new AbsenceAddRequest();
    addRequest.setDriverId(driverId);
    addAddRequestToModel(addRequest, model);
    addDriverInfoToModel(driverId, model);
    addReasonsToModel(model);

    return "forms/addAbsence";
  }

  @PostMapping(value = "/add")
  public String add(@ModelAttribute final AbsenceAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      addDriverInfoToModel(addRequest.getDriverId(), model);
      addReasonsToModel(model);

      return "forms/addAbsence";
    }

    return "redirect:" + BALANCE_ROOT_PATH + "/driver/" + addRequest.getDriverId();
  }

  private void addAddRequestToModel(final AbsenceAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = absenceQuery.getUpdateRequestById(id);
    addUpdateRequestToModel(model, updateRequest);
    addDriverInfoToModel(updateRequest.getDriverId(), model);
    addReasonsToModel(model);

    return "forms/updateAbsence";
  }

  @PostMapping("/update")
  public String update(final AbsenceUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    final var driverId = updateRequest.getDriverId();
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addDriverInfoToModel(driverId, model);
      addReasonsToModel(model);

      return "forms/updateAbsence";
    }

    return "redirect:" + BALANCE_ROOT_PATH + "/driver/" + driverId;
  }

  private void addUpdateRequestToModel(
      final Model model, final AbsenceUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, Model model) {
    model.addAttribute("deleteRequest", new AbsenceDeleteRequest(id));
    model.addAttribute("objectInfo", absenceQuery.getObjectInfo(id));

    return "forms/deleteAbsence";
  }

  @PostMapping("/delete")
  public String delete(final AbsenceDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);

    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteAbsence";
    }

    return "redirect:" + ABSENCE_ROOT_PATH;
  }

  private void addDriverInfoToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  private void addReasonsToModel(final Model model) {
    model.addAttribute("reasons", asList(AbsenceReason.class.getEnumConstants()));
  }
}
