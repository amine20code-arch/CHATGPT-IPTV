package com.example.iptvtvpro.data

import org.json.JSONArray
import org.json.JSONObject
import java.net.URI
import java.net.URLDecoder

object PlaylistParser {
    fun parse(text: String): List<Channel> = when {
        text.trimStart().startsWith("#EXTM3U", true) -> parseM3u(text)
        text.trimStart().startsWith("[") || text.trimStart().startsWith("{") -> parseJson(text)
        else -> emptyList()
    }

    fun parseM3u(text: String): List<Channel> {
        val out = mutableListOf<Channel>()
        var meta: Map<String,String> = emptyMap()
        var name = "Channel"
        text.lineSequence().map { it.trim() }.forEach { line ->
            when {
                line.startsWith("#EXTINF", true) -> {
                    val comma = line.indexOf(',')
                    name = if (comma >= 0) line.substring(comma + 1).trim() else "Channel"
                    meta = linkedMapOf<String,String>().apply {
                        attr(line,"group-title")?.let { put("group",it) }
                        attr(line,"tvg-logo")?.let { put("logo",it) }
                        attr(line,"tvg-id")?.let { put("id",it) }
                        attr(line,"tvg-name")?.let { put("tvg-name",it) }
                        attr(line,"catchup")?.let { put("catchup",it) }
                        attr(line,"http-user-agent")?.let { put("User-Agent",it) }
                        attr(line,"http-referrer")?.let { put("Referer",it) }
                    }
                }
                line.startsWith("#EXTVLCOPT:http-referrer=", true) -> meta = meta + ("Referer" to line.substringAfter('='))
                line.startsWith("#EXTVLCOPT:http-user-agent=", true) -> meta = meta + ("User-Agent" to line.substringAfter('='))
                line.isNotEmpty() && !line.startsWith("#") -> out += Channel(name, line, meta["group"] ?: "General", meta["logo"], meta["id"], meta.filterKeys { it=="User-Agent" || it=="Referer" }, meta["catchup"])
            }
        }
        return out
    }

    fun parseJson(text: String): List<Channel> {
        val root = text.trim()
        val arr = if (root.startsWith("[")) JSONArray(root) else JSONObject(root).optJSONArray("channels") ?: JSONArray()
        return (0 until arr.length()).mapNotNull { i ->
            val o = arr.optJSONObject(i) ?: return@mapNotNull null
            val url = o.optString("url").trim()
            if (url.isEmpty()) null else Channel(
                o.optString("name", "Channel"), url,
                o.optString("group", o.optString("group-title", "General")),
                o.optString("logo").ifBlank { null },
                o.optString("tvgId", o.optString("tvg-id")).ifBlank { null },
                buildMap { o.optJSONObject("headers")?.keys()?.forEach { k -> put(k, o.optString(k)) } },
                o.optString("catchup").ifBlank { null }
            )
        }
    }

    fun xtreamApiUrl(base: String, user: String, pass: String): String = base.trimEnd('/') + "/player_api.php?username=" + java.net.URLEncoder.encode(user,"UTF-8") + "&password=" + java.net.URLEncoder.encode(pass,"UTF-8")
    fun isDirectUrl(s: String): Boolean = try { URI(s.trim()).scheme?.lowercase() in setOf("http","https","rtsp","udp","rtp") } catch (_: Exception) { false }
    private fun attr(s: String, key: String): String? = Regex("$key=\\\"([^\\\"]*)\\\"", RegexOption.IGNORE_CASE).find(s)?.groupValues?.get(1)?.let { URLDecoder.decode(it,"UTF-8") }
}
