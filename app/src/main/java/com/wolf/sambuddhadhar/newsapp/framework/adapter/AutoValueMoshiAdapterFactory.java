package com.wolf.sambuddhadhar.newsapp.framework.adapter;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

@MoshiAdapterFactory
public abstract class AutoValueMoshiAdapterFactory implements JsonAdapter.Factory {

  public static JsonAdapter.Factory create() {
    return new AutoValueMoshi_AutoValueMoshiAdapterFactory();
  }

}
