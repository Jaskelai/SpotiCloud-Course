package com.github.kornilovmikhail.spoticloud.musicplayerservice

import android.app.Service
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.PlayingStatusEnum
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.graphics.Color
import android.os.Messenger
import android.os.Message
import android.os.Handler
import android.os.Bundle
import android.os.IBinder
import android.os.Build
import android.os.PowerManager
import android.widget.RemoteViews
import com.github.kornilovmikhail.spoticloud.BuildConfig
import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.ui.main.MainActivity
import com.spotify.sdk.android.player.*
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener, CallbackMessageService, Player.OperationCallback {

    @Inject
    lateinit var loginSoundloudUseCase: LoginSoundcloudUseCase

    @Inject
    lateinit var loginSpotifyUseCase: LoginSpotifyUseCase

    @Inject
    lateinit var tracksUseCase: TracksUseCase

    @Inject
    lateinit var context: Context

    private var notificationView: RemoteViews? = null
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null

    private var mediaPlayer: MediaPlayer? = null
    private var spotifyPlayer: Player? = null

    private var trackId: Int? = null
    private var trackStreamUrl: String? = null
    private var trackArtworkUrl: String? = null
    private var trackArtworkUrlHQ: String? = null
    private var trackStreamService: StreamServiceEnum? = null
    private var trackTitle: String? = null
    private var trackAuthor: String? = null
    private var trackDuration: Long? = null
    private lateinit var state: PlayingStatusEnum

    private val messengerReceiver = Messenger(IncomingHandler(this))
    private var messengerResponse: Messenger? = null

    private val disposables = CompositeDisposable()

    //lifecycle

    override fun onCreate() {
        super.onCreate()
        App.component
            .inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceStarted = true
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

    //send track to player

    private fun setupTrackToPlay(intent: Intent) {
        trackId = intent.getIntExtra(MusicServiceHelper.TRACK_ID, -1)
        trackStreamUrl = intent.getStringExtra(MusicServiceHelper.TRACK_URL)
        trackArtworkUrl = intent.getStringExtra(MusicServiceHelper.TRACK_COVER_LINK)
        trackArtworkUrlHQ = intent.getStringExtra(MusicServiceHelper.TRACK_COVER_LINK_HQ)
        trackStreamService = StreamServiceEnum.valueOf(intent.getStringExtra(MusicServiceHelper.TRACK_SOURCE))
        trackTitle = intent.getStringExtra(MusicServiceHelper.TRACK_TITLE)
        trackAuthor = intent.getStringExtra(MusicServiceHelper.TRACK_AUTHOR)
        sendTokenToPlay()
    }

    private fun sendTokenToPlay() {
        val token = trackStreamService?.let {
            when (it) {
                StreamServiceEnum.SOUNDCLOUD -> {
                    loginSoundloudUseCase.loadLocalSoundCloudToken()
                }
                StreamServiceEnum.SPOTIFY -> {
                    loginSpotifyUseCase.loadLocalSpotifyToken()
                }
            }
        }
        token?.let {
            disposables.add(
                it.subscribe({
                    playTrack(it)
                }, { })
            )
        }
    }

    private fun playTrack(token: String) {
        disposables.add(
            Completable.fromAction {
                if (trackStreamService == StreamServiceEnum.SPOTIFY) {
                    playSpotifyTrack(token)
                } else {
                    playSoundCloudTrack(token)
                }
            }
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
        state = PlayingStatusEnum.PlAYING
        handleSeekBar()
    }

    private fun handleSeekBar() {
        disposables.add(
            Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (state == PlayingStatusEnum.PlAYING) {
                        if (trackStreamService == StreamServiceEnum.SPOTIFY) {
                            sendSeekBarUpdate(messengerResponse, 0)
                        } else {
                            mediaPlayer?.currentPosition?.let {
                                sendSeekBarUpdate(messengerResponse, it)
                            }
                        }
                    }
                }, {
                })
        )
    }

    //play spotify track

    private fun playSpotifyTrack(token: String) {
        clearMusicPlayer()
        spotifyPlayer?.pause(this)
        configSpotifyPlayer(token)
        Thread.sleep(2000)
        configSpotifyPlayer(token)
        spotifyPlayer?.playUri(null, trackStreamUrl, 0, 0)
    }

    private fun configSpotifyPlayer(token: String) {
        val playerConfig = Config(context, token, BuildConfig.SPOTIFY_CLIENT_ID)
        Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
            override fun onInitialized(spotifyPlayer: SpotifyPlayer) {
                this@MusicService.spotifyPlayer = spotifyPlayer
            }

            override fun onError(throwable: Throwable) {
            }
        })
    }

    override fun onError(p0: Error?) {
    }

    override fun onSuccess() {
    }

    //play soundcloud track

    private fun playSoundCloudTrack(token: String) {
        clearSpotifyPlayer()
        createMediaPlayer()
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                .build()
        )
        mediaPlayer?.setDataSource("$trackStreamUrl?oauth_token=$token")
        mediaPlayer?.prepareAsync()
    }

    private fun createMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.apply {
                setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                setOnPreparedListener(this@MusicService)
                setOnCompletionListener(this@MusicService)
                setOnErrorListener(this@MusicService)
            }
        } else {
            mediaPlayer?.reset()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean = false

    //pause track

    override fun pausePlaying() {
        if (trackStreamService == StreamServiceEnum.SPOTIFY) {
            spotifyPlayer?.pause(this)
        } else {
            mediaPlayer?.let {
                it.pause()
            }
        }
        state = PlayingStatusEnum.PAUSED
    }

    //resume track

    override fun resumePlaying() {
        if (trackStreamService == StreamServiceEnum.SPOTIFY) {
            spotifyPlayer?.resume(this)
        } else {
            mediaPlayer?.let {
                it.start()
                it.seekTo(it.currentPosition)
            }
        }
        state = PlayingStatusEnum.PlAYING
    }

    //play prev

    override fun playPrev(messenger: Messenger) {
        trackId?.let {
            disposables.add(
                tracksUseCase.getTrackById(it - 1)
                    .subscribe({ track ->
                        getInfoFromTrack(track)
                        updateNotifiaction()
                        sendFooterUpdate(messenger)
                        sendPlayerUpdate(messenger)
                        sendTokenToPlay()
                    }, {

                    })
            )
        }
    }

    //play next

    override fun playNext(messenger: Messenger) {
        trackId?.let {
            disposables.add(
                tracksUseCase.getTrackById(it + 1)
                    .subscribe({ track ->
                        getInfoFromTrack(track)
                        updateNotifiaction()
                        sendFooterUpdate(messenger)
                        sendPlayerUpdate(messenger)
                        sendTokenToPlay()
                    }, {

                    })
            )
        }
    }

    //notification

    private fun startForegroundNotification() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager as NotificationManager)
        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = createNotification(pendInt)
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
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
        notificationView = RemoteViews(context.packageName, R.layout.notification_player)
        notificationView?.setTextViewText(R.id.tv_notification_title, trackTitle)

        notificationBuilder = NotificationCompat.Builder(this, MUSIC_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendIntent)
            .setContent(notificationView)
        return (notificationBuilder as NotificationCompat.Builder).build()
    }

    private fun updateNotifiaction() {
        notificationView?.setTextViewText(R.id.tv_notification_title, trackTitle)
        notificationBuilder?.setCustomContentView(notificationView)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.notify(FOREGROUND_NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun clearMusicPlayer() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun clearSpotifyPlayer() {
        spotifyPlayer?.pause(this)
        spotifyPlayer = null
    }

    private fun getInfoFromTrack(track: Track) {
        trackId = track.id
        trackStreamUrl = track.streamUrl
        trackArtworkUrl = track.artworkLowSizeUrl ?: track.artworkUrl
        trackArtworkUrlHQ = track.artworkUrl
        trackStreamService = track.streamService
        trackTitle = track.title
        trackAuthor = track.author.username
    }

    // handler for messages from activity

    class IncomingHandler(private val callback: CallbackMessageService) : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MusicServiceConnection.MESSAGE_TYPE_FOOTER_INIT -> {
                    callback.sendFooterUpdate(msg.replyTo)
                }
                MusicServiceConnection.MESSAGE_TYPE_PAUSE -> { callback.pausePlaying() }
                MusicServiceConnection.MESSAGE_TYPE_RESUME -> { callback.resumePlaying() }
                MusicServiceConnection.MESSAGE_TYPE_PREV -> { callback.playPrev(msg.replyTo) }
                MusicServiceConnection.MESSAGE_TYPE_NEXT -> { callback.playNext(msg.replyTo) }
                MusicServiceConnection.MESSAGE_TYPE_PLAYER_STARTED -> { callback.sendPlayerUpdate(msg.replyTo) }
            }
        }
    }

    //messages to activity

    override fun sendFooterUpdate(messenger: Messenger) {
        val bundle = Bundle()
        bundle.putString(MusicServiceConnection.MESSAGE_LINK_COVER_TRACK, trackArtworkUrl)
        bundle.putString(MusicServiceConnection.MESSAGE_TITLE_TRACK, trackTitle)
        val backMsg = Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_FOOTER_RESPONSE)
        backMsg.data = bundle
        messengerResponse = messenger
        messengerResponse?.send(backMsg)
    }

    override fun sendPlayerUpdate(messenger: Messenger) {
        val backMsg = Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_PLAYER_RESPONSE)
        backMsg.data = putBundleForMusicPlayer()
        messengerResponse = messenger
        messengerResponse?.send(backMsg)
    }

    private fun putBundleForMusicPlayer(): Bundle {
        val bundle = Bundle()
        bundle.apply {
            putString(MusicServiceConnection.MESSAGE_LINK_COVER_TRACK, trackArtworkUrlHQ)
            putString(MusicServiceConnection.MESSAGE_TITLE_TRACK, trackTitle)
            putString(MusicServiceConnection.MESSAGE_AUTHOR_TRACK, trackAuthor)
            putString(MusicServiceConnection.MESSAGE_SOURCE_TRACK, trackStreamService?.name)
            trackDuration = if (trackStreamService == StreamServiceEnum.SPOTIFY) {
                spotifyPlayer?.metadata?.currentTrack?.durationMs
            } else {
                mediaPlayer?.duration?.toLong()
            }
            trackDuration?.let {
                putLong(MusicServiceConnection.MESSAGE_DURATION_TRACK, it)
            }
        }
        return bundle
    }

    private fun sendSeekBarUpdate(messenger: Messenger?, value: Int) {
        val bundle = Bundle()
        bundle.putInt(MusicServiceConnection.MESSAGE_BAR_TRACK, value)
        val backMsg = Message.obtain(null, MusicServiceConnection.MESSAGE_TYPE_PLAYER_SEEK_BAR)
        backMsg.data = bundle
        messenger?.send(backMsg)
    }

    companion object {
        var isServiceStarted = false
        private const val FOREGROUND_NOTIFICATION_ID = 1338
        private const val MUSIC_CHANNEL_ID = "CHANNEL_MUSIC_SPOTICLOUD"
    }
}
