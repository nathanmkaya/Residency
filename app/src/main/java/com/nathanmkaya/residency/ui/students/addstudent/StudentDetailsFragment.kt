package com.nathanmkaya.residency.ui.students.addstudent

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.DataManager
import com.nathanmkaya.residency.model.Student
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout.OnBackClickedCallback
import com.stepstone.stepper.StepperLayout.OnCompleteClickedCallback
import com.stepstone.stepper.StepperLayout.OnNextClickedCallback
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_student_details.hostel
import kotlinx.android.synthetic.main.fragment_student_details.regNo
import kotlinx.android.synthetic.main.fragment_student_details.room
import kotlinx.android.synthetic.main.fragment_student_details.student_name
import kotlinx.android.synthetic.main.fragment_student_details.wing

/**
 * A simple [Fragment] subclass.
 */
class StudentDetailsFragment : Fragment(), BlockingStep {

  lateinit var dataManager: DataManager
  override fun onBackClicked(callback: OnBackClickedCallback?) {

  }

  override fun onCompleteClicked(callback: OnCompleteClickedCallback?) {
    callback?.complete()
  }

  override fun onNextClicked(callback: OnNextClickedCallback?) {
    dataManager.saveData(
        Student(student_name.text.toString(), regNo.text.toString(), hostel.text.toString(),
            wing.text.toString(), room.text.toString()))
    callback?.goToNextStep()
  }

  override fun onSelected() {

  }

  override fun verifyStep(): VerificationError? {
    if (TextUtils.isEmpty(student_name.text.toString()) || TextUtils.isEmpty(
        regNo.text.toString()) || TextUtils.isEmpty(
        room.text.toString()) || TextUtils.isEmpty(
        hostel.text.toString()) || TextUtils.isEmpty(wing.text.toString())) {
      return VerificationError("Fill All Fields")
    } else {
      return null
    }
  }

  override fun onError(error: VerificationError) {

  }


  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater!!.inflate(R.layout.fragment_student_details, container, false)
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


}
