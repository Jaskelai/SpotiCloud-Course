package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Completable
import io.reactivex.Single

class TracksSoundcloudUseCase(
    private val loginSoundcloudUseCase: LoginSoundcloudUseCase,
    private val tracksRepository: TrackRepository
) {

    fun getFavoriteTracks(): Single<List<Track>> =
        loginSoundcloudUseCase.loadLocalSoundCloudToken()
            .flatMap {
                if (it != "") {
                    tracksRepository.getFavoriteTracks(it)
                } else {
                    Single.just(arrayListOf())
                }
            }

    fun getSearchedTracks(keyword: String): Single<List<Track>> = loginSoundcloudUseCase.loadLocalSoundCloudToken()
        .flatMap {
            if (it != "") {
                tracksRepository.getSearchedTracks(it, keyword)
            } else {
                Single.just(arrayListOf())
            }
        }

    fun addTrackToFav(track: Track): Completable = loginSoundcloudUseCase.loadLocalSoundCloudToken()
        .flatMapCompletable {
            if (it != "") {
                tracksRepository.addTrackToFav(it, track.idSource)
            } else {
                Completable.complete()
            }
        }
}
