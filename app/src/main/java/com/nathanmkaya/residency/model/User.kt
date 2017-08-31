package com.nathanmkaya.residency.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.AppCompatImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.nathanmkaya.residency.utils.Utils
import java.util.UUID

/**
 * Created by nathan on 5/5/17.
 */

class User {
  var name: String? = null
  var email: String? = null
  var img: String? = null
  var regNo: String? = null
  var hostel: String? = null
  var wing: String? = null
  var room: String? = null
  var role: String? = null

  companion object {

    internal var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun UpdateProfilePic(context: Context, bitmap: Bitmap, view: AppCompatImageView) {
      val reference = FirebaseStorage.getInstance().reference
      // Create the file metadata
      val metadata = StorageMetadata.Builder()
          .setContentType("image/png")
          .build()
      val file = Utils.getFile(bitmap)
      val task = reference.child("users/" + UUID.randomUUID().toString()).putFile(
          Uri.fromFile(file), metadata)
      task.addOnSuccessListener { taskSnapshot ->
        /*Glide.with(context)
            .load(taskSnapshot.metadata!!.downloadUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(view)*/

        val changeRequest = UserProfileChangeRequest.Builder()
            .setPhotoUri(taskSnapshot.metadata!!.downloadUrl)
            .build()
        user?.updateProfile(changeRequest)
      }
    }
  }

}
