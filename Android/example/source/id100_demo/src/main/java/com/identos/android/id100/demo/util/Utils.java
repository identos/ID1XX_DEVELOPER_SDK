package com.identos.android.id100.demo.util;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.os.Build;

import com.identos.android.id100.library.logging.ConsoleLogger;
import com.identos.android.id100.library.logging.FileLogger;

import java.io.File;

public final class Utils {
    private static final String LOG_FILE = "id100_demo.log";
    private static final String LOG_TAG = "ID100 Demo";

    private Utils() {
        // Hide constructor
    }

    public static String getUsbDeviceName(UsbDevice device) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return device.getDeviceName();
        } else {
            return device.getProductName();
        }
    }

    public static File getLogFile(Context context) {
        return new File(context.getExternalCacheDir(), LOG_FILE);
    }

    public static FileLogger createFileLogger(Context context) {
        return new FileLogger(getLogFile(context));
    }

    public static ConsoleLogger createConsoleLogger() {
        return new ConsoleLogger(LOG_TAG);
    }
}