package com.nathanmkaya.residency

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Environment
import android.os.StatFs
import android.util.Log
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nathanmkaya.residency.injection.components.DaggerAppComponent
import com.nathanmkaya.residency.utils.DbReference
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by nathan on 8/14/17.
 */
class App : Application(), HasActivityInjector {

  companion object {
    private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()
    private val MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4
    private const val MAX_DISK_CACHE_SIZE = 40L * ByteConstants.MB
    @SuppressLint("NewApi")
    private val MAX_DISK_SIZE = StatFs(Environment.getExternalStoragePublicDirectory("Pictures").path).blockSizeLong
  }

  @Inject
  lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()
    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    FirebaseAuth.getInstance().signInAnonymously()
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
      DbReference.keepSynced(true)
      Log.d("User", user.uid)
      //DbReference.users.child(user.getUid()).
    }

    DaggerAppComponent.builder().application(this).build().inject(this)

    val pipelineConfig = ImagePipelineConfig.newBuilder(this).setMainDiskCacheConfig(
        DiskCacheConfig.newBuilder(this)
            .setBaseDirectoryName("residency")
            .setBaseDirectoryPath(Environment.getExternalStoragePublicDirectory("Pictures"))
            .setMaxCacheSize(MAX_DISK_SIZE / 4)
            .build())
        .build()
    Fresco.initialize(this, pipelineConfig)
  }

  override fun activityInjector(): AndroidInjector<Activity>? = dispatchingActivityInjector
}