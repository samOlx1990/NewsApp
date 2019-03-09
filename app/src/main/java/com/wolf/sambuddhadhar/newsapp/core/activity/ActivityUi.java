package com.wolf.sambuddhadhar.newsapp.core.activity;

import com.wolf.sambuddhadhar.newsapp.core.util.ScreenKey;

interface ActivityUi {
  void loadScreen(ScreenKey key, String param);

  void loadPrevious();
}
