package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.ControllerUtils.LINK_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.link.api.in.request.LinkAddRequest;
import ee.qrental.link.api.in.request.LinkDeleteRequest;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.usecase.LinkAddUseCase;
import ee.qrental.link.api.in.usecase.LinkDeleteUseCase;
import ee.qrental.link.api.in.usecase.LinkUpdateUseCase;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(LINK_ROOT_PATH)
@AllArgsConstructor
public class LinkUseCaseController {

  private final LinkAddUseCase addUseCase;
  private final LinkUpdateUseCase updateUseCase;
  private final LinkDeleteUseCase deleteUseCase;
  private final GetLinkQuery linkQuery;
  private final GetCarQuery carQuery;
  private final GetBalanceQuery balanceQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new LinkAddRequest());
    addDriverBalanceesListToModel(model);
    addCarListToModel(model);

    return "forms/addLink";
  }

  @PostMapping(value = "/add")
  public String addLinkLink(@ModelAttribute final LinkAddRequest linkInfo) {
    addUseCase.add(linkInfo);

    return "redirect:" + LINK_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", linkQuery.getUpdateRequestById(id));
    addDriverBalanceesListToModel(model);
    addCarListToModel(model);

    return "forms/updateLink";
  }

  @PostMapping("/update")
  public String updateLinkLink(final LinkUpdateRequest linkUpdateRequest) {
    updateUseCase.update(linkUpdateRequest);

    return "redirect:" + LINK_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new LinkDeleteRequest(id));
    model.addAttribute("objectInfo", linkQuery.getObjectInfo(id));

    return "forms/deleteLink";
  }

  @PostMapping("/delete")
  public String deleteForm(final LinkDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + LINK_ROOT_PATH;
  }

  private void addCarListToModel(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
  }

  private void addDriverBalanceesListToModel(final Model model) {
    final var drivers = balanceQuery.getAllBalanceTotalsWithDriver();
    model.addAttribute("drivers", drivers);
  }
}
