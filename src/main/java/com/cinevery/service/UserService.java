package com.cinevery.service;

import com.cinevery.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<User> findAllUsers();

  String findEmailByUsername(String username);
}
