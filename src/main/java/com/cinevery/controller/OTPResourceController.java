package com.cinevery.controller;

import com.cinevery.constant.APIConstants;
import com.cinevery.constant.Constants;
import com.cinevery.constant.DescriptionConstants;
import com.cinevery.constant.MessageConstants;
import com.cinevery.service.OTPService;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Description(value = DescriptionConstants.OTP_RESOURCE_Description)
@RestController
@RequestMapping(APIConstants.OTP)
public class OTPResourceController {

  private final OTPService otpService;

  public OTPResourceController(OTPService otpService) {
    this.otpService = otpService;
  }

  @PostMapping(value = APIConstants.OTP_GENERATE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> generateOTP() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Map<String, String> response = new HashMap<>(2);

    if (username == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    Boolean isGenerated = otpService.generateOtp(username);
    if (!isGenerated) {
      response.put(Constants.STATUS, MessageConstants.ERROR);
      response.put(Constants.MESSAGE, MessageConstants.OTP_GENERATED_ERROR);

      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    response.put(Constants.STATUS, MessageConstants.SUCCESS);
    response.put(Constants.MESSAGE, MessageConstants.OTP_GENERATED_SUCCESS);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(value = APIConstants.OTP_VALIDATE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> validateOTP(@RequestBody Map<String, Object> otp) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Map<String, String> response = new HashMap<>(2);

    if (username == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    Boolean isValid = otpService.validateOTP(username, (Integer) otp.get("otp"));
    if (!isValid) {
      response.put(Constants.STATUS, MessageConstants.ERROR);
      response.put(Constants.MESSAGE, MessageConstants.OTP_VALIDATE_ERROR);

      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    response.put(Constants.STATUS, MessageConstants.SUCCESS);
    response.put(Constants.MESSAGE, MessageConstants.OTP_VALIDATE_SUCCESS);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
