package com.cinevery.security.jwt;

import com.cinevery.constant.AuthoritiesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long tokenValidityInSeconds;

  @Value("${jwt.expiration}")
  private long tokenValidityInSecondsForRememberMe;

  public String createToken(Authentication authentication, Boolean rememberMe) {
    String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity;
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
    } else {
      validity = new Date(now + this.tokenValidityInSeconds * 1000);
    }

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AuthoritiesConstants.AUTHORITIES_KEY, authorities)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setExpiration(validity)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();

    String principal = claims.getSubject();
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AuthoritiesConstants.AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.info("Invalid JWT signature: " + e.getMessage());
      return false;
    }
  }
}
