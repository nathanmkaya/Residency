package com.nathanmkaya.residency.ui.news

import com.nathanmkaya.residency.ui.base.BasePresenter
import com.nathanmkaya.residency.ui.base.BaseView

/**
 * Created by nathan on 8/14/17.
 */
interface NewsContract {
  interface View: BaseView<Presenter>
  interface Presenter: BasePresenter
}