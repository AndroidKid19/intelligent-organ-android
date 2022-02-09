# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
-keep public class com.yway.scomponent.commonsdk.core.BaseResponse { *; }
-keep public class com.yway.scomponent.commonsdk.core.Constants { *; }
-keep public class com.yway.scomponent.commonsdk.core.EventBusHub { *; }
-keep public class com.yway.scomponent.commonsdk.core.RouterHub { *; }
-keep public class com.yway.scomponent.commonsdk.core.UserInfoBean { *; }
-keep public class com.yway.scomponent.commonsdk.core.AddressCompanyBean { *; }
-keep public class com.yway.scomponent.commonsdk.core.DictClassifyBean { *; }
-keep public class com.yway.scomponent.commonsdk.core.UploadFileBean { *; }