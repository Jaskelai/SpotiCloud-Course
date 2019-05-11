package com.github.kornilovmikhail.spoticloud.ui.trends

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class TrendsPresenter(private val tracksUseCase: TracksUseCase) : MvpPresenter<TrendsView>() {
    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposables.add(
            tracksUseCase.getTrendsTracks()
                .doOnSubscribe { viewState.showProgressBar() }
                .doAfterTerminate { viewState.hideProgressBar() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showTrendsTracks(it)
                }, {
                    viewState.showErrorMessage()
                })
        )
    }

    fun onTrackClicked(track: Track?) {
        viewState.sendTrackToPlayer(track)
    }

    fun trackAddToFav(track: Track?) {
        disposables.add(
            tracksUseCase.addTrackToFav(track)
                .doOnSubscribe { viewState.showProgressBar() }
                .doAfterTerminate { viewState.hideProgressBar() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showTrackAddedMessage()
                }, {
                    viewState.showErrorMessage()
                })
        )
    }

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}
