package com.nathanmkaya.residency.ui.students

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Student
import com.nathanmkaya.residency.ui.base.BaseActivity
import com.nathanmkaya.residency.ui.students.StudentsContract.View
import com.nathanmkaya.residency.ui.students.addstudent.AddStudentActivity
import com.nathanmkaya.residency.utils.DbReference
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_students.students_list
import kotlinx.android.synthetic.main.app_bar_base.fab
import android.widget.Toast
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsActivity
import org.parceler.Parcels


class StudentsActivity : BaseActivity(), View {

  lateinit var adapter : FirebaseRecyclerAdapter<Student, StudentHolder>
  val students = DbReference.students

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_students)

    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    layoutManager.scrollToPosition(0)
    students_list.layoutManager = layoutManager

    adapter = object : FirebaseRecyclerAdapter<Student, StudentHolder>(Student::class.java, R.layout.student, StudentHolder::class.java, students) {
      override fun populateViewHolder(viewHolder: StudentHolder?, student: Student?, position: Int) {
        student?.let { viewHolder?.bind(it) }
        viewHolder?.itemView?.setOnLongClickListener({
          Toast.makeText(this@StudentsActivity, "Long Clicked $position", Toast.LENGTH_SHORT).show()
          true
        })
        viewHolder?.itemView?.setOnClickListener {
          startActivity(Intent(this@StudentsActivity, StudentDetailsActivity::class.java).apply {
            putExtra("reference", getRef(position).key)
            student?.let { putExtra("student", Parcels.wrap(it)) }
          })
        }
      }

      override fun onBindViewHolder(viewHolder: StudentHolder?, position: Int) {
        super.onBindViewHolder(viewHolder, position)

      }
    }

    students_list.adapter = adapter

    fab.setImageDrawable(ContextCompat.getDrawable(this@StudentsActivity, R.drawable.student_add))
    fab.setOnClickListener { _ ->
      startActivity(Intent(this@StudentsActivity, AddStudentActivity::class.java))
      overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    /*ItemClickSupport.addTo(students_list).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
      override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: android.view.View) {

      }

    })*/
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    fab.show()
  }
}
