package com.cinevery.controller;

import com.cinevery.constant.APIConstants;
import com.cinevery.constant.AuthoritiesConstants;
import com.cinevery.rest.dto.LoginDTO;
import com.cinevery.security.jwt.JWTToken;
import com.cinevery.security.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(APIConstants.AUTH)
public class AuthenticationController {

  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  private final TokenProvider tokenProvider;

  private final AuthenticationManager authenticationManager;

  public AuthenticationController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @RequestMapping(value = APIConstants.AUTH_AUTHENTICATE, method = RequestMethod.POST)
  public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
    logger.debug("Credentials: {}", loginDTO);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
    try {
      Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);
      boolean rememberMe = loginDTO.isRememberMe() != null && loginDTO.isRememberMe();

      String jwt = tokenProvider.createToken(authentication, rememberMe);
      response.addHeader(AuthoritiesConstants.AUTHORIZATION_HEADER, AuthoritiesConstants.BEARER + jwt);

      return ResponseEntity.ok(new JWTToken(jwt));
    } catch (AuthenticationException exception) {
      return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
    }
  }
}
