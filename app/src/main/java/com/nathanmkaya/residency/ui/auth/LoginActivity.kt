package com.nathanmkaya.residency.ui.auth

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.ui.students.StudentsActivity
import dagger.android.AndroidInjection

class LoginActivity : AppCompatActivity(), LoginContract.View {

  private val RC_SIGN_IN = 23

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    startActivityForResult(
        AuthUI.getInstance().createSignInIntentBuilder()
            .setTheme(R.style.AppTheme_Login)
            .setAvailableProviders(arrayListOf(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
            )).build(), RC_SIGN_IN
    )
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == RC_SIGN_IN){
      val response = IdpResponse.fromResultIntent(data)
      when (resultCode){
        Activity.RESULT_OK -> {}
        else -> {
          when{
            (response == null) -> {
              finish()
            }
            (response.errorCode == ErrorCodes.NO_NETWORK) -> {}
            (response.errorCode == ErrorCodes.UNKNOWN_ERROR) -> {}
            else -> {}
          }
        }
      }
      if (resultCode == Activity.RESULT_OK){
        startActivity(Intent(this, StudentsActivity::class.java))
        finish()
        return
      }
    }
  }
}
