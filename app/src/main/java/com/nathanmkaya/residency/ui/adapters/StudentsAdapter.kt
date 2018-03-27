package com.nathanmkaya.residency.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.ui.students.StudentHolder

/**
 * Created by nathan on 8/14/17.
 */
class StudentsAdapter : RecyclerView.Adapter<StudentHolder>() {
    override fun onBindViewHolder(holder: StudentHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO(
                "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.student, parent, false)
        return StudentHolder(view)
    }
}