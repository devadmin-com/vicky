package com.devadmin.vicky.util.impl;

import com.devadmin.vicky.util.DozerMapper;
import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;

public class DozerMapperImpl extends DozerBeanMapper implements DozerMapper {

  @Override
  public <T, U> ArrayList<U> map(final List<T> source, final Class<U> destType) {

    final ArrayList<U> dest = new ArrayList<>();

    for (T element : source) {
      if (element == null) {
        continue;
      }
      dest.add(map(element, destType));
    }

    List<U> s1 = new ArrayList<>();
    s1.add(null);
    dest.removeAll(s1);

    return dest;
  }

}
