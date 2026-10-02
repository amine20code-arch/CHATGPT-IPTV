package com.example.iptvtvpro.epg

import org.xmlpull.v1.XmlPullParser
import android.util.Xml
import java.io.StringReader

data class EpgProgram(val channelId:String,val start:String,val stop:String,val title:String,val description:String?=null)

object EpgParser {
    fun parse(xml:String): List<EpgProgram> {
        val p=Xml.newPullParser(); p.setInput(StringReader(xml)); val out=mutableListOf<EpgProgram>(); var channel=""; var start=""; var stop=""; var title=""; var desc:String?=null; var tag=""
        while(p.eventType!=XmlPullParser.END_DOCUMENT){ when(p.eventType){XmlPullParser.START_TAG->{tag=p.name;if(tag=="programme"){channel=p.getAttributeValue(null,"channel")?:("");start=p.getAttributeValue(null,"start")?:("");stop=p.getAttributeValue(null,"stop")?:("")}; if(tag=="title")title="";if(tag=="desc")desc=""};XmlPullParser.TEXT->{when(tag){"title"->title+=p.text;"desc"->desc=(desc?:(""))+p.text}};XmlPullParser.END_TAG->{if(p.name=="programme"&&channel.isNotBlank()&&title.isNotBlank())out+=EpgProgram(channel,start,stop,title,desc);tag=""}};p.next()};return out
    }
}
