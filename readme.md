# 简介
GamepadTool 可以帮你用游戏手柄映射键盘的输入，他使用的是libGDX框架，项目结构使用gradle管理
此项目支持有键盘设备，如电脑

# 打包
1. .\gradlew desktop:dist
2. 以desktop/release的内容做软件目录
3. jar放进release/lib/
4. 如果系统没有JAVA_HOME的环境，需要下载jre1.8+解压缩到release/jre/目录内。解压后的结构应存在文件release/jre/bin/java.exe

# 混淆打包(推荐)
1. .\gradlew lwjgl3:proguardx

# 发布地址
[https://gitee.com/sellgirl/sgGamepadToolRelease/wikis/Home](https://gitee.com/sellgirl/sgGamepadToolRelease/wikis/Home)