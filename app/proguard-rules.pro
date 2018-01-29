# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\MyProject\androidSDK\sdk/tools/proguard/proguard-android.txt
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
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-ignorewarnings
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class android.app.Notification
-keep public class android.webkit.**
-keep class **.Webview2JsInterface {*; }
-keep public class * extends android.app.Dialog
-keep public class * extends android.view
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#实体类不混淆
-keep class com.rain.zhihui_community.entity.**{*;}
-keep class com.rain.zhihui_community.utils.**{*;}
-keep class com.rain.zhihui_community.ui.view.**{*;}
-keep class com.rain.zhihui_community.db.**{*;}
-keep class com.rain.zhihui_community.http.**{*;}

-keepattributes Signature
# 对R文件下的所有类及其方法，都不能被混淆
-keep public class com.rain.zhihui_community.R$*{
public static final int *;
}
# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}
# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
void *(**On*Event);
}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn com.squareup.okhttp.**

-keep class com.squareup.okhttp.** { *;}

-dontwarn okio.**

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keepclasseswithmembernames class * {
     native <methods>;
 }
-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
 }
-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
 # 保留Activity中的方法参数是view的方法，
 # 从而我们在layout里面编写onClick就不会影响
-keepclassmembers class * extends android.app.Activity {
     public void *(android.view.View);
 }
 # 枚举类不能被混淆
-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
 # 保留自定义控件(继承自View)不能被混淆
 -keep public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(***);
 *** get* ();
 }
 # 保留Parcelable序列化的类不能被混淆
-keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
 }


 -keep class com.amap.api.maps.**{*;}
 -keep class com.autonavi.amap.mapcore.*{*;}
 -keep class com.amap.api.trace.**{*;}
 -keep class com.amap.api.maps.**{*;}
 -keep class com.autonavi.**{*;}
 -keep class com.amap.api.trace.**{*;}
 -keep class com.amap.api.location.**{*;}
 -keep class com.amap.api.fence.**{*;}
 -keep class com.autonavi.aps.amapapi.model.**{*;}
 -keep class com.amap.api.services.**{*;}
 -keep class com.amap.api.maps2d.**{*;}
 -keep class com.amap.api.mapcore2d.**{*;}
 -keep class com.amap.api.navi.**{*;}
 -keep class com.autonavi.**{*;}

-keep class org.greenrobot.greendao.**{*;}
-dontwarn org.greenrobot.greendao.database.**
-keepclassmembers class * extends org.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }