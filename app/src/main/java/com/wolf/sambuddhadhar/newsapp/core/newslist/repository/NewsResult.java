package com.wolf.sambuddhadhar.newsapp.core.newslist.repository;

import com.google.auto.value.AutoValue;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.NewsListModel;

public abstract class NewsResult {

  public static Success success(NewsListModel data) {
    return Success.create(data);
  }

  public static Failure failure() {
    return Failure.create();
  }

  @AutoValue
  public static abstract class Success extends NewsResult {

    public abstract NewsListModel data();

    public static Success create(NewsListModel data) {
      return new AutoValue_NewsResult_Success(data);
    }
  }

  @AutoValue
  public static abstract class Failure extends NewsResult {

    public static Failure create() {
      return new AutoValue_NewsResult_Failure();
    }
  }

}
