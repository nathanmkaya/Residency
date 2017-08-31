package com.nathanmkaya.residency.ui.students.addstudent

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvc.imagepicker.ImagePicker
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.bus.RxBus
import com.nathanmkaya.residency.model.DataManager
import com.nathanmkaya.residency.model.Student
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout.OnBackClickedCallback
import com.stepstone.stepper.StepperLayout.OnCompleteClickedCallback
import com.stepstone.stepper.StepperLayout.OnNextClickedCallback
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_student_image.student_img
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


/**
 * A simple [Fragment] subclass.
 */
class StudentImageFragment : Fragment(), BlockingStep, EasyPermissions.PermissionCallbacks {

  var student: Student? = null
  lateinit var dataManager: DataManager
  val bus = RxBus().getInstance()
  private var img: Bitmap? = null


  companion object {
    private const val RC_CAMERA_PERM = 123
  }

  override fun onBackClicked(callback: OnBackClickedCallback?) {
    callback?.goToPrevStep()
  }

  override fun onCompleteClicked(callback: OnCompleteClickedCallback?) {

  }

  override fun onNextClicked(callback: OnNextClickedCallback?) {
    callback?.goToNextStep()
  }

  override fun onSelected() {
    student = dataManager.getData() as Student
    println(student?.name)
    student_img.setOnClickListener {
      getImage()
    }
    bus.event(Bitmap::class.java).subscribe({it -> student_img.setImageBitmap(it)})
  }

  override fun verifyStep(): VerificationError? {
    return null
  }

  override fun onError(error: VerificationError) {

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }



  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.fragment_student_image, container, false)

    return view
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is DataManager) {
      dataManager = context
    }
  }

  override fun onDetach() {
    super.onDetach()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    img = ImagePicker.getImageFromResult(this.context, requestCode, resultCode, data)
    student_img.setImageBitmap(img)
    dataManager.saveImage(img)
  }

  @AfterPermissionGranted(RC_CAMERA_PERM)
  fun getImage(){
    println("Image")
    val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    when (EasyPermissions.hasPermissions(this.context, *perms)) {
      true -> {
        ImagePicker.setMinQuality(600, 600)
        ImagePicker.pickImage(this@StudentImageFragment, "Select Image...")
      }
      else -> {
        EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, *perms)
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
      grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
  }

  override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

  }

  override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {

  }
}
