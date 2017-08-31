package com.nathanmkaya.residency.ui.devices

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.model.Device
import com.nathanmkaya.residency.ui.base.BaseActivity
import com.nathanmkaya.residency.utils.DbReference
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_devices.device_list


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
    device_list.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).build())
    adapter = object : FirebaseRecyclerAdapter<Device, DeviceHolder>(Device::class.java, R.layout.device, DeviceHolder::class.java, DbReference.devices){
      override fun populateViewHolder(viewHolder: DeviceHolder?, model: Device?, position: Int) {
        model?.let { viewHolder?.bind(it) }
      }
    }
    device_list.adapter = adapter
  }
}
