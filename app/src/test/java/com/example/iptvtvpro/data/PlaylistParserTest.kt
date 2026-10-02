package com.example.iptvtvpro.data
import org.junit.Assert.*
import org.junit.Test
class PlaylistParserTest { @Test fun m3u(){val x="#EXTM3U\n#EXTINF:-1 tvg-id=\"one\" group-title=\"News\",One\nhttps://example.com/one.m3u8";val c=PlaylistParser.parseM3u(x);assertEquals(1,c.size);assertEquals("News",c[0].group);assertEquals("one",c[0].tvgId)} @Test fun direct(){assertTrue(PlaylistParser.isDirectUrl("rtsp://example.com/live"));assertFalse(PlaylistParser.isDirectUrl("file:///x"))}}
