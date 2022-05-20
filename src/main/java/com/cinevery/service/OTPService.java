package com.cinevery.service;

import com.cinevery.constant.DescriptionConstants;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

@Description(value = DescriptionConstants.OTP_SERVICE_Description)
@Service
public interface OTPService {
  Boolean generateOtp(String key);

  Boolean validateOTP(String key, Integer otpNumber);
}
