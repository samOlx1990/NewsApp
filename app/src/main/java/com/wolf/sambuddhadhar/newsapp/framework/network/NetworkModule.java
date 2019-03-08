package com.wolf.sambuddhadhar.newsapp.framework.network;

import android.util.Log;
import com.squareup.moshi.Moshi;
import com.wolf.sambuddhadhar.newsapp.application.ApplicationComponent.ApplicationScope;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class NetworkModule {

  @Provides
  @ApplicationScope
  static Retrofit provideRetrofit(Moshi moshi) {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("OkHttp", message));
    loggingInterceptor.setLevel(Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build();

    return new Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .validateEagerly(true)
        .client(client)
        .baseUrl("")
        .build();
  }
}
