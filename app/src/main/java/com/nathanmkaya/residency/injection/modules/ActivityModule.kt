package com.nathanmkaya.residency.injection.modules

import com.nathanmkaya.residency.ui.auth.LoginActivity
import com.nathanmkaya.residency.ui.auth.LoginActivityModule
import com.nathanmkaya.residency.ui.devices.DevicesActivity
import com.nathanmkaya.residency.ui.devices.DevicesActivityModule
import com.nathanmkaya.residency.ui.news.NewsActivity
import com.nathanmkaya.residency.ui.news.NewsActivityModule
import com.nathanmkaya.residency.ui.students.StudentsActivity
import com.nathanmkaya.residency.ui.students.StudentsActivityModule
import com.nathanmkaya.residency.ui.students.addstudent.AddStudentActivity
import com.nathanmkaya.residency.ui.students.addstudent.AddStudentActivityModule
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsActivity
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by nathan on 8/21/17.
 */
@Module
abstract class ActivityModule {

  @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
  abstract fun bindLoginActivity(): LoginActivity

  @ContributesAndroidInjector(modules = arrayOf(DevicesActivityModule::class))
  abstract fun bindDevicesActivity(): DevicesActivity

  @ContributesAndroidInjector(modules = arrayOf(NewsActivityModule::class))
  abstract fun bindNewsActivity(): NewsActivity

  @ContributesAndroidInjector(modules = arrayOf(AddStudentActivityModule::class))
  abstract fun bindAddStudentActivity(): AddStudentActivity

  @ContributesAndroidInjector(modules = arrayOf(StudentDetailsActivityModule::class))
  abstract fun bindStudentDetailsActivity(): StudentDetailsActivity

  @ContributesAndroidInjector(modules = arrayOf(StudentsActivityModule::class))
  abstract fun bindStudentsActivity(): StudentsActivity
}