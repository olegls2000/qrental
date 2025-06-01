package ee.qrent.billing.ui.controller;

import org.springframework.ui.Model;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

public abstract class AbstractUseCaseController<A, U, D> {

  protected void addAddRequestToModel(final Model model, final A addRequest) {
    model.addAttribute(ADD_REQUEST_ATTRIBUTE, addRequest);
  }

  protected void addUpdateRequestToModel(final Model model, final U updateRequest) {
    model.addAttribute(UPDATE_REQUEST_ATTRIBUTE, updateRequest);
  }

  protected void addDeleteRequestToModel(final Model model, final D deleteRequest) {
    model.addAttribute(DELETE_REQUEST_ATTRIBUTE, deleteRequest);
  }

  protected void addObjectInfoToModel(final Model model, final String objectInfo) {
    model.addAttribute(OBJECT_INFO_ATTRIBUTE, objectInfo);
  }
}
