package com.wolf.sambuddhadhar.newsapp.core.newslist.events;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public class RefreshDataEvent implements Event{

  public static RefreshDataEvent create() {
    return new AutoValue_RefreshDataEvent();
  }

}
