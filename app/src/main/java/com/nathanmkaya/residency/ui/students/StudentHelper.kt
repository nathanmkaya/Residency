package com.nathanmkaya.residency.ui.students

import com.google.android.gms.tasks.OnFailureListener
import com.nathanmkaya.residency.utils.DbReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import java.util.UUID.randomUUID
import com.google.firebase.storage.StorageMetadata
import com.nathanmkaya.residency.model.Student
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.nathanmkaya.residency.utils.Utils
import java.util.UUID


/**
 * Created by nathan on 8/23/17.
 */
class StudentHelper {

  companion object {
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    fun addStudent(bitmap: Bitmap?, student: Student?, callBack: CallBack?) {

      val photo = Utils.getFile(bitmap!!)

      // Create the photo metadata
      val metadata = StorageMetadata.Builder()
          .setContentType("image/png")
          .build()

      val snapshot = storageRef.child("students/" + "${student?.name?.trim { it <= ' ' }}-${student?.regNo}").putFile(
          Uri.fromFile(photo), metadata)
      snapshot.addOnSuccessListener { taskSnapshot ->
        val downloadUrl = taskSnapshot.metadata!!.downloadUrl
        student?.img = downloadUrl?.toString()
        DbReference.students.push().setValue(student)
        callBack?.uploaded(true)
      }.addOnFailureListener {
        e -> println(e.stackTrace)
        callBack?.error(e)
      }.addOnCompleteListener {
        callBack?.complete()
      }
    }
  }

  interface CallBack{
    fun complete()
    fun uploaded(done: Boolean)
    fun error(error: Exception)
  }
}