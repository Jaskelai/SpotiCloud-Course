package com.github.kornilovmikhail.spoticloud.data.repository

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SharedPrefStorage(private val sharedPreferences: SharedPreferences) {

    fun writeMessage(key: String, value: Any) {
        Completable.fromAction {
            sharedPreferences.edit().putString(key, value as String).apply()
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun readMessage(key: String): Single<String> = Single.fromCallable {
        sharedPreferences.getString(key, "")
    }
}
