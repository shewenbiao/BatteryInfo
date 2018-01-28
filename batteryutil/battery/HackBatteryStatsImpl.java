package com.common.utils.battery;

public class HackBatteryStatsImpl {
    public static Object computeBatteryRealtime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "computeBatteryRealtime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getPhoneOnTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getPhoneOnTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getScreenBrightnessTime(Object obj, int i, long j, int i2) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getScreenBrightnessTime", new Class[]{Integer.TYPE, Long.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(i2)});
    }

    public static Object getWifiOnTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getWifiOnTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getGlobalWifiRunningTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getGlobalWifiRunningTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getBluetoothOnTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getBluetoothOnTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getBluetoothPingCount(Object obj) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getBluetoothPingCount", (Class[]) null, (Object[]) null);
    }

    public static Object getScreenOnTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getScreenOnTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getPhoneSignalStrengthTime(Object obj, int i, long j, int i2) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getPhoneSignalStrengthTime", new Class[]{Integer.TYPE, Long.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(i2)});
    }

    public static Object getPhoneSignalScanningTime(Object obj, long j, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getPhoneSignalScanningTime", new Class[]{Long.TYPE, Integer.TYPE}, new Object[]{Long.valueOf(j), Integer.valueOf(i)});
    }

    public static Object getUidStats(Object obj) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getUidStats", (Class[]) null, (Object[]) null);
    }

    public static Object getMobileTcpBytesReceived(Object obj, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getMobileTcpBytesReceived", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }

    public static Object getMobileTcpBytesSent(Object obj, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getMobileTcpBytesSent", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }

    public static Object getTotalTcpBytesReceived(Object obj, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getTotalTcpBytesReceived", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }

    public static Object getTotalTcpBytesSent(Object obj, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getTotalTcpBytesSent", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }

    public static Object getRadioDataUptime(Object obj) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "getRadioDataUptime", (Class[]) null, (Object[]) null);
    }

    public static Object distributeWorkLocked(Object obj, int i) {
        return RefInvoker.getObjectFromMethod(obj, "com.android.internal.os.HackBatteryStatsImpl", "distributeWorkLocked", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
    }
}
