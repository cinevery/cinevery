package com.cinevery.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class TestUtil {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

  public static byte[] convertObjectToJsonBytes(Object object)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(OffsetDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(LocalDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(Instant.class, JSR310DateTimeSerializer.INSTANCE);
    module.addDeserializer(LocalDate.class, JSR310LocalDateDeserializer.INSTANCE);
    mapper.registerModule(module);

    return mapper.writeValueAsBytes(object);
  }

  public static byte[] createByteArray(int size, String data) {
    byte[] byteArray = new byte[size];
    for (int i = 0; i < size; i++) {
      byteArray[i] = Byte.parseByte(data, 2);
    }
    return byteArray;
  }
}
