package ee.qrental.ui.controller.link;

import ee.qrental.link.api.in.query.GetLinkQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrental.ui.controller.ControllerUtils.LINK_ROOT_PATH;


@Controller
@RequestMapping(LINK_ROOT_PATH)
@AllArgsConstructor
public class LinkQueryController {

  private final GetLinkQuery linkQuery;

  @GetMapping
  public String getLinkView(final Model model) {
    model.addAttribute("links", linkQuery.getAll());

    return "links";
  }
}
