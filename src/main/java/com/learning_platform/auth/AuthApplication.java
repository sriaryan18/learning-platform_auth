package com.learning_platform.auth;

import com.learning_platform.configs.CorsConfig;
import com.learning_platform.configs.RestTemplateConfig;
import com.learning_platform.configs.SecurityConfig;
import com.learning_platform.filters.JWTFilter;
import com.learning_platform.utils.CommonJwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = {"com.learning_platform.auth", "com.learning_platform.utils"})
@Import({
  RestTemplateConfig.class,
  SecurityConfig.class,
  CorsConfig.class,
  JWTFilter.class,
  CommonJwtUtils.class,
})
public class AuthApplication {
  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    applicationContext = SpringApplication.run(AuthApplication.class, args);
    // displayAllBeans();
  }

  public static void displayAllBeans() {
    String[] allBeanNames = applicationContext.getBeanDefinitionNames();
    for (String beanName : allBeanNames) {
      System.out.println(beanName);
    }
  }
}
