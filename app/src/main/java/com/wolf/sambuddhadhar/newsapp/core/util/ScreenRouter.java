package com.wolf.sambuddhadhar.newsapp.core.util;

import android.support.v4.app.FragmentManager;
import com.wolf.sambuddhadhar.newsapp.R;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui.NewsDetailsFragment;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListFragment;
import javax.inject.Inject;

@ActivityScope
public class ScreenRouter {

  private final FragmentManager fragmentManager;

  @Inject
  public ScreenRouter(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  public void loadScreen(ScreenKey key, String param) {
    switch (key) {
      case LIST:
        NewsListFragment fragment = NewsListFragment.newInstance();
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        break;
      case DETAILS:
        NewsDetailsFragment fragment1 = NewsDetailsFragment.newInstance(param);
        fragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment1)
            .addToBackStack(null)
            .commitAllowingStateLoss();
        break;
      default:
        throw new IllegalArgumentException("Invalid screen key");
    }
  }

  public boolean loadPrevious() {
    if (fragmentManager.getBackStackEntryCount() > 0) {
      fragmentManager.popBackStack();
      return true;
    }
    return false;
  }

}
