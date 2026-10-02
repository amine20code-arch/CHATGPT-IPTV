package com.example.iptvtvpro.diagnostics

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlin.math.max

class PlaybackDiagnostics(private val player: ExoPlayer) : Player.Listener {
    var firstFrameMs: Long = -1
    var bufferingCount = 0
    var lastError: String? = null
    private var start = System.currentTimeMillis()
    override fun onRenderedFirstFrame() { if (firstFrameMs < 0) firstFrameMs = max(0, System.currentTimeMillis() - start) }
    override fun onPlaybackStateChanged(state: Int) { if (state == Player.STATE_BUFFERING) bufferingCount++ }
    override fun onPlayerError(error: PlaybackException) { lastError = "${error.errorCodeName}: ${error.message}" }
    fun reset() { start = System.currentTimeMillis(); firstFrameMs = -1; bufferingCount = 0; lastError = null }
}
