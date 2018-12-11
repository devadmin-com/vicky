package com.devadmin.vicky;

/**
 * An exception thrown in {@link MessageService} as a result of some problem...
 */
public class MessageServiceException extends Exception {

  public MessageServiceException(String msg) {
    super(msg);
  }

  public MessageServiceException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
