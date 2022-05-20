package com.cinevery.service.impl;

import com.cinevery.constant.DescriptionConstants;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Description(value = DescriptionConstants.OTP_GENERATOR_Description)
@Service
public class OTPGenerator {

  private static final Integer EXPIRE_MIN = 5;
  private final LoadingCache<String, Integer> otpCache;

  public OTPGenerator() {
    super();
    CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
    objectObjectCacheBuilder.expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES);
    otpCache = objectObjectCacheBuilder
        .build(new CacheLoader<String, Integer>() {
          @Override
          public Integer load(String s) throws Exception {
            return 0;
          }
        });
  }

  public Integer generateOTP(String key) {
    Random random = new Random();
    int OTP = 100000 + random.nextInt(900000);
    otpCache.put(key, OTP);

    return OTP;
  }

  public Integer getOPTByKey(String key) {
    return otpCache.getIfPresent(key);
  }

  public void clearOTPFromCache(String key) {
    otpCache.invalidate(key);
  }
}
