package com.paitrick.blog.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private Jwt jwt = new Jwt();
  private Admin admin = new Admin();

  @Data
  public static class Jwt {
    private String secret;
    private long expirationHours;
  }

  @Data
  public static class Admin {
    private String username;
    private String password;
  }
}
