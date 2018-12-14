package com.devadmin.vicky.test;

import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.Item;
import java.util.List;

public class TestChangelog implements Changelog {

  private List<Item> items;

  @Override
  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
