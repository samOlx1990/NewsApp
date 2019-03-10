package com.wolf.sambuddhadhar.newsapp.core.activity;

import android.support.v4.app.FragmentManager;
import com.squareup.picasso.Picasso;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.NewsActivityModule;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui.NewsDetailsFragment;
import com.wolf.sambuddhadhar.newsapp.core.newslist.api.NewsApiModule;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListAdapter;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListFragment;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

@ActivityScope
@Subcomponent(modules = {NewsActivityModule.class,
    NewsApiModule.class})
public interface NewsActivityComponent {

  void inject(NewsActivity target);

  void inject(NewsListFragment target);

  void inject(NewsListAdapter target);

  void inject(NewsDetailsFragment target);

  @Scope
  @Retention(RetentionPolicy.RUNTIME)
  @interface ActivityScope{}

  @Subcomponent.Builder
  interface Builder {

    @BindsInstance
    Builder activity(NewsActivity activity);

    @BindsInstance
    Builder picasso(Picasso picasso);

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
