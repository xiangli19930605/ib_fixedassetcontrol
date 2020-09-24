/*
 * Copyright 2009-2011 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <jni.h>

#include "rfid_interface_SerialPort.h"
#include "android/log.h"

static const char *TAG = "serial_port_c";

#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

static speed_t getBaudrate(jint baudrate) {
    switch (baudrate) {
        case 0:
            return B0;
        case 50:
            return B50;
        case 75:
            return B75;
        case 110:
            return B110;
        case 134:
            return B134;
        case 150:
            return B150;
        case 200:
            return B200;
        case 300:
            return B300;
        case 600:
            return B600;
        case 1200:
            return B1200;
        case 1800:
            return B1800;
        case 2400:
            return B2400;
        case 4800:
            return B4800;
        case 9600:
            return B9600;
        case 19200:
            return B19200;
        case 38400:
            return B38400;
        case 57600:
            return B57600;
        case 115200:
            return B115200;
        case 230400:
            return B230400;
        case 460800:
            return B460800;
        case 500000:
            return B500000;
        case 576000:
            return B576000;
        case 921600:
            return B921600;
        case 1000000:
            return B1000000;
        case 1152000:
            return B1152000;
        case 1500000:
            return B1500000;
        case 2000000:
            return B2000000;
        case 2500000:
            return B2500000;
        case 3000000:
            return B3000000;
        case 3500000:
            return B3500000;
        case 4000000:
            return B4000000;
        default:
            return -1;
    }
}

JNIEXPORT jobject JNICALL Java_rfid_1interface_SerialPort_openSerialPort
        (JNIEnv *env, jclass thiz, jstring path, jint baud, jint flowctrl, jint databits,
         jint stopbits, jint parity) {
    int fd, ret;
    speed_t speed;
    jobject mFileDescriptor;

    LOGD("姝ｅ湪鎵撳紑涓插彛");
    {
        speed = getBaudrate(baud);
        if (speed == -1) {
            LOGE("娉㈢壒鐜囬敊璇紒");
            return NULL;
        }
    }

    {
        jboolean iscopy;
        const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
//		fd = open(path_utf, O_RDWR | O_DIRECT | O_SYNC);
//		fd = open(path_utf, O_RDWR | flags);
        //fd = open(path_utf, O_RDWR | O_NOCTTY | O_NONBLOCK | O_NDELAY);
        fd = open(path_utf, O_RDWR | O_NOCTTY | O_NONBLOCK);
        LOGD("鎵撳紑涓插彛%s, fd = %d", path_utf, fd);
        (*env)->ReleaseStringUTFChars(env, path, path_utf);
        if (fd == -1) {
            LOGE("鏃犳硶鎵撳紑涓插彛");
            return NULL;
        }
        LOGD("涓插彛琚垚鍔熸墦寮�");
    }

    {
        struct termios cfg;
        ret = tcgetattr(fd, &cfg);
        if (ret != 0) {
            LOGE("璇诲彇涓插彛灞炴�уけ璐ヤ簡锛乺et:%d", ret);
            close(fd);
            return NULL;
        }
        cfmakeraw(&cfg);
        cfsetispeed(&cfg, speed);
        cfsetospeed(&cfg, speed);

        //淇敼鎺у埗妯″紡锛屼繚璇佺▼搴忎笉浼氬崰鐢ㄤ覆鍙�
        cfg.c_cflag |= CLOCAL;
        //淇敼鎺у埗妯″紡锛屼娇寰楄兘澶熶粠涓插彛涓鍙栬緭鍏ユ暟鎹�
        cfg.c_cflag |= CREAD;
        //璁剧疆鏁版嵁娴佹帶鍒�
        switch (flowctrl) {
            case 0 ://涓嶄娇鐢ㄦ祦鎺у埗
                cfg.c_cflag &= ~CRTSCTS;
                break;
            case 1 ://浣跨敤纭欢娴佹帶鍒�
                cfg.c_cflag |= CRTSCTS;
                break;
            case 2 ://浣跨敤杞欢娴佹帶鍒�
                cfg.c_cflag |= IXON | IXOFF | IXANY;
                break;
        }
        //璁剧疆鏁版嵁浣�
        cfg.c_cflag &= ~CSIZE; //灞忚斀鍏朵粬鏍囧織浣�
        switch (databits) {
            case 5    :
                cfg.c_cflag |= CS5;
                break;
            case 6    :
                cfg.c_cflag |= CS6;
                break;
            case 7    :
                cfg.c_cflag |= CS7;
                break;
            case 8:
            default:
                cfg.c_cflag |= CS8;
                break;
        }
        //璁剧疆鏍￠獙浣�
#ifdef CMSPAR
        cfg.c_cflag &= ~(PARENB | PARODD | CMSPAR);
#endif /* CMSPAR */
        switch (parity) {
            case 1://璁剧疆涓哄鏍￠獙
                cfg.c_cflag |= PARENB | PARODD;
                //cfg.c_cflag |= (PARODD | PARENB);
                //cfg.c_iflag |= INPCK;
                break;
            case 2://璁剧疆涓哄伓鏍￠獙
                cfg.c_cflag |= PARENB;
                //cfg.c_cflag |= PARENB;
                //cfg.c_cflag &= ~PARODD;
                //cfg.c_iflag |= INPCK;
                break;
            case 0: //鏃犲鍋舵牎楠屼綅銆�
            default:
                break;
                //cfg.c_cflag &= ~PARENB;
                //cfg.c_iflag &= ~INPCK;
        }
        // 璁剧疆鍋滄浣�
        switch (stopbits) {
            case 2:
                cfg.c_cflag |= CSTOPB;
                break;
            case 3: // 1.5
                cfg.c_cflag |= CSTOPB;
                cfg.c_cflag |= CS5;
                break;
            case 1:
            default:
                cfg.c_cflag &= ~CSTOPB;
        }
        //淇敼杈撳嚭妯″紡锛屽師濮嬫暟鎹緭鍑�
        //cfg.c_oflag &= ~OPOST;
        //璁剧疆绛夊緟鏃堕棿鍜屾渶灏忔帴鏀跺瓧绗�
        //cfg.c_cc[VTIME] = 1; /* 璇诲彇涓�涓瓧绗︾瓑寰�1*(1/10)s */
        //cfg.c_cc[VMIN] = 1; /* 璇诲彇瀛楃鐨勬渶灏戜釜鏁颁负1 */
        //濡傛灉鍙戠敓鏁版嵁婧㈠嚭锛屾帴鏀舵暟鎹紝浣嗘槸涓嶅啀璇诲彇
        //tcflush(fd,TCIFLUSH);

        //婵�娲婚厤缃� (灏嗕慨鏀瑰悗鐨則ermios鏁版嵁璁剧疆鍒颁覆鍙ｄ腑锛�
        int ret = tcsetattr(fd, TCSANOW, &cfg);
        if (ret != 0) {
            LOGE("璁剧疆涓插彛灞炴�уけ璐ヤ簡锛乺et:%d", ret);
            close(fd);
            return NULL;
        }
        LOGD("涓插彛灞炴�ц璁剧疆鎴愬姛銆�");
    }

    tcflush(fd, TCIOFLUSH);

    {
        jclass cFileDescriptor = (*env)->FindClass(env, "java/io/FileDescriptor");
        jmethodID iFileDescriptor = (*env)->GetMethodID(env, cFileDescriptor, "<init>", "()V");
        jfieldID descriptorID = (*env)->GetFieldID(env, cFileDescriptor, "descriptor", "I");
        mFileDescriptor = (*env)->NewObject(env, cFileDescriptor, iFileDescriptor);
        (*env)->SetIntField(env, mFileDescriptor, descriptorID, (jint) fd);
        LOGD("涓插彛鎻忚堪绗﹀凡琚垱寤猴紝鍙互璇诲啓浜嗐��");
    }

    return mFileDescriptor;
}


