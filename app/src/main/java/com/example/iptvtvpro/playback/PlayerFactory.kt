package com.example.iptvtvpro.playback

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultAllocator
import com.example.iptvtvpro.data.Channel

object PlayerFactory {
    fun create(context: Context): ExoPlayer {
        val tracks = DefaultTrackSelector(context).apply {
            setParameters(buildUponParameters().setForceHighestSupportedBitrate(false).setTunnelingEnabled(true))
        }
        val load = DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
            .setBufferDurationsMs(1500, 10000, 500, 1000)
            .setPrioritizeTimeOverSizeThresholds(true)
            .build()
        return ExoPlayer.Builder(context)
            .setTrackSelector(tracks)
            .setLoadControl(load)
            .setMediaSourceFactory(DefaultMediaSourceFactory(context))
            .setAudioAttributes(AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MOVIE).setUsage(C.USAGE_MEDIA).build(), true)
            .build()
    }

    fun mediaItem(channel: Channel): MediaItem = MediaItem.Builder()
        .setUri(channel.url)
        .setMimeType(mime(channel.url))
        .setMediaId(channel.id)
        .apply { if (channel.headers.isNotEmpty()) setRequestMetadata(androidx.media3.common.MediaMetadata.Builder().setExtras(android.os.Bundle().apply { channel.headers.forEach { putString(it.key, it.value) } }).build()) }
        .build()

    private fun mime(url: String): String? = when {
        url.contains(".m3u8", true) -> MimeTypes.APPLICATION_M3U8
        url.contains(".mpd", true) -> MimeTypes.APPLICATION_MPD
        url.startsWith("rtsp://", true) -> MimeTypes.APPLICATION_RTSP
        else -> null
    }
}
