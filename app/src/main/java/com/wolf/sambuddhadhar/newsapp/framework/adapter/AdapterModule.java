package com.wolf.sambuddhadhar.newsapp.framework.adapter;

import com.serjltt.moshi.adapters.FallbackEnum;
import com.squareup.moshi.Moshi;
import com.wolf.sambuddhadhar.newsapp.application.ApplicationComponent.ApplicationScope;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class AdapterModule {

  @Binds
  @ApplicationScope
  public static Moshi provideMoshi() {
    return new Moshi.Builder()
        .add(AutoValueMoshiAdapterFactory.create())
        .add(FallbackEnum.ADAPTER_FACTORY)
        .build();
  }
}
