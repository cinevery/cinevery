package com.cinevery.security;

import com.cinevery.enumeration.AuthorityName;
import com.cinevery.exceptions.UserNotActivatedException;
import com.cinevery.model.User;
import com.cinevery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

  private final UserRepository userRepository;

  public UserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {
    logger.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase();
    Optional<User> userFromDatabase = userRepository.findByUsername(lowercaseLogin);
    return userFromDatabase.map(user -> {
      if (user.getEnabled() == null || !user.getEnabled()) {
        throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }
      List<GrantedAuthority> grantedAuthorities = user.getRoles()
          .stream()
          .map(role -> new SimpleGrantedAuthority(role.getId().toString()))
          .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(lowercaseLogin, user.getPassword(), grantedAuthorities);
    }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
  }
}
