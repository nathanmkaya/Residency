package com.nathanmkaya.residency.ui.devices

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nathanmkaya.residency.model.Device
import kotlinx.android.synthetic.main.device_simple.view.*

/**
 * Created by nathan on 8/14/17.
 */
class DeviceHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(device: Device) {
        with(device) {
            itemView.device_type.text = device.type
            itemView.device_make.text = device.make
            itemView.device_serial.text = device.serial
        }
    }
}