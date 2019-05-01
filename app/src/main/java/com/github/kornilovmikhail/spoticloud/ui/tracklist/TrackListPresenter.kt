package com.github.kornilovmikhail.spoticloud.ui.tracklist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class TrackListPresenter(
    private val tracksUseCase: TracksUseCase,
    private val loginSoundcloudUseCase: LoginSoundcloudUseCase
) : MvpPresenter<TrackListView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        disposables.add(
            loginSoundcloudUseCase.loadLocalSoundCloudToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != "") {
                        loadTracks(it)
                    }
                }, {
                    viewState.showErrorMessage()
                })
        )
    }

    private fun loadTracks(token: String) {
        disposables.add(
            tracksUseCase.loadTracks(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        println("IT IS EMPTY")
                    } else {
                        viewState.showTracks(it)
                    }
                }, {

                })
        )
    }

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun onTrackClicked(track: Track?) {

    }
}
