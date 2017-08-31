package com.nathanmkaya.residency.ui.auth

import com.nathanmkaya.residency.ui.base.BasePresenter
import com.nathanmkaya.residency.ui.base.BaseView

/**
 * Created by nathan on 8/14/17.
 */
interface LoginContract {
  interface View: BaseView<Presnter>
  interface Presnter: BasePresenter
}