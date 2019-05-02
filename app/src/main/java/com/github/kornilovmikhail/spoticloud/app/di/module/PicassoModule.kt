package com.github.kornilovmikhail.spoticloud.app.di.module

import android.content.Context
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class PicassoModule {
    @Provides
    @ApplicationScope
    fun providePicasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso =
        Picasso.Builder(context).downloader(okHttp3Downloader).build()

    @Provides
    @ApplicationScope
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader = OkHttp3Downloader(okHttpClient)
}
