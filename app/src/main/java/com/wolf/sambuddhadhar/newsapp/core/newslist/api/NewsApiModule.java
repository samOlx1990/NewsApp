package com.wolf.sambuddhadhar.newsapp.core.newslist.api;

import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NewsApiModule {

  @ActivityScope
  @Provides
  public static NewsApi provideNewsApi(Retrofit retrofit) {
    return retrofit.create(NewsApi.class);
  }

}
