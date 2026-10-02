package com.example.iptvtvpro.epg

import com.example.iptvtvpro.data.Channel
import com.example.iptvtvpro.data.EpgProgram

object EpgMapper {
    fun map(channels: List<Channel>, programs: List<EpgProgram>): Map<String, List<EpgProgram>> {
        val byId = channels.mapNotNull { it.tvgId?.let { id -> id to it.id } }.toMap()
        return programs.groupBy { byId[it.channelId] ?: it.channelId }
    }
}
