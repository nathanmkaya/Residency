package com.nathanmkaya.residency.ui.base

/**
 * Created by nathan on 8/8/17.
 */
interface BaseContract {
  interface View: BaseView<Presenter>
  interface Presenter: BasePresenter
}