package com.nathanmkaya.residency.ui.students.addstudent

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mvc.imagepicker.ImagePicker
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.bus.RxBus
import com.nathanmkaya.residency.model.DataManager
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_add_student.student_stepper
import org.parceler.Parcel
import org.parceler.Parcels

class AddStudentActivity : AppCompatActivity(), DataManager, StepperLayout.StepperListener {
  companion object {
    private const val DATA = "data"
  }

  private var data: Any? = null
  private var img: Bitmap? = null
  val bus = RxBus().getInstance()

  override fun saveData(data: Any?) {
    this.data = data
  }

  override fun getData(): Any? {
    return data
  }

  override fun saveImage(bitmap: Bitmap?) {
    this.img = bitmap
  }

  override fun getImage(): Bitmap? {
    return img
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_student)
    title = "Add Student"
    student_stepper.adapter = AddStudentStepperAdapter(supportFragmentManager, this)
    data = Parcels.unwrap(savedInstanceState?.getParcelable(DATA))
    img = Parcels.unwrap(savedInstanceState?.getParcelable("img"))
    student_stepper.setListener(this)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(DATA, Parcels.wrap(data))
    outState.putParcelable("img", img)
    super.onSaveInstanceState(outState)
  }

  override fun onBackPressed() {
    if (student_stepper.currentStepPosition > 0) {
      student_stepper.onBackClicked()
    } else {
      super.onBackPressed()
    }
  }

  override fun onStepSelected(newStepPosition: Int) {
    Toast.makeText(this,"position $newStepPosition", Toast.LENGTH_SHORT).show()
  }

  override fun onError(verificationError: VerificationError?) {
    Toast.makeText(this,verificationError.toString(), Toast.LENGTH_SHORT).show()
  }

  override fun onReturn() {
    Toast.makeText(this,"return", Toast.LENGTH_SHORT).show()
  }

  override fun onCompleted(completeButton: View?) {
    //Toast.makeText(this,"Complete", Toast.LENGTH_SHORT).show()
    finish()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
  }
}
