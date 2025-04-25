package ee.qrent.billing.ui.config.spring;

import ee.qrent.billing.ui.interceptor.GitInfoInjectionInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.info.InfoPropertiesInfoContributor;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class QWebMvcConfigurer implements WebMvcConfigurer {

  private final InfoPropertiesInfoContributor<GitProperties> gitContributor;

  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new GitInfoInjectionInterceptor(gitContributor));
  }
}
