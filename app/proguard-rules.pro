# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\androidstudio\androidsdk\Android\sdk/tools/proguard/proguard-android.txt
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


# 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify
# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后使用printmapping指定映射文件的名称
-verbose
-printmapping proguardMapping.txt
# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature
#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTabl
#保证代码中的注释以及内部类不被混淆
-keepattributes *Annotation*,InnerClasses
-keepattributes EnclosingMethod
-ignorewarnings


# 保留了继承自Activity、Application这些类的子类
# 因为这些子类，都有可能被外部调用
# 比如说，第一行就保证了所有Activity的子类不要被混淆
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 枚举类不能被混淆
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
# 保留在Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}
# 对于带有回调函数onXXEvent的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}

# 保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 对WebView的处理
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, java.lang.String);
}


-keep class com.huawei.solarsafe.bean.**{*;}
-dontwarn  com.huawei.solarsafe.bean.**
-keep class com.huawei.solarsafe.logger104.bean.**{*;}
-dontwarn com.huawei.solarsafe.logger104.bean.**


-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}

#保证项目中实体类不被混淆
-dontwarn com.github.mikephil.charting.**
-keep class com.github.mikephil.charting.**{*;}

#保证高德地图不被混淆
-dontwarn com.amap.api.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}

-dontwarn jxl.**
-keep class jxl.**{*;}
-keep interface jxl.**{*;}

-dontwarn  org.eclipse.paho.client.mqttv3.**
-keep class org.eclipse.paho.client.mqttv3.**{*;}

#------photoview

-dontwarn uk.co.senab.photoview.**
-keep interface uk.co.senab.photoview.**{*;}
-keep class uk.co.senab.photoview.**{*;}


-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}

-keep class com.facebook.** {*;}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.**
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Works around a bug in the animated GIF module which will be fixed in 0.12.0
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}
#升级SDK
-keep class com.huawei.updatesdk.**{ *; }