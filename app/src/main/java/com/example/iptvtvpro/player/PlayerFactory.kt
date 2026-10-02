package com.example.iptvtvpro.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory

@UnstableApi object PlayerFactory {
    fun create(context: Context): ExoPlayer {
        val http=DefaultHttpDataSource.Factory().setConnectTimeoutMs(8000).setReadTimeoutMs(15000).setAllowCrossProtocolRedirects(true)
        val load=DefaultLoadControl.Builder().setBufferDurationsMs(2500,15000,700,1200).setPrioritizeTimeOverSizeThresholds(true).build()
        return ExoPlayer.Builder(context).setLoadControl(load).setMediaSourceFactory(DefaultMediaSourceFactory(http)).build()
    }
    fun item(channelUrl:String, headers:Map<String,String>=emptyMap()):MediaItem=MediaItem.Builder().setUri(channelUrl).setRequestMetadata(MediaItem.RequestMetadata.Builder().setMediaUri(android.net.Uri.parse(channelUrl)).build()).setCustomCacheKey(null).build()
}
