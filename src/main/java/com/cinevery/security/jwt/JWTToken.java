package com.cinevery.security.jwt;

import com.cinevery.constant.AuthoritiesConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {

  private String idToken;

  public JWTToken(String idToken) {
    this.idToken = idToken;
  }

  @JsonProperty(AuthoritiesConstants.ID_TOKEN)
  public String getIdToken() {
    return idToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

}
