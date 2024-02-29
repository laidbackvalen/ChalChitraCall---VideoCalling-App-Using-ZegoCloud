pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        //maven(url = "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")
        maven (url ="https://storage.zego.im/maven")     // <- Add this line.
        maven(url = "https://www.jitpack.io")
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
//        maven(url = "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")

        maven (url ="https://storage.zego.im/maven")     // <- Add this line.
        maven(url = "https://www.jitpack.io")


    }
}

rootProject.name = "Video Calling App"
include(":app")
