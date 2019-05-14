package com.github.kornilovmikhail.spoticloud.app.di.module

import com.github.kornilovmikhail.spoticloud.BuildConfig
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudApi
import com.github.kornilovmikhail.spoticloud.data.network.api.SoundCloudV2Api
import com.github.kornilovmikhail.spoticloud.data.network.api.SpotifyApi
import com.github.kornilovmikhail.spoticloud.data.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class RetrofitModule {

    @Provides
    @ApplicationScope
    @Named(RETROFIT_SOUNDCLOUD)
    fun provideRetrofitSoundCloud(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @Named(SOUNDCLOUD_URL) baseURL: String,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @ApplicationScope
    @Named(RETROFIT_SOUNDCLOUD_V2)
    fun provideRetrofitSoundCloudV2(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @Named(SOUNDCLOUD_URL_V2) baseURL: String,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @ApplicationScope
    @Named(RETROFIT_SPOTIFY)
    fun provideRetrofitSpotify(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @Named(SPOTIFY_URL) baseURL: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @ApplicationScope
    fun provideSoundcloudApi(@Named(RETROFIT_SOUNDCLOUD) retrofit: Retrofit): SoundCloudApi =
        retrofit.create(SoundCloudApi::class.java)

    @Provides
    @ApplicationScope
    fun provideSoundcloudV2Api(@Named(RETROFIT_SOUNDCLOUD_V2) retrofit: Retrofit): SoundCloudV2Api =
        retrofit.create(SoundCloudV2Api::class.java)

    @Provides
    @ApplicationScope
    fun provideSpotifyApi(@Named(RETROFIT_SPOTIFY) retrofit: Retrofit): SpotifyApi =
        retrofit.create(SpotifyApi::class.java)

    @Provides
    @ApplicationScope
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @ApplicationScope
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(interceptor: TokenInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @ApplicationScope
    @Named(SOUNDCLOUD_URL)
    fun provideSoundCloudURL(): String = BuildConfig.SOUNDCLOUD_URL

    @Provides
    @ApplicationScope
    @Named(SOUNDCLOUD_URL_V2)
    fun provideSoundCloudURLV2(): String = BuildConfig.SOUNDCLOUD_URL_V2

    @Provides
    @ApplicationScope
    @Named(SPOTIFY_URL)
    fun provideSpotifyURL(): String = BuildConfig.SPOTIFY_URL

    @Provides
    @ApplicationScope
    fun provideRefreshInterceptor(): TokenInterceptor =
        TokenInterceptor()

    companion object {
        private const val SOUNDCLOUD_URL = "SOUNDCLOUD_URL"
        private const val SOUNDCLOUD_URL_V2 = "SOUNDCLOUD_URL_V2"
        private const val SPOTIFY_URL = "SPOTIFY_URL"
        private const val RETROFIT_SOUNDCLOUD = "RETROFIT_SOUNDLOUD"
        private const val RETROFIT_SOUNDCLOUD_V2 = "RETROFIT_SOUNDLOUD_V2"
        private const val RETROFIT_SPOTIFY = "RETROFIT_SPOTIFY"
    }
}
