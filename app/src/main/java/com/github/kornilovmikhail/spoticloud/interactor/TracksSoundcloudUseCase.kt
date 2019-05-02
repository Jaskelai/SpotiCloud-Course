package com.github.kornilovmikhail.spoticloud.interactor

import com.github.kornilovmikhail.spoticloud.core.interfaces.TrackRepository
import com.github.kornilovmikhail.spoticloud.core.model.Track
import io.reactivex.Single

class TracksSoundcloudUseCase(
    private val loginSoundcloudUseCase: LoginSoundcloudUseCase,
    private val tracksRepository: TrackRepository
) {

    fun getTracks(): Single<List<Track>> =
        loginSoundcloudUseCase.loadLocalSoundCloudToken()
            .flatMap {
                if (it != "") {
                    tracksRepository.getTracks(it)
                } else {
                    Single.just(arrayListOf())
                }
            }
}
