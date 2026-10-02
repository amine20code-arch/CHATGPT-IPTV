package com.example.iptvtvpro.provider

import com.example.iptvtvpro.data.Channel
import org.json.JSONArray
import java.net.URLEncoder
import java.net.URL

class XtreamProvider(private val server: String, private val user: String, private val pass: String) {
    fun loadLive(): List<Channel> {
        val base = server.trimEnd('/')
        val u = base + "/player_api.php?username=${enc(user)}&password=${enc(pass)}&action=get_live_streams"
        val a = JSONArray(URL(u).readText())
        return buildList {
            for (i in 0 until a.length()) {
                val o = a.getJSONObject(i)
                add(Channel(o.optString("stream_id"), o.optString("name"), "$base/live/$user/$pass/${o.optString("stream_id")}.m3u8", o.optString("category_name"), o.optString("stream_icon").ifBlank { null }))
            }
        }
    }
    private fun enc(s: String) = URLEncoder.encode(s, "UTF-8")
}
