package ee.qrent.billing.ui.controller.bolt;

import static ee.qrent.billing.ui.controller.ControllerUtils.BOLT_STATISTICS_ROOT_PATH;
import static ee.qrent.billing.ui.controller.ControllerUtils.FIRM_ROOT_PATH;

import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsAddRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsDeleteRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsAddUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsDeleteUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(BOLT_STATISTICS_ROOT_PATH)
@AllArgsConstructor
public class BoltStatisticsUseCaseController {

  private final BoltStatisticsAddUseCase addUseCase;
  private final BoltStatisticsUpdateUseCase updateUseCase;
  private final BoltStatisticsDeleteUseCase deleteUseCase;
  private final GetBoltStatisticsQuery query;

  /*  @GetMapping("/")
  public String listFiles(Model model) {
    model.addAttribute("files", fileStorageService.getAllFiles());
    return "upload_form";
  }*/

  /*  @PostMapping("/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    try {
      fileStorageService.store(file);
      redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
    }
    return "redirect:/";
  }*/

  /*  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
    return fileStorageService
        .getFile(id)
        .map(
            file ->
                ResponseEntity.ok()
                    .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .body(file.getFileData()))
        .orElse(ResponseEntity.notFound().build());
  }*/

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new BoltStatisticsAddRequest());

    return "forms/addBoltStatistics";
  }

  @PostMapping(value = "/add")
  public String add(
      @RequestParam("reportFile") MultipartFile reportFile,
      @RequestParam("region") String region,
      @RequestParam("month") Integer month,
      @RequestParam("year") Integer year)
      throws IOException {

    final var addRequest = new BoltStatisticsAddRequest();
    addRequest.setRegion(region);
    addRequest.setMonth(month);
    addRequest.setYear(year);
    addRequest.setData(reportFile.getBytes());
    addRequest.setFileName(reportFile.getOriginalFilename());
    addUseCase.add(addRequest);

    return "redirect:" + BOLT_STATISTICS_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", query.getUpdateRequestById(id));

    return "forms/updateFirm";
  }

  @PostMapping("/update")
  public String update(final BoltStatisticsUpdateRequest updateRequest) {
    updateUseCase.update(updateRequest);

    return "redirect:" + BOLT_STATISTICS_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new BoltStatisticsDeleteRequest(id));
    model.addAttribute("objectInfo", query.getObjectInfo(id));

    return "forms/deleteBoltStatistics";
  }

  @PostMapping("/delete")
  public String delete(final BoltStatisticsDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + BOLT_STATISTICS_ROOT_PATH;
  }
}
