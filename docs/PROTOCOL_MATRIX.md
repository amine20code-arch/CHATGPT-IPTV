# Protocol matrix

| Input | Foundation | Notes |
|---|---|---|
| M3U/M3U8 | Yes | Parser + Media3 playback |
| JSON playlist | Yes | Generic channel schema |
| Xtream Codes | Yes | Live-stream API client foundation |
| XMLTV | Yes | Parser + mapping guide |
| HLS | Yes | Media3 HLS |
| DASH | Yes | Media3 DASH |
| RTSP | Yes | Media3 RTSP |
| HTTP MPEG-TS | Yes | Progressive playback where codec is supported |
| HTTP Basic/token/headers | Foundation | Header injection layer to be completed in provider profile UI |
| Cookie/session | Foundation | Provider-session layer required for per-provider persistence |
| UDP/RTP multicast | Adapter required | Device/network dependent; not falsely advertised as universal |
| DVR/Timeshift | Provider/device dependent | Requires provider catch-up or a recording pipeline; not universally possible |
