package com.nathanmkaya.residency.ui.devices

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Device
import com.nathanmkaya.residency.ui.base.BaseActivity
import com.nathanmkaya.residency.utils.DbReference
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_devices.*


class DevicesActivity : BaseActivity(), DeviceContract.View {

    var mPresenter = DevicePresenter(this)
    lateinit var adapter: FirebaseRecyclerAdapter<Device, DeviceHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)
        mPresenter.start()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.scrollToPosition(0)
        device_list.layoutManager = layoutManager
        //device_list.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).build())

        val options = FirebaseRecyclerOptions.Builder<Device>()
                .setQuery(DbReference.devices, Device::class.java)
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

        /*adapter = object : FirebaseRecyclerAdapter<Device, DeviceHolder>(Device::class.java, R.layout.device_simple, DeviceHolder::class.java, DbReference.devices){
          override fun populateViewHolder(viewHolder: DeviceHolder?, model: Device?, position: Int) {
            model?.let { viewHolder?.bind(it) }
          }
        }*/
        device_list.adapter = adapter
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
