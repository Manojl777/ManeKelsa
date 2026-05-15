# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/google/home/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any custom Keep rules here:

# Keep Compose internal classes
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.material3.** { *; }
