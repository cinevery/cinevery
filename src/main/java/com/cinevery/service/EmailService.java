package com.cinevery.service;

import com.cinevery.rest.dto.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
  Boolean sendSimpleMessage(EmailDTO emailDTO);
}
