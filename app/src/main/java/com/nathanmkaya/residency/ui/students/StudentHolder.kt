package com.nathanmkaya.residency.ui.students

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nathanmkaya.residency.model.Student
import kotlinx.android.synthetic.main.student.view.*

/**
 * Created by nathan on 8/14/17.
 */
class StudentHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(student: Student) {
        with(student) {
            itemView.student_name_txt.text = student.name
            itemView.student_regno_txt.text = student.regNo
            itemView.student_room_txt.text = "${student.hostel} : ${student.wing} : ${student.room}"
            itemView.student_img.setImageURI(student.img)
        }
    }
}