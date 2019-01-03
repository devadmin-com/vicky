/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
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
