package com.cinevery.converter;

import com.cinevery.constant.DateFormatConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter
public class StringToDateConverter implements AttributeConverter<String, Date> {

  @Override
  public Date convertToDatabaseColumn(String str) {
    try {
      DateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD);
      return dateFormat.parse(str);
    } catch (ParseException e) {
      return null;
    }
  }

  @Override
  public String convertToEntityAttribute(Date date) {
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD);
    return dateFormat.format(currentDate);
  }
}
