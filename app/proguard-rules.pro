# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/posytive/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Needed by DBFlow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }

# Retrofit and Gson
-keep class com.google.gson.** { *; }
-keep public class com.google.gson.** {public private protected *;}
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.xml.stream.** { *; }
-keep class retrofit.** { *; }
-keep class com.google.appengine.** { *; }
-keepattributes *Annotation*, InnerClasses, Signature
-dontwarn com.squareup.okhttp.*
-dontwarn rx.**
-dontwarn javax.xml.stream.**
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class nl.splendo.assignment.posytive.data.** { *; }

# Allowing most own packages to be kept, not really hiding any secret there
# could be more specific about what to keep, within some classes, but *!
-keep class nl.splendo.assignment.posytive.helpers.** { *; }
-keep class nl.splendo.assignment.posytive.presenters.** { *; }
-keep class nl.splendo.assignment.posytive.ui.** { *; }


# Parceler
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keep class org.parceler.Parceler$$Parcels

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Support libs
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep class nl.splendo.assignment.posytive.itemdecorator
-keep public class * extends android.support.v7.widget.RecyclerView.ItemDecoration
-keep class android.support.v7.widget.RecyclerView
-dontwarn android.support.**

# General
-dontwarn org.apache.**
-dontwarn com.loopj.android.http.**
-keep class com.loopj.android.http.**{*;}

-keepclassmembers class * extends android.app.Activity {  *; }
