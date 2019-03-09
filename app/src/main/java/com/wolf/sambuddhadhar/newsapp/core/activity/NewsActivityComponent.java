package com.wolf.sambuddhadhar.newsapp.core.activity;

import android.support.v4.app.FragmentManager;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.NewsActivityModule;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

@ActivityScope
@Subcomponent(modules = NewsActivityModule.class)
public interface NewsActivityComponent {

  void inject(NewsActivity target);

  @Scope
  @Retention(RetentionPolicy.RUNTIME)
  @interface ActivityScope{}

  @Subcomponent.Builder
  interface Builder {

    @BindsInstance
    Builder activity(NewsActivity activity);

    NewsActivityComponent build();
  }

  @Module
  abstract class NewsActivityModule {

    @ActivityScope
    @Provides
    static FragmentManager provideFragmentManager(NewsActivity activity) {
      return activity.getSupportFragmentManager();
    }
  }

}
