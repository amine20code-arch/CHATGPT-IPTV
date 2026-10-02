# Performance / fast-zap design

1. Keep one ExoPlayer instance for channel changes.
2. Replace MediaItem rather than recreating Activity/player.
3. Keep EPG/logo/provider metadata in local cache.
4. Use short startup buffer for live TV, then allow buffer growth.
5. Pre-resolve the next channel URL where provider rules permit.
6. Record first-frame time and buffering events.
7. Prefer hardware decoders; expose codec information in Diagnostics.
8. Do not cache protected/live video indiscriminately.

Real channel-zap time depends on DNS, TLS, server response, provider authorization, stream segment duration and the TV chipset.
