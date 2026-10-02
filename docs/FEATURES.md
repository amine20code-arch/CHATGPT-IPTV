# IPTV TV Pro 1.1 feature matrix

## Providers
- M3U/M3U8
- JSON playlist
- Xtream Codes live API
- Stalker/Ministra API v3 adapter (portal authorization varies by provider)
- Direct URL

## Playback
- Media3 1.11.1
- HLS / DASH / RTSP / progressive HTTP
- adaptive bitrate for HLS/DASH
- audio/subtitle track selection through Media3 UI
- hardware decoder first, Android codec fallback
- low startup buffer profile
- diagnostics: first frame, buffering count, last player error

## TV UX target
- D-pad navigation
- category/channel grid
- favorites
- search
- EPG mapping
- fast zap manager

## Important limitations
- Stalker/Ministra may enforce MAC/serial/token/device binding; the adapter cannot bypass provider policy.
- UDP/RTP multicast requires a device/network path that supports it; generic Android Media3 support is not a guarantee for every multicast setup.
- DVR/catch-up are provider/storage dependent and must respect content rights.
