package com.devadmin.vicky;

public class VickyException extends Exception {

  public VickyException(String msg) {
    super(msg);
  }

  public VickyException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
