package com.nathanmkaya.residency.ui.students.studentdetails

import com.nathanmkaya.residency.ui.base.BasePresenter
import com.nathanmkaya.residency.ui.base.BaseView

/**
 * Created by nathan on 8/14/17.
 */
interface StudentDetailsContract {
    interface View : BaseView<Presenter>
    interface Presenter : BasePresenter
}