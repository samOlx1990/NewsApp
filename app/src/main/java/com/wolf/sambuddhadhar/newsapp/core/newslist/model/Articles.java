package com.wolf.sambuddhadhar.newsapp.core.newslist.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import javax.annotation.Nullable;

@AutoValue
public abstract class Articles {

  @Nullable
  @Json(name = "title")
  public abstract String title();

  @Nullable
  @Json(name = "url")
  public abstract String url();

  @Nullable
  @Json(name = "urlToImage")
  public abstract String imageUrl();

  public static JsonAdapter<Articles> jsonAdapter(Moshi moshi) {
    return new AutoValue_Articles.MoshiJsonAdapter(moshi);
  }

  public static Articles create(String title, String url, String imageUrl) {
    return new AutoValue_Articles(title, url, imageUrl);
  }
}
