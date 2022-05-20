package com.cinevery.converter;

import com.cinevery.constant.DateFormatConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Converter
public class LocalDateToUtilDateConverter implements AttributeConverter<LocalDate, Date> {

  @Override
  public Date convertToDatabaseColumn(LocalDate localDate) {
    DateFormat dateFormatLD = new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD);
    String strDate = dateFormatLD.format(localDate);
    try {
      DateFormat dateFormatDL = new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD);
      return dateFormatDL.parse(strDate);
    } catch (ParseException e) {
      return null;
    }
  }

  @Override
  public LocalDate convertToEntityAttribute(Date date) {
    DateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.YYYY_MM_DD_HH_MM_SS);
    String dateString = dateFormat.format(date);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormatConstants.D_MM_YYYY);
    return LocalDate.parse(dateString, dateTimeFormatter);
  }
}
