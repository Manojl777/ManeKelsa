# Mane Kelsa (Android)

Jetpack Compose Android app for **Mane Kelsa**, with Firebase Firestore. Open this folder in **Android Studio** (Ladybug or newer recommended), sync Gradle, then run on a device or emulator.

## Requirements

- [Android Studio](https://developer.android.com/studio) with Android SDK 36 (or let Studio install missing SDKs)
- JDK 21 (bundled with recent Android Studio)
- USB debugging enabled on a physical device, or an emulator with Google Play if you rely on Firebase

## Firebase

The app initializes Firebase in code (`MainActivity`) and uses Firestore (including a named database). For production you should:

1. Register an **Android** app in the [Firebase Console](https://console.firebase.google.com/) with package name `com.example.localservice`.
2. Download `google-services.json` into `app/` **or** keep programmatic `FirebaseOptions` aligned with your project’s keys and App ID from the console.

`firestore.rules` in the project root is for deploying security rules to Firebase (not used at compile time).

## Colours & UI

Brand colours match the original design: primary `#006766`, secondary `#F4511E`, tertiary `#5D5E53`, background `#FFFDF5` (see `MainActivity.kt` and `res/values/colors.xml`).
