package com.cinevery.service.impl;

import com.cinevery.rest.dto.EmailDTO;
import com.cinevery.service.EmailService;
import com.cinevery.service.OTPService;
import com.cinevery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OTPServiceImpl implements OTPService {
  private final Logger logger = LoggerFactory.getLogger(OTPService.class);

  private final OTPGenerator otpGenerator;
  private final EmailService emailService;
  private final UserService userService;

  public OTPServiceImpl(OTPGenerator otpGenerator, EmailService emailService, UserService userService) {
    this.otpGenerator = otpGenerator;
    this.emailService = emailService;
    this.userService = userService;
  }

  @Override
  public Boolean generateOtp(String key) {

    Integer otpValue = otpGenerator.generateOTP(key);
    if (otpValue == -1) {
      logger.error("OTP generator is not working...");
      return false;
    }

    logger.info("Generated OTP: {}", otpValue);

    String userEmail = userService.findEmailByUsername(key);
    List<String> recipients = new ArrayList<>();
    recipients.add(userEmail);

    EmailDTO emailDTO = new EmailDTO();
    emailDTO.setSubject("Spring Boot OTP Password.");
    emailDTO.setBody("OTP Password: " + otpValue);
    emailDTO.setRecipients(recipients);

    return emailService.sendSimpleMessage(emailDTO);
  }

  @Override
  public Boolean validateOTP(String key, Integer otpNumber) {
    Integer cacheOTP = otpGenerator.getOPTByKey(key);
    if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
      otpGenerator.clearOTPFromCache(key);
      return true;
    }
    return false;
  }
}
