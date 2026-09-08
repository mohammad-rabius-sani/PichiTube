# ============================================================
# PichiTube ProGuard Rules
# ============================================================

# -------------------------------------------------------
# NewPipeExtractor — Rhino JS Engine (cipher decryption)
# -------------------------------------------------------
-keep class org.mozilla.javascript.** { *; }
-keep class org.mozilla.classfile.ClassFileWriter
-dontwarn org.mozilla.javascript.tools.**
-dontwarn org.mozilla.javascript.engine.**
-dontwarn java.beans.**
-dontwarn jdk.dynalink.**

# Keep all NewPipeExtractor service classes (extractors are loaded via reflection)
-keep class org.schabi.newpipe.extractor.** { *; }
-keepnames class org.schabi.newpipe.extractor.** { *; }

# -------------------------------------------------------
# Kotlinx Serialization
# -------------------------------------------------------
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.pichitube.app.**$$serializer { *; }
-keepclassmembers class com.pichitube.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.pichitube.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# -------------------------------------------------------
# Hilt / Dagger
# -------------------------------------------------------
-dontwarn com.google.errorprone.annotations.**

# -------------------------------------------------------
# OkHttp / Okio
# -------------------------------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# -------------------------------------------------------
# Ktor
# -------------------------------------------------------
-dontwarn io.ktor.**
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }

# -------------------------------------------------------
# Coil
# -------------------------------------------------------
-dontwarn coil.**

# -------------------------------------------------------
# Media3 / ExoPlayer
# -------------------------------------------------------
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# -------------------------------------------------------
# ZXing QR Code
# -------------------------------------------------------
-keep class com.google.zxing.** { *; }
-keep class com.journeyapps.** { *; }

# -------------------------------------------------------
# Room
# -------------------------------------------------------
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# -------------------------------------------------------
# WorkManager
# -------------------------------------------------------
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.CoroutineWorker
-keep class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}

# -------------------------------------------------------
# General Android
# -------------------------------------------------------
-keepattributes SourceFile,LineNumberTable
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
