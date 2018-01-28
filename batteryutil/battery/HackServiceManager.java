package com.common.utils.battery;

import android.os.IBinder;

import java.util.HashMap;

public class HackServiceManager {
    public static HashMap<String, IBinder> getIBinderMap() {
        return (HashMap) RefInvoker.getDeclaredField(null, "android.os.ServiceManager", "sCache");
    }
}
