package com.wolf.sambuddhadhar.newsapp.core.activity;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public abstract class BackPressEvent implements Event{

  public static BackPressEvent create() {
    return new AutoValue_BackPressEvent();
  }

}
