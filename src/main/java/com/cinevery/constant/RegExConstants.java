package com.cinevery.constant;

public class RegExConstants {
  public static final String USERNAME = "^(?=.{1,50}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
}
