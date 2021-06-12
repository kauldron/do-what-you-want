-dontnote com.lockwood.**

# Producing useful obfuscated stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, *Annotation*

# Keep Application kotlin properties for reflection purposes
-keepclassmembers, allowoptimization, allowobfuscation public final class * extends android.app.Application {
  private final *** *;
  *** get*();
}