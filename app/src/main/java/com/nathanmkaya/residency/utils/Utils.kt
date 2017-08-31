package com.nathanmkaya.residency.utils

import android.graphics.Bitmap
import android.os.Environment

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Created by nathan on 5/5/17.
 */

object Utils {

  fun getFile(bitmap: Bitmap): File {
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "img.png")
    try {
      val outputStream = BufferedOutputStream(FileOutputStream(file))
      bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
      outputStream.close()
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    return file
  }
}
