package com.nathanmkaya.residency.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
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