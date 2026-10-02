package com.example.iptvtvpro.repository

import android.content.Context
import com.example.iptvtvpro.data.Channel
import org.json.JSONArray

class FavoritesStore(context: Context) {
    private val p = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    fun isFavorite(id: String) = p.getBoolean(id, false)
    fun toggle(channel: Channel) { p.edit().putBoolean(channel.id, !isFavorite(channel.id)).apply() }
    fun ids(): Set<String> = p.all.filterValues { it == true }.keys
}
