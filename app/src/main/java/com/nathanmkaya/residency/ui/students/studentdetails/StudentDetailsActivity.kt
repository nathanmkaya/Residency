package com.nathanmkaya.residency.ui.students.studentdetails

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.GravityEnum.CENTER
import com.afollestad.materialdialogs.MaterialDialog
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Device
import com.nathanmkaya.residency.model.Student
import com.nathanmkaya.residency.ui.students.studentdetails.StudentDetailsContract.View
import com.nathanmkaya.residency.utils.DbReference
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_student_details.*
import kotlinx.android.synthetic.main.student_details.*
import org.parceler.Parcels


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
                } else if (isVisible) {
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

        val options = FirebaseRecyclerOptions.Builder<Device>()
                .setQuery(DbReference.devices.orderByChild("owner").equalTo(reference), Device::class.java)
                .build()

        adapter = object : FirebaseRecyclerAdapter<Device, DeviceHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceHolder {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.device_simple, parent, false)

                return DeviceHolder(view)
            }

            override fun onBindViewHolder(holder: DeviceHolder, position: Int, model: Device) {
                holder.bind(model)
            }

        }

        /*adapter = object : FirebaseRecyclerAdapter<Device, DeviceHolder>(Device::class.java, R.layout.device_simple, DeviceHolder::class.java, DbReference.devices.orderByChild("owner").equalTo(reference)) {
          override fun populateViewHolder(viewHolder: DeviceHolder?, model: Device?, position: Int) {
            model?.let { viewHolder?.bind(it) }
          }
        }*/
        student_device_list.adapter = adapter
    }

    fun addDevice() {
        dialog = object : MaterialDialog.Builder(this) {}.apply {
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
            onNegative { dialog, _ ->
                dialog.dismiss()
            }
        }.build()
        dialog.show()
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
