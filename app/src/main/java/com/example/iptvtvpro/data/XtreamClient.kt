package com.example.iptvtvpro.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class XtreamClient(private val baseUrl: String, private val username: String, private val password: String) {
    suspend fun liveChannels(): List<Channel> = withContext(Dispatchers.IO) {
        val u = baseUrl.trimEnd('/') + "/player_api.php?username=${URLEncoder.encode(username,"UTF-8")}&password=${URLEncoder.encode(password,"UTF-8")}&action=get_live_streams"
        val a = JSONArray(get(u))
        (0 until a.length()).map { i ->
            val o = a.getJSONObject(i); val id=o.optString("stream_id")
            Channel(o.optString("name","Channel"), baseUrl.trimEnd()+"/live/$username/$password/$id.ts", o.optString("category_name","General"), o.optString("stream_icon").ifBlank{null}, o.optString("epg_channel_id").ifBlank{null})
        }
    }
    private fun get(u:String):String { val c=URL(u).openConnection() as HttpURLConnection; c.connectTimeout=10000; c.readTimeout=15000; c.requestMethod="GET"; c.inputStream.bufferedReader().use{return it.readText()} }
}
