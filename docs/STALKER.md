# Stalker / Ministra integration

The application includes a provider adapter for Ministra/Stalker API v3.

Input fields:
- Portal URL
- MAC address when the provider requires it
- optional serial number

The provider must permit API access and the account/device. Ministra documentation describes MAC/serial verification and OAuth token lifetime settings. Do not attempt to bypass device/account restrictions.

Typical portal forms may use a `/stalker_portal/` API path, but the exact deployment can differ. Test with the provider's own portal/API documentation.
