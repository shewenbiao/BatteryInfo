package com.common.utils.battery;

import android.content.Context;

public class HackPowerProfile {
    public static Object getPowerProfile(Context context) {
        return RefInvoker.getObjectFromConstructor("com.android.internal.os.PowerProfile", new Class[]{Context.class}, new Object[]{context});
    }

    public static Object getAveragePower(Object obj, String str) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.PowerProfile", "getAveragePower", new Class[]{String.class}, new Object[]{str});
    }

    public static Object getAveragePower(Object obj, String str, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.PowerProfile", "getAveragePower", new Class[]{String.class, Integer.TYPE}, new Object[]{str, Integer.valueOf(i)});
    }

    public static Object getNumSpeedSteps(Object obj) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.PowerProfile", "getNumSpeedSteps", (Class[]) null, (Object[]) null);
    }
}
