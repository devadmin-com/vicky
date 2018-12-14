package com.devadmin.vicky.test;

import com.devadmin.vicky.Item;

public class TestItem implements Item {

  private String field;
  private String to;

  @Override
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  @Override
  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }
}
