# proguard5.pro 此文件打包后单独运行knightsasha.jar正常
# (推荐发到github时使用本混淆配置)
# 尝试混淆多一点内容, 不考虑被 knightsashax 反向调用的情况
# 实测可以混淆sgJavaHelper包, 但进入knightsashax时报错
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/resources.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/rt.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/jsse.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/jce.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/charsets.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/ext/dnsns.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/ext/localedata.jar
-libraryjars  D:/Program Files/Java/jdk1.8.0_201/jre/lib/ext/sunjce_provider.jar
-libraryjars  D:/maven_repository/com/sellgirl/sgJavaHelper/0.0.42/sgJavaHelper-0.0.42.jar
-dontwarn
-keepattributes *Annotation*
-keepattributes Signature
-printmapping proguard.map
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
#-keep public class * {
#    public protected *;
#}
#-keep public class com.mygdx.game.** {
#    public protected *;
#}

# knightsasha
-keep class com.sellgirl.sgGameHelper.** implements com.sellgirl.sgJavaHelper.ISGUnProGuard{*;}
-keep class com.sellgirl.gamepadtool.** implements com.sellgirl.sgJavaHelper.ISGUnProGuard{*;}
-keep public class com.sellgirl.gamepadtool.DesktopLauncher {
    public protected *;
}

# libGDX
-keep class com.badlogic.** { *; }
-keep enum com.badlogic.** { *; }
# -keep class com.studiohartman.** { *; }

-keep class !com.sellgirl.gamepadtool.**,!com.sellgirl.sgJavaHelper.**,!com.sellgirl.sgGameHelper.**,** {*;}
