package com.paitrick.blog.auth.service;

import com.paitrick.blog.auth.dto.LoginRequest;
import com.paitrick.blog.common.config.AppProperties;
import com.paitrick.blog.common.exception.BusinessException;
import com.paitrick.blog.common.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final AppProperties appProperties;
  private final JwtService jwtService;

  public AuthService(AppProperties appProperties, JwtService jwtService) {
    this.appProperties = appProperties;
    this.jwtService = jwtService;
  }

  public String login(LoginRequest request) {
    var admin = appProperties.getAdmin();
    if (!admin.getUsername().equals(request.getUsername())
        || !admin.getPassword().equals(request.getPassword())) {
      throw new BusinessException("invalid username or password");
    }
    return jwtService.create(request.getUsername());
  }
}
