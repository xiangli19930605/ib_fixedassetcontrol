LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := SerialPort_JNI
LOCAL_SRC_FILES := rfid_interface_SerialPort.c
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)