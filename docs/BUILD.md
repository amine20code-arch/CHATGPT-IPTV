# Build

Open the project root in Android Studio.

Recommended:
- compileSdk 36
- targetSdk 34
- JDK 17
- Android TV emulator or physical Android TV device

Build debug:
`./gradlew assembleDebug`

Build release:
`./gradlew assembleRelease`

For a signed release configure your own Android Keystore in Android Studio. Never commit the keystore or passwords.
