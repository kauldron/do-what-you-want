# Keep Application kotlin properties for reflection purposes
-keepclassmembers, allowoptimization, allowobfuscation public final class * extends android.app.Application {
  private final *** *;
  *** get*();
}