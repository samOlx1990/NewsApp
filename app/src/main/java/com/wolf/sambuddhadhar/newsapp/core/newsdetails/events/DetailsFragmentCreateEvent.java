package com.wolf.sambuddhadhar.newsapp.core.newsdetails.events;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
public abstract class DetailsFragmentCreateEvent implements Event{

  public abstract String url();

  public static DetailsFragmentCreateEvent create(String url) {
    return new AutoValue_DetailsFragmentCreateEvent(url);
  }

}
