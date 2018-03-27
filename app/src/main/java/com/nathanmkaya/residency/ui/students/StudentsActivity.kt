package com.nathanmkaya.residency.ui.students

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Student
import com.nathanmkaya.residency.ui.base.BaseActivity
import com.nathanmkaya.residency.ui.students.StudentsContract.View
import com.nathanmkaya.residency.ui.students.addstudent.AddStudentActivity
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsActivity
import com.nathanmkaya.residency.utils.DbReference
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_students.*
import kotlinx.android.synthetic.main.app_bar_base.*
import org.parceler.Parcels


class StudentsActivity : BaseActivity(), View {

    lateinit var adapter: FirebaseRecyclerAdapter<Student, StudentHolder>
    val students = DbReference.students

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.scrollToPosition(0)
        students_list.layoutManager = layoutManager

        val options = FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(students, Student::class.java)
                .setLifecycleOwner(this)
                .build()

        adapter = object : FirebaseRecyclerAdapter<Student, StudentHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StudentHolder {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.student, parent, false)
                /*val viewHolder = StudentHolder(view)
                viewHolder.itemView.setOnClickListener({

                })
                viewHolder.itemView.setOnLongClickListener({


                    true
                })*/

                return StudentHolder(view)
            }

            override fun onBindViewHolder(holder: StudentHolder, position: Int, model: Student) {
                holder.bind(model)
                holder.itemView.setOnLongClickListener({
                    Toast.makeText(this@StudentsActivity, "Long Clicked at $position", Toast.LENGTH_SHORT).show()
                    true
                })
                holder.itemView.setOnClickListener {
                    startActivity(Intent(this@StudentsActivity, StudentDetailsActivity::class.java).apply {
                        putExtra("reference", getRef(position).key)
                        model.let { putExtra("student", Parcels.wrap(it)) }
                    })
                }
            }

        }

        /*adapter = object : FirebaseRecyclerAdapter<Student, StudentHolder>(Student::class.java, R.layout.student, StudentHolder::class.java, students) {
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
        }*/

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                students_list.smoothScrollToPosition(itemCount)
            }
        })

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

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
