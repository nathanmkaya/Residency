package com.nathanmkaya.residency.ui.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.ui.base.BaseActivity
import com.nathanmkaya.residency.ui.news.NewsContract.View
import dagger.android.AndroidInjection

class NewsActivity : BaseActivity(), View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_news)
  }
}
