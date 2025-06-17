package ee.qrent.billing.ui.controller.bolt;

import static ee.qrent.billing.ui.controller.ControllerUtils.BOLT_STATISTICS_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BOLT_STATISTICS_ROOT_PATH)
@AllArgsConstructor
public class BoltStatisticsQueryController {

  private final QDateFormatter qDateFormatter;
  private final GetBoltStatisticsQuery query;

  @GetMapping("/reports")
  public String getView(final Model model) {
    model.addAttribute("reports", query.getAll());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "boltStatistics";
  }

  @GetMapping("/reports/{id}/files")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
    final var statistics = query.getById(id);
    final var fileData = query.getBoltStatisticsFileBiId(id);
    if (statistics != null && fileData != null) {
      return ResponseEntity.ok()
          .header(
              HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + statistics.getFileName() + "\"")
          .contentType(MediaType.parseMediaType("text/csv"))
          .body(fileData);
    }

    return ResponseEntity.notFound().build();
  }
}
