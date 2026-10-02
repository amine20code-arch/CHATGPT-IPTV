# XMLTV EPG mapping
1. Prefer exact `tvg-id` on M3U and XMLTV `channel id`.
2. If no ID exists, normalize channel names and match display-name.
3. Keep a manual mapping table for providers with unstable IDs.
4. Store the last successful mapping locally and refresh only changed EPG data.
