package com.nathanmkaya.residency.ui.students.studentdetails

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.GravityEnum.CENTER
import com.afollestad.materialdialogs.MaterialDialog
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Device
import com.nathanmkaya.residency.model.Student
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsContract.View
import com.nathanmkaya.residency.utils.DbReference
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_student_details.app_bar
import kotlinx.android.synthetic.main.activity_student_details.fab
import kotlinx.android.synthetic.main.activity_student_details.student_image
import kotlinx.android.synthetic.main.activity_student_details.toolbar
import kotlinx.android.synthetic.main.activity_student_details.toolbar_layout
import kotlinx.android.synthetic.main.add_device.device_make
import kotlinx.android.synthetic.main.add_device.device_serial
import kotlinx.android.synthetic.main.add_device.device_type
import kotlinx.android.synthetic.main.student_details.student_housing
import kotlinx.android.synthetic.main.student_details.student_name
import org.parceler.Parcels
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.student_details.student_device_list


class StudentDetailsActivity : AppCompatActivity(), View {

  lateinit var dialog: MaterialDialog
  lateinit var adapter: FirebaseRecyclerAdapter<Device, DeviceHolder>

  val student: Student by lazy {
    Parcels.unwrap<Student>(intent.getParcelableExtra("student"))
  }

  val reference by lazy {
    intent.getStringExtra("reference")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_student_details)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar_layout.isTitleEnabled = false
    app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
      var isVisible = true
      var scrollRange = -1
      override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout?.getTotalScrollRange()!!;
        }
        if (scrollRange + verticalOffset == 0) {
          toolbar.setTitle("${student.name}");
          isVisible = true;
        } else if(isVisible) {
          toolbar.setTitle("");
          isVisible = false;
        }
      }
    })
    student_name.text = student.name
    student_housing.text = "${student.hostel} ${student.wing} ${student.room}"
    student_image.setImageURI(student.img)
    fab.setOnClickListener {
      addDevice()
    }
    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    layoutManager.scrollToPosition(0)
    student_device_list.layoutManager = layoutManager
    student_device_list.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).build())
    adapter = object : FirebaseRecyclerAdapter<Device, DeviceHolder>(Device::class.java, R.layout.device_simple, DeviceHolder::class.java, DbReference.devices.orderByChild("owner").equalTo(reference)) {
      override fun populateViewHolder(viewHolder: DeviceHolder?, model: Device?, position: Int) {
        model?.let { viewHolder?.bind(it) }
      }
    }
    student_device_list.adapter = adapter
  }

  fun addDevice() {
    dialog = object : MaterialDialog.Builder(this){}.apply {
      title("Add Device")
      customView(R.layout.add_device, true)
      positiveText("Add")
      negativeText("Cancel")
      titleGravity(CENTER)
      onPositive { dialog, _ ->
        val type = dialog.customView?.findViewById<TextInputEditText>(R.id.device_type)
        val make = dialog.customView?.findViewById<TextInputEditText>(R.id.device_make)
        val serial = dialog.customView?.findViewById<TextInputEditText>(R.id.device_serial)
        DbReference.devices.push().setValue(Device(type?.text.toString(), make?.text.toString(), serial?.text.toString(), reference))
        dialog.dismiss()
      }
      onNegative { dialog, which ->
        dialog.dismiss()
      }
    }.build()
    dialog.show()
  }
}
