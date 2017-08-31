package com.nathanmkaya.residency.ui.students.addstudent

import android.content.Context
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

/**
 * Created by nathan on 8/14/17.
 */
class AddStudentStepperAdapter(manager: FragmentManager, context: Context): AbstractFragmentStepAdapter(manager, context) {
  override fun getCount(): Int {
    return 3
  }

  override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
    var builder = StepViewModel.Builder(context)
    when(position){
      0 -> {builder.setTitle("Student details")}
      1 -> {builder.setTitle("Student picture")}
      2 -> {builder.setTitle("Confirm details")}
    }
    return builder.create()
  }

  override fun createStep(position: Int): Step {
    when (position) {
      0 -> return StudentDetailsFragment()
      1 -> return StudentImageFragment()
      2 -> return ConfirmDetailsFragment()
      else -> throw IllegalArgumentException("Unsupported position: $position")
    }
  }
}