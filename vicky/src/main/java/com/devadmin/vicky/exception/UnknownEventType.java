package com.devadmin.vicky.exception;

public class UnknownEventType extends VickyException {

  public UnknownEventType(String msg) {
    super(msg);
  }

  public UnknownEventType(String msg, Throwable cause) {
    super(msg, cause);
  }
}
