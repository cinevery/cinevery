package com.cinevery.service.impl;

import com.cinevery.rest.dto.EmailDTO;
import com.cinevery.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService {
  private final Logger logger = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender emailSender;

  public EmailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public Boolean sendSimpleMessage(EmailDTO emailDTO) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(String.join(",", emailDTO.getRecipients()));
    mailMessage.setSubject(emailDTO.getSubject());
    mailMessage.setText(emailDTO.getBody());

    boolean isSent = false;
    try {
      emailSender.send(mailMessage);
      isSent = true;
    } catch (Exception e) {
      logger.error("Sending e-mail error: {}", e.getMessage());
    }
    return isSent;
  }
}
