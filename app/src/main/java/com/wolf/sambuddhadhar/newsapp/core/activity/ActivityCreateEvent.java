package com.wolf.sambuddhadhar.newsapp.core.activity;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.util.Event;

@AutoValue
abstract class ActivityCreateEvent implements Event{

  public static ActivityCreateEvent create() {
    return new AutoValue_ActivityCreateEvent();
  }

}
