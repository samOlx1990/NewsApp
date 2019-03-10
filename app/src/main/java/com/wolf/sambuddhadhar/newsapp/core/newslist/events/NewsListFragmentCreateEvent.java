package com.wolf.sambuddhadhar.newsapp.core.newslist.events;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public abstract class NewsListFragmentCreateEvent implements Event{

  public static NewsListFragmentCreateEvent create() {
    return new AutoValue_NewsListFragmentCreateEvent();
  }

}
