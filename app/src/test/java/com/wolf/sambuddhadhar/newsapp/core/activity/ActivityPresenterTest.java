package com.wolf.sambuddhadhar.newsapp.core.activity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.wolf.sambuddhadhar.newsapp.core.newslist.events.ItemClickEvent;
import com.wolf.sambuddhadhar.newsapp.core.util.ScreenKey;
import com.wolf.sambuddhadhar.newsapp.framework.TestSchedulerRule;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.subjects.PublishSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ActivityPresenterTest {

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Rule public TestSchedulerRule schedulerRule = new TestSchedulerRule();

  @Mock ActivityUi activityUi;

  @Captor ArgumentCaptor<ScreenKey> screenKeyArgumentCaptor;
  @Captor ArgumentCaptor<String> dataCaptor;

  PublishSubject<Event> uiEvent = PublishSubject.create();

  @Before
  public void setUp() throws Exception {
    uiEvent
        .compose(new ActivityPresenter())
        .subscribe(ui -> ui.act(activityUi));
  }

  @Test
  public void when_activity_is_created_load_news_list_fragment_with_null_param() {
    uiEvent.onNext(ActivityCreateEvent.create());
    verify(activityUi).loadScreen(screenKeyArgumentCaptor.capture(), dataCaptor.capture());
    verifyNoMoreInteractions(activityUi);
    ScreenKey screenKey = screenKeyArgumentCaptor.getValue();
    String data = dataCaptor.getValue();
    assertEquals(null, data);
    assertEquals(ScreenKey.LIST, screenKey);

  }

  @Test
  public void when_back_press_event_is_received_load_previous_screen() {
    uiEvent.onNext(BackPressEvent.create());
    verify(activityUi).loadPrevious();
    verifyNoMoreInteractions(activityUi);

  }

  @Test
  public void when_item_click_event_is_received_then_load_details_screen_with_specific_url() {
    String url = "abc";
    uiEvent.onNext(ItemClickEvent.create(url));
    verify(activityUi).loadScreen(screenKeyArgumentCaptor.capture(), dataCaptor.capture());
    verifyNoMoreInteractions(activityUi);
    ScreenKey key = screenKeyArgumentCaptor.getValue();
    String data = dataCaptor.getValue();
    assertEquals(ScreenKey.DETAILS, key);
    assertEquals(url, data);
  }

}