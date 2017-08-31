package com.nathanmkaya.residency.bus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject




/**
 * Created by nathan on 8/15/17.
 */
class RxBus {

  private var instance: RxBus? = null

  val bus = PublishRelay.create<Any>().toSerialized()
  private val _bus = PublishSubject.create<Any>()

  fun send(event: Any) {
    bus.accept(event)
  }

  fun _send(event: Any) {
    _bus.onNext(event)
  }

  fun getInstance(): RxBus {
    if (instance == null) {
      instance = RxBus()
    }
    return instance as RxBus
  }

  fun toObservable(): Observable<Any> {
    return bus
  }

  fun asFlowable(): Flowable<Any> {
    return bus.toFlowable(BackpressureStrategy.LATEST)
  }

  fun hasObservable(): Boolean {
    return bus.hasObservers()
  }

  fun _hasObservable(): Boolean {
    return _bus.hasObservers()
  }

  fun <T> event(klazz: Class<T>): Observable<T> {
    return bus.ofType(klazz)
  }

  fun <T> _event(klazz: Class<T>): Observable<T> {
    return _bus.ofType(klazz)
  }
}