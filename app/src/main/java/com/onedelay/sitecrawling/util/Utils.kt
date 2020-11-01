package com.onedelay.sitecrawling.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Single<T>.onNetwork(): Single<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Single<T>.onMainThread(): Single<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}