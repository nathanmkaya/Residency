package com.nathanmkaya.residency.ui.devices

import android.util.Log
import com.nathanmkaya.residency.ui.devices.DeviceContract.View

/**
 * Created by nathan on 8/14/17.
 */

class DevicePresenter(view: View) : DeviceContract.Presenter {

  override fun start() {
    Log.d("Presenter", "success")
  }
}
