# Producing useful obfuscated stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, *Annotation*

# Optimizing for Android SDK versions
-assumevalues class android.os.Build$VERSION {
  int SDK_INT return 21..2147483647;
}

# Keep Application kotlin properties for reflection purposes
-keepclassmembers, allowoptimization, allowobfuscation public final class * extends android.app.Application {
  private final *** *;
  *** get*();
}