package com.devadmin.vicky.util;

import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;

public interface DozerMapper extends Mapper {

  <T, U> ArrayList<U> map(final List<T> source, final Class<U> destType);
}
