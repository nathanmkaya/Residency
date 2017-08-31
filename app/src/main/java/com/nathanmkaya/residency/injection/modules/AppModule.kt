package com.nathanmkaya.residency.injection.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by nathan on 8/21/17.
 */
@Module
abstract class AppModule {
  @Provides
  @Singleton
  fun provideContext(application: Application): Context {
    return application
  }
}