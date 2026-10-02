# Security

- Credentials should be encrypted with Android Keystore-backed keys.
- Never log passwords, tokens, cookies, MAC credentials or full authenticated URLs.
- Use HTTPS whenever the provider supports it.
- Release builds enable R8.
- Do not disable TLS certificate validation globally.
- Stalker/Ministra device binding is treated as an authorization boundary, not something to bypass.
- Recording/catch-up must be used only for content the user is authorized to record.
