package com.cinevery.rest.resources;

import com.cinevery.constant.APIConstants;
import com.cinevery.model.User;
import com.cinevery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIConstants.API)
public class UserResource {

  private final Logger logger = LoggerFactory.getLogger(UserResource.class);

  private final UserService userService;

  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(
      value = APIConstants.API_GET_USERS,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<User>> getUsers() {
    logger.debug("CLIENT REST REQUEST!");

    List<User> userList = userService.findAllUsers();
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }
}
