package com.example.iptvtvpro.provider

import android.net.Uri
import com.example.iptvtvpro.data.Channel
import com.example.iptvtvpro.data.Type
import com.example.iptvtvpro.data.PlaylistParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ProviderRouter {
    suspend fun load(type: Type, endpoint: String, username: String? = null, password: String? = null, mac: String? = null): List<Channel> = withContext(Dispatchers.IO) {
        when (type) {
            Type.M3U -> PlaylistParser.parseM3u(URL(endpoint).readText())
            Type.JSON -> PlaylistParser.parseJson(URL(endpoint).readText())
            Type.XTREAM -> XtreamProvider(endpoint, username.orEmpty(), password.orEmpty()).loadLive()
            Type.STALKER -> StalkerClient(endpoint, mac.orEmpty()).channels()
            Type.DIRECT -> listOf(Channel("direct", endpoint, endpoint))
        }
    }
}
