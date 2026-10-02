# Implementation roadmap

Phase 1: playlist import + Media3 player + TV launcher + security foundation.
Phase 2: Xtream profiles, categories, VOD/series, headers/cookies, QR import.
Phase 3: XMLTV EPG grid, favorites, search, recent channels, fast zap.
Phase 4: catch-up/timeshift using provider APIs and time-shift metadata.
Phase 5: diagnostics, buffering telemetry, offline metadata cache, parental PIN.
Phase 6: device-specific UDP/RTP adapters and DVR pipeline where technically and legally supported.

The project intentionally keeps provider, EPG, security, and playback layers separate so later phases do not require a reset.
