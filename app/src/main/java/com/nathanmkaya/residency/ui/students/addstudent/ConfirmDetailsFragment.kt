package com.nathanmkaya.residency.ui.students.addstudent

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.bus.RxBus
import com.nathanmkaya.residency.model.DataManager
import com.nathanmkaya.residency.model.Student
import com.nathanmkaya.residency.ui.students.StudentHelper
import com.nathanmkaya.residency.ui.students.StudentHelper.CallBack
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.Step
import com.stepstone.stepper.StepperLayout.OnBackClickedCallback
import com.stepstone.stepper.StepperLayout.OnCompleteClickedCallback
import com.stepstone.stepper.StepperLayout.OnNextClickedCallback
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_confirm_details.hostel_txt
import kotlinx.android.synthetic.main.fragment_confirm_details.name_txt
import kotlinx.android.synthetic.main.fragment_confirm_details.regNo_txt
import kotlinx.android.synthetic.main.fragment_confirm_details.room_txt
import kotlinx.android.synthetic.main.fragment_confirm_details.student_img
import kotlinx.android.synthetic.main.fragment_confirm_details.wing_txt

/**
 * A simple [Fragment] subclass.
 */
class ConfirmDetailsFragment : Fragment(), BlockingStep {

  lateinit var dataManager: DataManager
  var student: Student? = null
  val bus = RxBus().getInstance()

  override fun onSelected() {
    student = dataManager.getData() as Student
    name_txt.text = student?.name
    regNo_txt.text = student?.regNo
    hostel_txt.text = student?.hostel
    wing_txt.text = student?.wing
    room_txt.text = student?.room
    student_img.setImageBitmap(dataManager.getImage())
  }

  override fun verifyStep(): VerificationError? {
    return null
  }

  override fun onError(error: VerificationError) {

  }

  override fun onBackClicked(callback: OnBackClickedCallback?) {

  }

  override fun onCompleteClicked(callback: OnCompleteClickedCallback?) {
    callback?.stepperLayout?.showProgress("Uploading")
    StudentHelper.addStudent(dataManager.getImage(), student, object:CallBack{
      override fun complete() {
        callback?.complete()
      }

      override fun uploaded(done: Boolean) {
        callback?.stepperLayout?.hideProgress()
      }

      override fun error(error: Exception) {
        onError(VerificationError("Upload Failed Try Again"))
      }
    })
  }

  override fun onNextClicked(callback: OnNextClickedCallback?) {

  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is DataManager) {
      dataManager = context
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater!!.inflate(R.layout.fragment_confirm_details, container, false)
  }
}// Required empty public constructor
