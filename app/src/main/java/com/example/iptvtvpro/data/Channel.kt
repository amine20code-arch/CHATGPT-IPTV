package com.example.iptvtvpro.data

data class Channel(
    val id: String,
    val name: String,
    val url: String,
    val group: String = "Other",
    val logo: String? = null,
    val tvgId: String? = null,
    val tvgName: String? = null,
    val headers: Map<String, String> = emptyMap(),
    val catchupTemplate: String? = null,
    val catchupDays: Int = 0
)

data class EpgProgram(val channelId: String, val title: String, val startEpochMs: Long, val endEpochMs: Long, val description: String? = null)

data class ProviderAccount(val id: String, val name: String, val type: Type, val endpoint: String, val username: String? = null)
enum class Type { M3U, JSON, XTREAM, STALKER, DIRECT }
