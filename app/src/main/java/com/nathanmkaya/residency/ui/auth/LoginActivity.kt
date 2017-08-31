package com.nathanmkaya.residency.ui.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nathanmkaya.residency.R
import dagger.android.AndroidInjection

class LoginActivity : AppCompatActivity(), LoginContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
  }
}
