#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring

JNICALL
Java_com_test_application1_GetPostsAsync_getSiteFromJNI(
        JNIEnv *env,
        jobject
        )
{
    std::string url = "https://jsonplaceholder.typicode.com/";
    return env->NewStringUTF(url.c_str());
}