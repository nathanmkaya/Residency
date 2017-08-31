package com.nathanmkaya.residency.injection.components

import android.app.Application
import com.nathanmkaya.residency.App
import com.nathanmkaya.residency.injection.modules.ActivityModule
import com.nathanmkaya.residency.injection.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * Created by nathan on 8/21/17.
 */
@Singleton
@Component(
    modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivityModule::class))
interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: App)
}