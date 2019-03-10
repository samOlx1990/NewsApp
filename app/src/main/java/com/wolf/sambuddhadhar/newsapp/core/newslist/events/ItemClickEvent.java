package com.wolf.sambuddhadhar.newsapp.core.newslist.events;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public abstract class ItemClickEvent implements Event{

  public abstract String url();

  public static ItemClickEvent create(String url) {
    return new AutoValue_ItemClickEvent(url);
  }

}
