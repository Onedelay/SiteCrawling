package com.onedelay.sitecrawling.util

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Flowable<T>.onNetwork(): Flowable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.onMainThread(): Flowable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}