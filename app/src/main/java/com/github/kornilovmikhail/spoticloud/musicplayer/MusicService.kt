package com.github.kornilovmikhail.spoticloud.musicplayer

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.PlayingStatusEnum
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.graphics.Color
import android.os.*
import android.widget.RemoteViews
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity


class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener, CallbackMessageService {

    @Inject
    lateinit var binder: OnBinder

    @Inject
    lateinit var loginSoundloudUseCase: LoginSoundcloudUseCase

    @Inject
    lateinit var loginSpotifyUseCase: LoginSpotifyUseCase

    @Inject
    lateinit var tracksUseCase: TracksUseCase

    private var player: MediaPlayer? = null
    private var position: Int? = null
    private var track: Track? = null
    private lateinit var state: PlayingStatusEnum
    private val handler = IncomingHandler(this)
    private val messengerReceiver = Messenger(handler)
    private var messengerResponse: Messenger? = null

    private val disposables = CompositeDisposable()

    inner class OnBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        App.component
            .musicServiceSubComponentBuilder()
            .build()
            .inject(this)
        isServiceStarted = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            setupTrackToPlay(intent)
        }
        startForegroundNotification()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = messengerReceiver.binder

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
        clearMusicPlayer()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        state = PlayingStatusEnum.PlAYING
    }

    override fun onCompletion(p0: MediaPlayer?) {
        //do nothing after track ending
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean = false

    override fun pausePlaying() {
        player?.let {
            it.pause()
            state = PlayingStatusEnum.PAUSED
        }
    }

    override fun resumePlaying() {
        player?.let {
            it.start()
            it.seekTo(it.currentPosition)
        }
    }

    private fun setupTrackToPlay(intent: Intent) {
        position = intent.getIntExtra(MusicServiceHelper.TRACK_ID, -1)
        position?.let {
            findTrack(it)
        }
    }

    private fun findTrack(position: Int) {
        terminatePrevious()
        disposables.add(
            tracksUseCase.findTrackById(position)
                .subscribe({
                    track = it
                    getToken(it.streamService)
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun getToken(streamServiceEnum: StreamServiceEnum) {
        val token = run {
            when (streamServiceEnum) {
                StreamServiceEnum.SOUNDCLOUD -> {
                    loginSoundloudUseCase.loadLocalSoundCloudToken()
                }
                StreamServiceEnum.SPOTIFY -> {
                    loginSpotifyUseCase.loadLocalSpotifyToken()
                }
            }
        }
        disposables.add(
            token.subscribe({
                playTrack(it)
            }, {
                it.printStackTrace()
            })
        )
    }

    private fun playTrack(token: String) {
        disposables.add(
            Completable.fromAction {
                createMediaPlayerIfNeeded()
                player?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                        .build()
                )
                player?.setDataSource("${track?.streamUrl}?oauth_token=$token")
                player?.prepareAsync()
            }
                .subscribeOn(Schedulers.io())
                .subscribe({
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun startForegroundNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = createNotification(pendInt)
        startForeground(FOREGROUND_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MUSIC_CHANNEL_ID,
                getString(R.string.music_channel_name),
                NotificationManager.IMPORTANCE_LOW
            )
            channel.apply {
                lightColor = Color.BLUE
                description = getString(R.string.music_channel_description)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(pendIntent: PendingIntent): Notification {
        val notificationView = RemoteViews(applicationContext.packageName, R.layout.notification_player)
        notificationView.setTextViewText(R.id.tv_notification_title, track?.title)
        return NotificationCompat.Builder(this, MUSIC_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendIntent)
            .setContent(notificationView)
            .build()
    }

    private fun createMediaPlayerIfNeeded() {
        if (player == null) {
            player = MediaPlayer()
            player?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            player?.setOnPreparedListener(this)
            player?.setOnCompletionListener(this)
            player?.setOnErrorListener(this)
        } else {
            player?.reset()
        }
    }

    fun start() {
        if (player != null) {
            player?.start()
            state = PlayingStatusEnum.PlAYING
        }
    }


    private fun terminatePrevious() {
        disposables.clear()
    }

    private fun clearMusicPlayer() {
        player?.reset()
        player?.release()
        player = null
    }

    class IncomingHandler(private val callback: CallbackMessageService) : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MusicServiceConnection.MESSAGE_TYPE_FOOTER_INIT -> {
                    callback.sendFooterInit(msg.replyTo)
                }
                MusicServiceConnection.MESSAGE_TYPE_FOOTER_PAUSE -> {
                    callback.pausePlaying()
                }
                MusicServiceConnection.MESSAGE_TYPE_FOOTER_RESUME -> {
                    callback.resumePlaying()
                }
            }
        }
    }

    override fun sendFooterInit(messenger: Messenger) {
        val bundle = Bundle()
        bundle.putString(MusicServiceConnection.MESSAGE_LINK_COVER_TRACK, track?.artworkUrl)
        bundle.putString(MusicServiceConnection.MESSAGE_TITLE_TRACK, track?.title)
        val backMsg = Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_FOOTER_RESPONSE)
        backMsg.data = bundle
        messengerResponse = messenger
        messengerResponse?.send(backMsg)
    }

    companion object {
        var isServiceStarted = false
        private const val FOREGROUND_ID = 1338
        private const val MUSIC_NOTIFICATION_ID = 1339
        private const val MUSIC_CHANNEL_ID = "CHANNEL_MUSIC_SPOTICLOUD"
    }
}
