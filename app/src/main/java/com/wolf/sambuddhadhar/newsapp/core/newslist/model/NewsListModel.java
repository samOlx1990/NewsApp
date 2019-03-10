package com.wolf.sambuddhadhar.newsapp.core.newslist.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class NewsListModel {

  @Json(name = "status")
  abstract String status();

  @Json(name = "articles")
  public abstract List<Articles> articles();

  public static JsonAdapter<NewsListModel> jsonAdapter(Moshi moshi) {
    return new AutoValue_NewsListModel.MoshiJsonAdapter(moshi);
  }

  public static NewsListModel create(String status, List<Articles> articles) {
    return new AutoValue_NewsListModel(status, articles);
  }

}
