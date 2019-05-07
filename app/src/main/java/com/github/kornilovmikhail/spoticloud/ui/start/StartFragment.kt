package com.github.kornilovmikhail.spoticloud.ui.start

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

class StartFragment : MvpAppCompatFragment(), StartView {

    @Inject
    @InjectPresenter
    lateinit var startPresenter: StartPresenter

    private var callback: CallbackFromFragments? = null

    @ProvidePresenter
    fun getPresenter(): StartPresenter = startPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .startSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback = context as CallbackFromFragments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_soundcloud.setOnClickListener { startPresenter.onSoundcloudButtonClicked() }
        btn_start_spotify.setOnClickListener { startPresenter.onSpotifyButtonClicked(REQUEST_CODE_SPOTIFY) }
        MySupportAppNavigator.setFragment(this)
    }

    override fun onResume() {
        super.onResume()
        startPresenter.onResume()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        startPresenter.onCleared()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SPOTIFY) {
                val dataBundle = data?.getBundleExtra(EXTRA_AUTH_RESPONSE)
                val any = dataBundle?.get(RESPONSE_KEY)
                startPresenter.onSpotifyLoginResult(any)
            }
        }
    }

    override fun disableSpotifyButton() {
        btn_start_spotify.isEnabled = false
        context?.let {
            btn_start_spotify.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGrey))
            tv_start_choice.setTextColor(ContextCompat.getColor(it, R.color.colorGrey))
        }
    }

    override fun disableSoundCloudButton() {
        btn_start_soundcloud.isEnabled = false
        context?.let {
            btn_start_soundcloud.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGrey))
            tv_start_choice.setTextColor(ContextCompat.getColor(it, R.color.colorGrey))
        }
    }

    override fun showSnackBar() {
        val snackbar =
            Snackbar.make(layout_start, "", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.go_to_app) {
                    startPresenter.onSnackBarClicked()
                }
        context?.let {
            snackbar.setActionTextColor(ContextCompat.getColor(it, R.color.white))
            snackbar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.colorDarkAccent))
        }
        snackbar.show()
    }

    override fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun showBottomBar() {
        callback?.enableBottomNavBar()
    }

    override fun showToolbar() {
        callback?.enableToolbar()
    }

    companion object {
        private const val REQUEST_CODE_SPOTIFY = 898
        private const val RESPONSE_KEY = "response"
        private const val EXTRA_AUTH_RESPONSE = "EXTRA_AUTH_RESPONSE"

        fun getInstance(): StartFragment = StartFragment()
    }
}
