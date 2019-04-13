package com.github.kornilovmikhail.spoticloud.app.di.module

import android.content.Context
import android.content.SharedPreferences
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SharedPrefModule {

    @Provides
    @ApplicationScope
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(NAME_SHAREDPREF, Context.MODE_PRIVATE)

    companion object {
        private const val NAME_SHAREDPREF: String = "PREFS"
    }
}
