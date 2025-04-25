package ee.qrent.billing.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoPropertiesInfoContributor;
import org.springframework.boot.info.GitProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@AllArgsConstructor
public class GitInfoInjectionInterceptor implements HandlerInterceptor {

  private final InfoPropertiesInfoContributor<GitProperties> gitContributor;

  @Override
  public void postHandle(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler,
      final ModelAndView modelAndView) {
    if (modelAndView == null) {
      return;
    }

    final var builder = new Info.Builder();
    gitContributor.contribute(builder);
    final var details = builder.build().getDetails();
    final var gitInfo = (Map<String, Object>) details.get("git");
    final var gitCommitBranch = gitInfo.get("branch");
    final var gitCommitMap = (Map<String, Object>) gitInfo.get("commit");
    final var gitCommitTime = gitCommitMap.get("time");
    final var gitCommitAbbrev = ((Map<String, Object>) (gitCommitMap.get("id"))).get("abbrev");
    final var gitCommitUserEmail = ((Map<String, Object>) (gitCommitMap.get("user"))).get("email");

    final var model = modelAndView.getModel();
    if (model == null) {
      return;
    }

    model.put("gitBranch", gitCommitBranch);
    model.put("gitCommitAbbrev", gitCommitAbbrev);
    model.put("gitCommitTime", gitCommitTime);
    model.put("gitCommitUserEmail", gitCommitUserEmail);
  }
}
