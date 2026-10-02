package com.example.iptvtvpro.provider

import com.example.iptvtvpro.data.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URI

/** Ministra/Stalker API v3 client. Provider must permit the client/device to use its API. */
class StalkerClient(private val portal: String, private val mac: String, private val serial: String = "IPTVTVPRO") {
    private var token: String? = null
    private var cookie: String? = null

    suspend fun handshake(): Boolean = withContext(Dispatchers.IO) {
        val r = request("/stalker_portal/api/v3/handshake")
        if (r.code !in 200..299) return@withContext false
        val j = JSONObject(r.body)
        token = j.optString("token").takeIf { it.isNotBlank() }
        cookie = r.cookie
        token != null
    }

    suspend fun channels(): List<Channel> = withContext(Dispatchers.IO) {
        if (token == null) handshake()
        val r = request("/stalker_portal/api/v3/itv")
        if (r.code !in 200..299) return@withContext emptyList()
        val root = JSONObject(r.body)
        val arr = root.optJSONArray("results") ?: JSONArray()
        buildList {
            for (i in 0 until arr.length()) {
                val o = arr.optJSONObject(i) ?: continue
                val cmd = o.optString("cmd")
                if (cmd.isBlank()) continue
                add(Channel(
                    id = o.optString("id", i.toString()),
                    name = o.optString("name", "Channel $i"),
                    url = cmd,
                    group = o.optString("tv_genre_name", "Other"),
                    logo = o.optString("logo").takeIf { it.isNotBlank() }
                ))
            }
        }
    }

    private fun request(path: String): Response {
        val base = portal.trimEnd('/')
        val uri = if (path.startsWith("http")) URI(path) else URI(base + path)
        val c = uri.toURL().openConnection() as HttpURLConnection
        c.connectTimeout = 5000; c.readTimeout = 8000
        c.requestMethod = "GET"
        c.setRequestProperty("User-Agent", "IPTV-TV-Pro/1.0")
        c.setRequestProperty("X-User-Agent", "Model: MAG256; Link: WiFi; Ver: 0.1.0; Locale: en_GB; API: 262; MAC: $mac; Image: 0")
        c.setRequestProperty("Authorization", "Bearer ${token ?: ""}")
        c.setRequestProperty("Cookie", cookie ?: "stb_lang=en; timezone=Europe/Paris")
        return Response(c.responseCode, c.inputStream.bufferedReader().use { it.readText() }, c.getHeaderField("Set-Cookie"))
    }
    private data class Response(val code: Int, val body: String, val cookie: String?)
}
