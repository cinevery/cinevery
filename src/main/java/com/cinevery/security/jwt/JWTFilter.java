package com.cinevery.security.jwt;

import com.cinevery.constant.AuthoritiesConstants;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

  private final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

  private final TokenProvider tokenProvider;

  public JWTFilter(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String jwt = resolveToken(httpServletRequest);
      if (StringUtils.hasText(jwt)) {
        if (this.tokenProvider.validateToken(jwt)) {
          Authentication authentication = this.tokenProvider.getAuthentication(jwt);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (ExpiredJwtException eje) {
      logger.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
      ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AuthoritiesConstants.AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthoritiesConstants.BEARER)) {
      return bearerToken.substring(7, bearerToken.length());
    }
    String jwt = request.getParameter(AuthoritiesConstants.AUTHORIZATION_TOKEN);
    if (StringUtils.hasText(jwt)) {
      return jwt;
    }
    return null;
  }
}
