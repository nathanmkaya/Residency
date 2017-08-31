package com.nathanmkaya.residency.ui.devices

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nathanmkaya.residency.model.Device
import kotlinx.android.synthetic.main.device.view.make_txt
import kotlinx.android.synthetic.main.device.view.serial_txt
import kotlinx.android.synthetic.main.device.view.type_txt

/**
 * Created by nathan on 8/14/17.
 */
class DeviceHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
  fun bind(device: Device) {
    with(device) {
      itemView.type_txt.text = device.type
      itemView.make_txt.text = device.make
      itemView.serial_txt.text = device.serial
    }
  }
}