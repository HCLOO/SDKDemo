# SDKDemo
开发PerformanceMonitorSDK，其功能实现：LeakCanary内存监听、Logger日志组件框架管理

SDK开发步骤（本博主结合目前项目中使用到的Logger日志管理组件框架和LeakCanary内存监控工具来开发PerformanceMonitorSDK）：
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
1.创建一个Android项目工程，如SDKDemo
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
2.在该工程中创建一个Module-->选择Android Library-->命名SDK（本SDK命名为PerformanceMonitorSDK）
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
3.向performancemonitorsdk module的build.gradle 文件中添加以下代码：

//////// 打包发布配置开始 ////////
apply plugin: 'maven'
ext {
    //项目的本地地址
    GITHUB_REPO_PATH = "D:\\GitLabFile\\SDKDemo"       //这里指定的就是新建项目在本地的路径
    PUBLISH_GROUP_ID = 'com.example'
    PUBLISH_ARTIFACT_ID = 'sdkdemo_performancemonitorsdk'
    PUBLISH_VERSION = '1.0.1'
}
uploadArchives {
    repositories.mavenDeployer {
        def deployPath = file(project.GITHUB_REPO_PATH)
        repository(url: "file://${deployPath.absolutePath}")
        pom.project {
            groupId project.PUBLISH_GROUP_ID
            artifactId project.PUBLISH_ARTIFACT_ID
            version project.PUBLISH_VERSION
        }
    }
}

// 源代码一起打包
task androidSourcesJar(type: Jar) {
    classifier = 'sources'
    from android.sourceSets.main.java.sourceFiles
}
artifacts {
    archives androidSourcesJar
}
//////// 打包发布配置结束 ////////
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
4.打包输出，使用命令行进入到项目的根目录（也就是gradlew文件所在的目录），执行命令：

gradlew uploadArchives
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
5.待步骤4命令行执行成功后，即可在SDKDemo/com/example/sdkdemo_performancemonitorsdk/1.0.1目录中查看
到aar、jar、pom等包已生成
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
6.至此，SDK的开发工作已完成，接下来将步骤5目录中生成的aar、jar、pom等包放到GitHub仓库以供其他的开
发者使用（本示例直接将整个SDKDemo项目放到GitHub仓库）
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
7.使用SDK：

（1）在project的build.gradle文件中添加以下代码：
allprojects {

    repositories {

        ......
        maven { url "https://raw.githubusercontent.com/HCLOO/SDKDemo/master" }

        //maven { url "https://raw.githubusercontent.com/GitHub用户名/仓库项目名/master" }
       }

}

(2)在module的build.gradle文件中添加以下代码：
dependencies {
    ......
    implementation 'com.example:sdkdemo_performancemonitorsdk:1.0.1'
    //依赖规范为：PUBLISH_GROUP_ID：PUBLISH_ARTIFACT_ID：PUBLISH_VERSION
}

（3）build一下项目即可完成对SDK的引入，接下来就可以使用该SDK了
