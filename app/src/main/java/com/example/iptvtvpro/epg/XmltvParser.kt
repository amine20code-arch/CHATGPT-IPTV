package com.example.iptvtvpro.epg

import com.example.iptvtvpro.data.EpgProgram
import org.xmlpull.v1.XmlPullParser
import android.util.Xml
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

object XmltvParser {
    private val fmt = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)
    fun parse(input: InputStream): List<EpgProgram> {
        val x = Xml.newPullParser(); x.setInput(input, null)
        val out = mutableListOf<EpgProgram>(); var event = x.eventType; var channel = ""; var title = ""; var desc: String? = null; var start = 0L; var end = 0L
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG) when (x.name) {
                "programme" -> { channel = x.getAttributeValue(null, "channel") ?: ""; start = parseTime(x.getAttributeValue(null,"start")); end = parseTime(x.getAttributeValue(null,"stop")) }
                "title" -> title = x.nextText()
                "desc" -> desc = x.nextText()
            } else if (event == XmlPullParser.END_TAG && x.name == "programme" && channel.isNotBlank() && title.isNotBlank()) out += EpgProgram(channel,title,start,end,desc)
            event = x.next()
        }
        return out
    }
    private fun parseTime(v: String?): Long = try { if (v.isNullOrBlank()) 0 else fmt.parse(v.substring(0, minOf(v.length, 24)))?.time ?: 0 } catch (_: Exception) { 0 }
}
