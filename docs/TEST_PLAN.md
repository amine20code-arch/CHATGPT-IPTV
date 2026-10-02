# Test plan
## Unit
- M3U attributes, groups, logos, IDs, user-agent and referrer.
- JSON playlist variants.
- XMLTV parsing and channel mapping.
- URL/protocol classification.

## Integration
- HLS startup and channel zapping.
- DASH adaptive track selection.
- RTSP connection/reconnect.
- HTTP errors 401/403/404/429/5xx.
- Expired Xtream credentials.
- EPG refresh and stale-cache behavior.
- Remote-only navigation on Google TV/Android TV.

## Soak
- 4-hour continuous playback.
- 100 channel zap cycle.
- Wi-Fi loss/recovery.
- Decoder fallback across ARM64/ARMv7 TV devices.
