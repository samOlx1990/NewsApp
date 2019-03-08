package com.wolf.sambuddhadhar.newsapp.application;

import android.app.Application;

public class NewsApplication extends Application {
  public static ApplicationComponent component() {
    return applicationComponent;
  }

  private static ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    applicationComponent = buildComponent();
    super.onCreate();
    applicationComponent.inject(this);
  }

  protected ApplicationComponent buildComponent() {
    return DaggerApplicationComponent.builder()
        .application(this)
        .build();
  }
}
