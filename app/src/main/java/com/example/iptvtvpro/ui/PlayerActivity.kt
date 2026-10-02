package com.example.iptvtvpro.ui

import android.app.Activity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.iptvtvpro.R
import com.example.iptvtvpro.player.PlayerFactory

@UnstableApi class PlayerActivity:Activity(){private lateinit var player:ExoPlayer;private lateinit var view:PlayerView
 override fun onCreate(s:Bundle?){super.onCreate(s);setContentView(R.layout.activity_player);view=findViewById(R.id.playerView);player=PlayerFactory.create(this);view.player=player;val u=intent.getStringExtra("url")?:return;player.setMediaItem(MediaItem.fromUri(u));player.prepare();player.playWhenReady=true}
 override fun onStop(){super.onStop();player.release()}}