JNIEXPORT void JNICALL Java_rfid_1interface_SerialPort_closeSerialPort
        (JNIEnv *env, jobject thiz) {
    LOGD("姝ｅ湪鍏抽棴涓插彛");
    {
        jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
        jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");
        jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "mFd",
                                            "Ljava/io/FileDescriptor;");
        jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");
        jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
        jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);
        if (descriptor < 0) {
            LOGE("鎵句笉鍒颁覆鍙ｆ弿杩扮锛�");
            return;
        }
        tcflush(descriptor, TCIOFLUSH);
        close(descriptor);
        LOGD("涓插彛鎻忚堪绗�%d宸茶鍏抽棴", descriptor);
    }
}


JNIEXPORT void JNICALL Java_rfid_1interface_SerialPort_flushSerialPort
        (JNIEnv *env, jobject thiz) {
    LOGD("姝ｅ湪娓呯悊涓插彛");
    {
        jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
        jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");
        jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "mFd",
                                            "Ljava/io/FileDescriptor;");
        jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");
        jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
        jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);
        if (descriptor < 0) {
            LOGE("鎵句笉鍒颁覆鍙ｆ弿杩扮锛�");
            return;
        }
        tcflush(descriptor, TCIOFLUSH);
        LOGD("涓插彛鎻忚堪绗�%d宸茶娓呯悊", descriptor);
    }
}

JNIEXPORT void JNICALL Java_rfid_1interface_SerialPort_flushInputSerialPort
        (JNIEnv *env, jobject thiz) {
    LOGD("姝ｅ湪娓呯悊涓插彛");
    {
        jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
        jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");
        jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "mFd",
                                            "Ljava/io/FileDescriptor;");
        jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");
        jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
        jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);
        if (descriptor < 0) {
            LOGE("鎵句笉鍒颁覆鍙ｆ弿杩扮锛�");
            return;
        }
        tcflush(descriptor, TCIFLUSH);
        LOGD("涓插彛鎻忚堪绗�%d鐨勮緭鍏ュ尯宸茶娓呯悊", descriptor);
    }
}


JNIEXPORT void JNICALL Java_rfid_1interface_SerialPort_flushOutputSerialPort
        (JNIEnv *env, jobject thiz) {
    LOGD("姝ｅ湪娓呯悊涓插彛");
    {
        jclass SerialPortClass = (*env)->GetObjectClass(env, thiz);
        jclass FileDescriptorClass = (*env)->FindClass(env, "java/io/FileDescriptor");
        jfieldID mFdID = (*env)->GetFieldID(env, SerialPortClass, "mFd",
                                            "Ljava/io/FileDescriptor;");
        jfieldID descriptorID = (*env)->GetFieldID(env, FileDescriptorClass, "descriptor", "I");
        jobject mFd = (*env)->GetObjectField(env, thiz, mFdID);
        jint descriptor = (*env)->GetIntField(env, mFd, descriptorID);
        if (descriptor < 0) {
            LOGE("鎵句笉鍒颁覆鍙ｆ弿杩扮锛�");
            return;
        }
        tcflush(descriptor, TCOFLUSH);
        LOGD("涓插彛鎻忚堪绗�%d鐨勮緭鍑哄尯宸茶娓呯悊", descriptor);
    }
}


