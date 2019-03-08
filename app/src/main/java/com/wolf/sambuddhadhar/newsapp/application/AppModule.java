package com.wolf.sambuddhadhar.newsapp.application;

import android.app.Application;
import android.content.Context;
import com.wolf.sambuddhadhar.newsapp.application.ApplicationComponent.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  @ApplicationScope
  @Provides
  public static Context provideApplicationContext(Application application) {
    return application.getApplicationContext();
  }

}
