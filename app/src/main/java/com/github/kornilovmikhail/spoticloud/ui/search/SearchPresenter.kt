package com.github.kornilovmikhail.spoticloud.ui.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SearchPresenter(private val tracksUseCase: TracksUseCase) : MvpPresenter<SearchView>() {

    private val disposables = CompositeDisposable()

    fun onSearch(keyword: String) {
        disposables.add(
            tracksUseCase.getSearchedTracks(keyword)
                .doOnSubscribe { viewState.showProgressBar() }
                .doAfterTerminate { viewState.hideProgressBar() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showSearchedTracks(it)
                    if (it.isEmpty()) {
                        viewState.showNotFoundMessage()
                    }
                }, {
                    viewState.showErrorMessage()
                })
        )
    }

    fun onTrackClicked(track: Track?) {

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
