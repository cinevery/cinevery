package com.cinevery.service.impl;

import com.cinevery.model.User;
import com.cinevery.repository.UserRepository;
import com.cinevery.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAllUsers() {
    return this.userRepository.findAll();
  }

  @Override
  public String findEmailByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(User::getEmail).orElse(null);
  }
}
