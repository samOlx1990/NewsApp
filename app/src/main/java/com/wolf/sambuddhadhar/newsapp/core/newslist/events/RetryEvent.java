package com.wolf.sambuddhadhar.newsapp.core.newslist.events;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public class RetryEvent implements Event{

  public static RetryEvent create() {
    return new AutoValue_RetryEvent();
  }

}
