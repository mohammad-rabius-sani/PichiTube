package com.pichitube.app.feature.player

import android.content.Context
import android.os.Build
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Shared playback state visible across the whole app (mini-player)
data class PlaybackState(
    val videoId: String = "",
    val title: String = "",
    val channelName: String = "",
    val thumbnailUrl: String = "",
    val isPlaying: Boolean = false,
    val isVisible: Boolean = false,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
)

class SharedPlaybackState {
    private val _state = MutableStateFlow(PlaybackState())
    val state: StateFlow<PlaybackState> = _state.asStateFlow()

    fun update(block: PlaybackState.() -> PlaybackState) {
        _state.value = _state.value.block()
    }

    fun dismiss() { _state.value = PlaybackState() }
    fun togglePlayPause() { _state.value = _state.value.copy(isPlaying = !_state.value.isPlaying) }
}
