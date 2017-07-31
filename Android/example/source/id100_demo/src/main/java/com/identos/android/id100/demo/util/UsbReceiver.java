package com.identos.android.id100.demo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

public class UsbReceiver extends BroadcastReceiver {
    public static final String ACTION_USB_PERMISSION = "com.identos.android.USB_PERMISSION";

    private final Callback mCallback;

    public UsbReceiver(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

        if(device != null && mCallback != null){
            switch (intent.getAction()){
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    mCallback.onDeviceAttached(device);
                    break;

                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    mCallback.onDeviceDetached(device);
                    break;

                case UsbReceiver.ACTION_USB_PERMISSION:
                    boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    mCallback.onPermissionReceived(device, granted);
                    break;
            }
        }
    }

    public interface Callback {
        void onDeviceAttached(UsbDevice device);

        void onDeviceDetached(UsbDevice device);

        void onPermissionReceived(UsbDevice device, boolean granted);
    }
}