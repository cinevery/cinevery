package com.cinevery.rest.dto;

import com.cinevery.constant.RegExConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDTO {

  @Pattern(regexp = RegExConstants.USERNAME)
  @NotNull
  @Size(min = 1, max = 50)
  private String username;

  @NotNull
  @Size(min = 4, max = 32)
  private String password;

  private Boolean rememberMe;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(Boolean rememberMe) {
    this.rememberMe = rememberMe;
  }

  @Override
  public String toString() {
    return "LoginDTO{" + "password='" + password + '\'' + ", username='" + username + '\'' + ", rememberMe=" + rememberMe + '}';
  }
}
