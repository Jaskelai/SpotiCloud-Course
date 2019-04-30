package com.github.kornilovmikhail.spoticloud.ui.loginsoundcloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import kotlinx.android.synthetic.main.fragment_soundcloud_login.*
import javax.inject.Inject

class SoundcloudLoginFragment : MvpAppCompatFragment(), SoundcloudloginView {

    @Inject
    @InjectPresenter
    lateinit var soundcloudLoginPresenter: SoundcloudLoginPresenter

    @ProvidePresenter
    fun getPresenter(): SoundcloudLoginPresenter = soundcloudLoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .loginSoundcloudSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_soundcloud_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login_back.setOnClickListener { soundcloudLoginPresenter.onBackArrowClicked() }
        btn_login_signin.setOnClickListener {
            val email = et_login_email.text.toString()
            val password = et_login_password.text.toString()
            soundcloudLoginPresenter.onSignInClicked(email, password)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundcloudLoginPresenter.onCleared()
    }

    override fun showProgressBar() {
        login_soundcloud_progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun hideProgressBar() {
        login_soundcloud_progressBar.visibility = ProgressBar.INVISIBLE
    }

    override fun displayError() {
        tv_login_error.text = getString(R.string.login_error)
    }
}
