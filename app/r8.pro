-dontnote com.lockwood.**

# Producing useful obfuscated stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, *Annotation*

# Optimizing for Android SDK versions
-assumevalues class android.os.Build$VERSION {
  int SDK_INT return 29..2147483647;
}

-keepclassmembers,allowobfuscation class * implements androidx.lifecycle.LifecycleObserver {
    @androidx.lifecycle.OnLifecycleEvent <methods>;
}

-keepclassmembers class com.lockwood.** extends android.text.TextWatcher {
    *;
}