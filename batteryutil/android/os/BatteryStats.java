//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.os;

import android.content.pm.ApplicationInfo;
import android.util.Printer;
import android.util.SparseArray;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BatteryStats implements Parcelable {
    private static final String APK_DATA = "apk";
    public static final int AUDIO_TURNED_ON = 7;
    private static final String BATTERY_DATA = "bt";
    private static final String BATTERY_LEVEL_DATA = "lv";
    private static final int BATTERY_STATS_CHECKIN_VERSION = 5;
    private static final long BYTES_PER_GB = 1073741824L;
    private static final long BYTES_PER_KB = 1024L;
    private static final long BYTES_PER_MB = 1048576L;
    public static final int DATA_CONNECTION_1xRTT = 7;
    public static final int DATA_CONNECTION_CDMA = 4;
    private static final String DATA_CONNECTION_COUNT_DATA = "dcc";
    public static final int DATA_CONNECTION_EDGE = 2;
    public static final int DATA_CONNECTION_EVDO_0 = 5;
    public static final int DATA_CONNECTION_EVDO_A = 6;
    public static final int DATA_CONNECTION_EVDO_B = 12;
    public static final int DATA_CONNECTION_GPRS = 1;
    public static final int DATA_CONNECTION_HSDPA = 8;
    public static final int DATA_CONNECTION_HSPA = 10;
    public static final int DATA_CONNECTION_HSUPA = 9;
    public static final int DATA_CONNECTION_IDEN = 11;
    static final String[] DATA_CONNECTION_NAMES = new String[]{"none", "gprs", "edge", "umts", "cdma", "evdo_0", "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b", "other"};
    public static final int DATA_CONNECTION_NONE = 0;
    public static final int DATA_CONNECTION_OTHER = 13;
    private static final String DATA_CONNECTION_TIME_DATA = "dct";
    public static final int DATA_CONNECTION_UMTS = 3;
    public static final int FULL_WIFI_LOCK = 5;
    public static final BatteryStats.BitDescription[] HISTORY_STATE_DESCRIPTIONS;
    private static final String KERNEL_WAKELOCK_DATA = "kwl";
    private static final boolean LOCAL_LOGV = false;
    private static final String MISC_DATA = "m";
    private static final String NETWORK_DATA = "nt";
    public static final int NUM_DATA_CONNECTION_TYPES = 14;
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    private static final String PROCESS_DATA = "pr";
    public static final int SCAN_WIFI_LOCK = 6;
    public static final int SCREEN_BRIGHTNESS_BRIGHT = 4;
    public static final int SCREEN_BRIGHTNESS_DARK = 0;
    private static final String SCREEN_BRIGHTNESS_DATA = "br";
    public static final int SCREEN_BRIGHTNESS_DIM = 1;
    public static final int SCREEN_BRIGHTNESS_LIGHT = 3;
    public static final int SCREEN_BRIGHTNESS_MEDIUM = 2;
    static final String[] SCREEN_BRIGHTNESS_NAMES = new String[]{"dark", "dim", "medium", "light", "bright"};
    public static final int SENSOR = 3;
    private static final String SENSOR_DATA = "sr";
    private static final String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    static final String[] SIGNAL_STRENGTH_NAMES = new String[]{"none", "poor", "moderate", "good", "great"};
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;
    private static final String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    public static final int STATS_CURRENT = 2;
    public static final int STATS_LAST = 1;
    public static final int STATS_SINCE_CHARGED = 0;
    public static final int STATS_SINCE_UNPLUGGED = 3;
    private static final String[] STAT_NAMES = new String[]{"t", "l", "c", "u"};
    private static final String UID_DATA = "uid";
    private static final String USER_ACTIVITY_DATA = "ua";
    public static final int VIDEO_TURNED_ON = 8;
    private static final String WAKELOCK_DATA = "wl";
    public static final int WAKE_TYPE_FULL = 1;
    public static final int WAKE_TYPE_PARTIAL = 0;
    public static final int WAKE_TYPE_WINDOW = 2;
    private static final String WIFI_LOCK_DATA = "wfl";
    public static final int WIFI_MULTICAST_ENABLED = 7;
    public static final int WIFI_RUNNING = 4;
    private final StringBuilder mFormatBuilder = new StringBuilder(32);
    private final Formatter mFormatter;

    static {
        BatteryStats.BitDescription[] var0 = new BatteryStats.BitDescription[]{new BatteryStats.BitDescription(1073741824, "plugged"), new BatteryStats.BitDescription(536870912, "screen"), new BatteryStats.BitDescription(268435456, "gps"), new BatteryStats.BitDescription(134217728, "phone_in_call"), new BatteryStats.BitDescription(67108864, "phone_scanning"), new BatteryStats.BitDescription(33554432, "wifi"), new BatteryStats.BitDescription(16777216, "wifi_running"), new BatteryStats.BitDescription(8388608, "wifi_full_lock"), new BatteryStats.BitDescription(4194304, "wifi_scan_lock"), new BatteryStats.BitDescription(2097152, "wifi_multicast"), new BatteryStats.BitDescription(1048576, "bluetooth"), new BatteryStats.BitDescription(524288, "audio"), new BatteryStats.BitDescription(262144, "video"), new BatteryStats.BitDescription(131072, "wake_lock"), new BatteryStats.BitDescription(65536, "sensor"), new BatteryStats.BitDescription(15, 0, "brightness", SCREEN_BRIGHTNESS_NAMES), new BatteryStats.BitDescription(240, 4, "signal_strength", SIGNAL_STRENGTH_NAMES), new BatteryStats.BitDescription(3840, 8, "phone_state", new String[]{"in", "out", "emergency", "off"}), new BatteryStats.BitDescription('\uf000', 12, "data_conn", DATA_CONNECTION_NAMES)};
        HISTORY_STATE_DESCRIPTIONS = var0;
    }

    public BatteryStats() {
        this.mFormatter = new Formatter(this.mFormatBuilder);
    }

    private static final void dumpLine(PrintWriter var0, int var1, String var2, String var3, Object... var4) {
        var0.print(5);
        var0.print(',');
        var0.print(var1);
        var0.print(',');
        var0.print(var2);
        var0.print(',');
        var0.print(var3);
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object var7 = var4[var6];
            var0.print(',');
            var0.print(var7);
        }

        var0.print('\n');
    }

    private final String formatBytesLocked(long var1) {
        this.mFormatBuilder.setLength(0);
        if(var1 < 1024L) {
            return var1 + "B";
        } else if(var1 < 1048576L) {
            Formatter var9 = this.mFormatter;
            Object[] var10 = new Object[]{Double.valueOf((double)var1 / 1024.0D)};
            var9.format("%.2fKB", var10);
            return this.mFormatBuilder.toString();
        } else if(var1 < 1073741824L) {
            Formatter var6 = this.mFormatter;
            Object[] var7 = new Object[]{Double.valueOf((double)var1 / 1048576.0D)};
            var6.format("%.2fMB", var7);
            return this.mFormatBuilder.toString();
        } else {
            Formatter var3 = this.mFormatter;
            Object[] var4 = new Object[]{Double.valueOf((double)var1 / 1.073741824E9D)};
            var3.format("%.2fGB", var4);
            return this.mFormatBuilder.toString();
        }
    }

    private final String formatRatioLocked(long var1, long var3) {
        if(var3 == 0L) {
            return "---%";
        } else {
            float var5 = 100.0F * ((float)var1 / (float)var3);
            this.mFormatBuilder.setLength(0);
            Formatter var6 = this.mFormatter;
            Object[] var7 = new Object[]{Float.valueOf(var5)};
            var6.format("%.1f%%", var7);
            return this.mFormatBuilder.toString();
        }
    }

    private static final void formatTime(StringBuilder var0, long var1) {
        long var3 = var1 / 100L;
        formatTimeRaw(var0, var3);
        var0.append(10L * (var1 - 100L * var3));
        var0.append("ms ");
    }

    private static final void formatTimeMs(StringBuilder var0, long var1) {
        long var3 = var1 / 1000L;
        formatTimeRaw(var0, var3);
        var0.append(var1 - 1000L * var3);
        var0.append("ms ");
    }

    private static final void formatTimeRaw(StringBuilder var0, long var1) {
        long var3 = var1 / 86400L;
        if(var3 != 0L) {
            var0.append(var3);
            var0.append("d ");
        }

        long var5 = 24L * 60L * var3 * 60L;
        long var7 = (var1 - var5) / 3600L;
        if(var7 != 0L || var5 != 0L) {
            var0.append(var7);
            var0.append("h ");
        }

        long var11 = var5 + 60L * var7 * 60L;
        long var13 = (var1 - var11) / 60L;
        if(var13 != 0L || var11 != 0L) {
            var0.append(var13);
            var0.append("m ");
        }

        long var17 = var11 + var13 * 60L;
        if(var1 != 0L || var17 != 0L) {
            var0.append(var1 - var17);
            var0.append("s ");
        }

    }

    private static final String printWakeLock(StringBuilder var0, BatteryStats.Timer var1, long var2, String var4, int var5, String var6) {
        if(var1 != null) {
            long var7 = (500L + var1.getTotalTimeLocked(var2, var5)) / 1000L;
            int var9 = var1.getCountLocked(var5);
            if(var7 != 0L) {
                var0.append(var6);
                formatTimeMs(var0, var7);
                if(var4 != null) {
                    var0.append(var4);
                }

                var0.append(' ');
                var0.append('(');
                var0.append(var9);
                var0.append(" times)");
                return ", ";
            }
        }

        return var6;
    }

    private static final String printWakeLockCheckin(StringBuilder var0, BatteryStats.Timer var1, long var2, String var4, int var5, String var6) {
        long var7 = 0L;
        int var9 = 0;
        if(var1 != null) {
            var7 = var1.getTotalTimeLocked(var2, var5);
            var9 = var1.getCountLocked(var5);
        }

        var0.append(var6);
        var0.append((500L + var7) / 1000L);
        var0.append(',');
        String var13;
        if(var4 != null) {
            var13 = var4 + ",";
        } else {
            var13 = "";
        }

        var0.append(var13);
        var0.append(var9);
        return ",";
    }

    public abstract long computeBatteryRealtime(long var1, int var3);

    public abstract long computeBatteryUptime(long var1, int var3);

    public abstract long computeRealtime(long var1, int var3);

    public abstract long computeUptime(long var1, int var3);

    public final void dumpCheckinLocked(PrintWriter var1, int var2, int var3) {
        long var4 = 1000L * SystemClock.uptimeMillis();
        long var6 = 1000L * SystemClock.elapsedRealtime();
        long var8 = this.getBatteryUptime(var4);
        long var10 = this.getBatteryRealtime(var6);
        long var12 = this.computeBatteryUptime(var4, var2);
        long var14 = this.computeBatteryRealtime(var6, var2);
        long var16 = this.computeRealtime(var6, var2);
        long var18 = this.computeUptime(var4, var2);
        long var20 = this.getScreenOnTime(var10, var2);
        long var22 = this.getPhoneOnTime(var10, var2);
        long var24 = this.getWifiOnTime(var10, var2);
        long var26 = this.getGlobalWifiRunningTime(var10, var2);
        long var28 = this.getBluetoothOnTime(var10, var2);
        StringBuilder var30 = new StringBuilder(128);
        SparseArray var31 = this.getUidStats();
        int var32 = var31.size();
        String var33 = STAT_NAMES[var2];
        Object[] var34 = new Object[5];
        Object var35;
        if(var2 == 0) {
            var35 = Integer.valueOf(this.getStartCount());
        } else {
            var35 = "N/A";
        }

        var34[0] = var35;
        var34[1] = Long.valueOf(var14 / 1000L);
        var34[2] = Long.valueOf(var12 / 1000L);
        var34[3] = Long.valueOf(var16 / 1000L);
        var34[4] = Long.valueOf(var18 / 1000L);
        dumpLine(var1, 0, var33, "bt", var34);
        long var36 = 0L;
        long var38 = 0L;
        long var40 = 0L;
        long var42 = 0L;

        for(int var44 = 0; var44 < var32; ++var44) {
            BatteryStats.Uid var121 = (BatteryStats.Uid)var31.valueAt(var44);
            var36 += var121.getTcpBytesReceived(var2);
            var38 += var121.getTcpBytesSent(var2);
            Map var122 = var121.getWakelockStats();
            if(var122.size() > 0) {
                Iterator var123 = var122.entrySet().iterator();

                while(var123.hasNext()) {
                    BatteryStats.Wakelock var124 = (BatteryStats.Wakelock)((Entry)var123.next()).getValue();
                    BatteryStats.Timer var125 = var124.getWakeTime(1);
                    if(var125 != null) {
                        var40 += var125.getTotalTimeLocked(var10, var2);
                    }

                    BatteryStats.Timer var126 = var124.getWakeTime(0);
                    if(var126 != null) {
                        var42 += var126.getTotalTimeLocked(var10, var2);
                    }
                }
            }
        }

        Object[] var45 = new Object[]{Long.valueOf(var20 / 1000L), Long.valueOf(var22 / 1000L), Long.valueOf(var24 / 1000L), Long.valueOf(var26 / 1000L), Long.valueOf(var28 / 1000L), Long.valueOf(var36), Long.valueOf(var38), Long.valueOf(var40), Long.valueOf(var42), Integer.valueOf(this.getInputEventCount(var2))};
        dumpLine(var1, 0, var33, "m", var45);
        Object[] var46 = new Object[5];

        for(int var47 = 0; var47 < 5; ++var47) {
            var46[var47] = Long.valueOf(this.getScreenBrightnessTime(var47, var10, var2) / 1000L);
        }

        dumpLine(var1, 0, var33, "br", var46);
        Object[] var48 = new Object[5];

        for(int var49 = 0; var49 < 5; ++var49) {
            var48[var49] = Long.valueOf(this.getPhoneSignalStrengthTime(var49, var10, var2) / 1000L);
        }

        dumpLine(var1, 0, var33, "sgt", var48);
        Object[] var50 = new Object[]{Long.valueOf(this.getPhoneSignalScanningTime(var10, var2) / 1000L)};
        dumpLine(var1, 0, var33, "sst", var50);

        for(int var51 = 0; var51 < 5; ++var51) {
            var48[var51] = Integer.valueOf(this.getPhoneSignalStrengthCount(var51, var2));
        }

        dumpLine(var1, 0, var33, "sgc", var48);
        Object[] var52 = new Object[14];

        for(int var53 = 0; var53 < 14; ++var53) {
            var52[var53] = Long.valueOf(this.getPhoneDataConnectionTime(var53, var10, var2) / 1000L);
        }

        dumpLine(var1, 0, var33, "dct", var52);

        for(int var54 = 0; var54 < 14; ++var54) {
            var52[var54] = Integer.valueOf(this.getPhoneDataConnectionCount(var54, var2));
        }

        dumpLine(var1, 0, var33, "dcc", var52);
        if(var2 == 3) {
            Object[] var120 = new Object[]{Integer.valueOf(this.getDischargeStartLevel()), Integer.valueOf(this.getDischargeCurrentLevel())};
            dumpLine(var1, 0, var33, "lv", var120);
        }

        if(var3 < 0) {
            Map var115 = this.getKernelWakelockStats();
            if(var115.size() > 0) {
                Iterator var116 = var115.entrySet().iterator();

                while(var116.hasNext()) {
                    Entry var117 = (Entry)var116.next();
                    var30.setLength(0);
                    printWakeLockCheckin(var30, (BatteryStats.Timer)var117.getValue(), var10, (String)null, var2, "");
                    Object[] var119 = new Object[]{var117.getKey(), var30.toString()};
                    dumpLine(var1, 0, var33, "kwl", var119);
                }
            }
        }

        for(int var55 = 0; var55 < var32; ++var55) {
            int var56 = var31.keyAt(var55);
            if(var3 < 0 || var56 == var3) {
                BatteryStats.Uid var57 = (BatteryStats.Uid)var31.valueAt(var55);
                long var58 = var57.getTcpBytesReceived(var2);
                long var60 = var57.getTcpBytesSent(var2);
                long var62 = var57.getFullWifiLockTime(var10, var2);
                long var64 = var57.getScanWifiLockTime(var10, var2);
                long var66 = var57.getWifiRunningTime(var10, var2);
                if(var58 > 0L || var60 > 0L) {
                    Object[] var68 = new Object[]{Long.valueOf(var58), Long.valueOf(var60)};
                    dumpLine(var1, var56, var33, "nt", var68);
                }

                if(var62 != 0L || var64 != 0L || var66 != 0L) {
                    Object[] var69 = new Object[]{Long.valueOf(var62), Long.valueOf(var64), Long.valueOf(var66)};
                    dumpLine(var1, var56, var33, "wfl", var69);
                }

                if(var57.hasUserActivity()) {
                    Object[] var111 = new Object[7];
                    boolean var112 = false;

                    for(int var113 = 0; var113 < 7; ++var113) {
                        int var114 = var57.getUserActivityCount(var113, var2);
                        var111[var113] = Integer.valueOf(var114);
                        if(var114 != 0) {
                            var112 = true;
                        }
                    }

                    if(var112) {
                        dumpLine(var1, 0, var33, "ua", var111);
                    }
                }

                Map var70 = var57.getWakelockStats();
                if(var70.size() > 0) {
                    Iterator var104 = var70.entrySet().iterator();

                    while(var104.hasNext()) {
                        Entry var105 = (Entry)var104.next();
                        BatteryStats.Wakelock var106 = (BatteryStats.Wakelock)var105.getValue();
                        var30.setLength(0);
                        String var107 = printWakeLockCheckin(var30, var106.getWakeTime(1), var10, "f", var2, "");
                        String var108 = printWakeLockCheckin(var30, var106.getWakeTime(0), var10, "p", var2, var107);
                        printWakeLockCheckin(var30, var106.getWakeTime(2), var10, "w", var2, var108);
                        if(var30.length() > 0) {
                            Object[] var110 = new Object[]{var105.getKey(), var30.toString()};
                            dumpLine(var1, var56, var33, "wl", var110);
                        }
                    }
                }

                Map var71 = var57.getSensorStats();
                if(var71.size() > 0) {
                    Iterator var95 = var71.entrySet().iterator();

                    while(var95.hasNext()) {
                        Entry var96 = (Entry)var95.next();
                        BatteryStats.Sensor var97 = (BatteryStats.Sensor)var96.getValue();
                        int var98 = ((Integer)var96.getKey()).intValue();
                        BatteryStats.Timer var99 = var97.getSensorTime();
                        if(var99 != null) {
                            long var100 = (500L + var99.getTotalTimeLocked(var10, var2)) / 1000L;
                            int var102 = var99.getCountLocked(var2);
                            if(var100 != 0L) {
                                Object[] var103 = new Object[]{Integer.valueOf(var98), Long.valueOf(var100), Integer.valueOf(var102)};
                                dumpLine(var1, var56, var33, "sr", var103);
                            }
                        }
                    }
                }

                Map var72 = var57.getProcessStats();
                if(var72.size() > 0) {
                    Iterator var86 = var72.entrySet().iterator();

                    label134:
                    while(true) {
                        Entry var87;
                        long var89;
                        long var91;
                        int var93;
                        do {
                            if(!var86.hasNext()) {
                                break label134;
                            }

                            var87 = (Entry)var86.next();
                            BatteryStats.Proc var88 = (BatteryStats.Proc)var87.getValue();
                            var89 = var88.getUserTime(var2);
                            var91 = var88.getSystemTime(var2);
                            var93 = var88.getStarts(var2);
                        } while(var89 == 0L && var91 == 0L && var93 == 0);

                        Object[] var94 = new Object[]{var87.getKey(), Long.valueOf(10L * var89), Long.valueOf(10L * var91), Integer.valueOf(var93)};
                        dumpLine(var1, var56, var33, "pr", var94);
                    }
                }

                Map var73 = var57.getPackageStats();
                if(var73.size() > 0) {
                    Iterator var74 = var73.entrySet().iterator();

                    label121:
                    while(var74.hasNext()) {
                        Entry var75 = (Entry)var74.next();
                        BatteryStats.Pkg var76 = (BatteryStats.Pkg)var75.getValue();
                        int var77 = var76.getWakeups(var2);
                        Iterator var78 = var76.getServiceStats().entrySet().iterator();

                        while(true) {
                            Entry var79;
                            long var81;
                            int var83;
                            int var84;
                            do {
                                if(!var78.hasNext()) {
                                    continue label121;
                                }

                                var79 = (Entry)var78.next();
                                BatteryStats.Serv var80 = (BatteryStats.Serv)var79.getValue();
                                var81 = var80.getStartTime(var8, var2);
                                var83 = var80.getStarts(var2);
                                var84 = var80.getLaunches(var2);
                            } while(var81 == 0L && var83 == 0 && var84 == 0);

                            Object[] var85 = new Object[]{Integer.valueOf(var77), var75.getKey(), var79.getKey(), Long.valueOf(var81 / 1000L), Integer.valueOf(var83), Integer.valueOf(var84)};
                            dumpLine(var1, var56, var33, "apk", var85);
                        }
                    }
                }
            }
        }

    }

    public void dumpCheckinLocked(PrintWriter var1, String[] var2, List<ApplicationInfo> var3) {
        boolean var4 = false;
        int var5 = var2.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            if("-u".equals(var2[var6])) {
                var4 = true;
            }
        }

        if(var3 != null) {
            SparseArray var7 = new SparseArray();
            int var8 = 0;

            while(true) {
                int var9 = var3.size();
                if(var8 >= var9) {
                    SparseArray var10 = this.getUidStats();
                    int var11 = var10.size();
                    String[] var12 = new String[2];

                    for(int var13 = 0; var13 < var11; ++var13) {
                        int var14 = var10.keyAt(var13);
                        ArrayList var15 = (ArrayList)var7.get(var14);
                        if(var15 != null) {
                            int var16 = 0;

                            while(true) {
                                int var17 = var15.size();
                                if(var16 >= var17) {
                                    break;
                                }

                                var12[0] = Integer.toString(var14);
                                var12[1] = (String)var15.get(var16);
                                dumpLine(var1, 0, "i", "uid", (Object[])var12);
                                ++var16;
                            }
                        }
                    }
                    break;
                }

                ApplicationInfo var18 = (ApplicationInfo)var3.get(var8);
                ArrayList var19 = (ArrayList)var7.get(var18.uid);
                if(var19 == null) {
                    var19 = new ArrayList();
                    var7.put(var18.uid, var19);
                }

                String var20 = var18.packageName;
                var19.add(var20);
                ++var8;
            }
        }

        if(var4) {
            this.dumpCheckinLocked(var1, 3, -1);
        } else {
            this.dumpCheckinLocked(var1, 0, -1);
            this.dumpCheckinLocked(var1, 3, -1);
        }
    }

    public void dumpLocked(PrintWriter var1) {
        BatteryStats.HistoryItem var2 = new BatteryStats.HistoryItem();
        if(this.startIteratingHistoryLocked()) {
            var1.println("Battery History:");
            long var19 = this.getHistoryBaseTime() + SystemClock.elapsedRealtime();
            int var21 = 0;
            byte var22 = -1;
            byte var23 = -1;
            byte var24 = -1;
            int var25 = -1;

            for(int var26 = -1; this.getNextHistoryLocked(var2); var21 = var2.states) {
                var1.print("  ");
//                TimeUtils.formatDuration(var2.time - var19, var1, 19);
                var1.print(" ");
                if(var2.cmd == 1) {
                    var1.println(" START");
                } else if(var2.cmd == 2) {
                    var1.println(" *OVERFLOW*");
                } else {
                    if(var2.batteryLevel < 10) {
                        var1.print("00");
                    } else if(var2.batteryLevel < 100) {
                        var1.print("0");
                    }

                    var1.print(var2.batteryLevel);
                    var1.print(" ");
                    if(var2.states < 16) {
                        var1.print("0000000");
                    } else if(var2.states < 256) {
                        var1.print("000000");
                    } else if(var2.states < 4096) {
                        var1.print("00000");
                    } else if(var2.states < 65536) {
                        var1.print("0000");
                    } else if(var2.states < 1048576) {
                        var1.print("000");
                    } else if(var2.states < 16777216) {
                        var1.print("00");
                    } else if(var2.states < 268435456) {
                        var1.print("0");
                    }

                    var1.print(Integer.toHexString(var2.states));
                    byte var27 = var2.batteryStatus;
                    if(var22 != var27) {
                        var22 = var2.batteryStatus;
                        var1.print(" status=");
                        switch(var22) {
                            case 1:
                                var1.print("unknown");
                                break;
                            case 2:
                                var1.print("charging");
                                break;
                            case 3:
                                var1.print("discharging");
                                break;
                            case 4:
                                var1.print("not-charging");
                                break;
                            case 5:
                                var1.print("full");
                                break;
                            default:
                                var1.print(var22);
                        }
                    }

                    byte var28 = var2.batteryHealth;
                    if(var23 != var28) {
                        var23 = var2.batteryHealth;
                        var1.print(" health=");
                        switch(var23) {
                            case 1:
                                var1.print("unknown");
                                break;
                            case 2:
                                var1.print("good");
                                break;
                            case 3:
                                var1.print("overheat");
                                break;
                            case 4:
                                var1.print("dead");
                                break;
                            case 5:
                                var1.print("over-voltage");
                                break;
                            case 6:
                                var1.print("failure");
                                break;
                            default:
                                var1.print(var23);
                        }
                    }

                    byte var29 = var2.batteryPlugType;
                    if(var24 != var29) {
                        var24 = var2.batteryPlugType;
                        var1.print(" plug=");
                        switch(var24) {
                            case 0:
                                var1.print("none");
                                break;
                            case 1:
                                var1.print("ac");
                                break;
                            case 2:
                                var1.print("usb");
                                break;
                            default:
                                var1.print(var24);
                        }
                    }

                    char var30 = var2.batteryTemperature;
                    if(var25 != var30) {
                        var25 = var2.batteryTemperature;
                        var1.print(" temp=");
                        var1.print(var25);
                    }

                    char var31 = var2.batteryVoltage;
                    if(var26 != var31) {
                        var26 = var2.batteryVoltage;
                        var1.print(" volt=");
                        var1.print(var26);
                    }

                    int var32 = var2.states;
                    BatteryStats.BitDescription[] var33 = HISTORY_STATE_DESCRIPTIONS;
                    this.printBitDescriptions(var1, var21, var32, var33);
                    var1.println();
                }
            }

            var1.println("");
        }

        SparseArray var3 = this.getUidStats();
        int var4 = var3.size();
        boolean var5 = false;
        long var6 = SystemClock.elapsedRealtime();
        new StringBuilder(64);

        for(int var8 = 0; var8 < var4; ++var8) {
            SparseArray var9 = ((BatteryStats.Uid)var3.valueAt(var8)).getPidStats();
            if(var9 != null) {
                int var10 = 0;

                while(true) {
                    int var11 = var9.size();
                    if(var10 >= var11) {
                        break;
                    }

                    BatteryStats.Pid var12 = (BatteryStats.Pid)var9.valueAt(var10);
                    if(!var5) {
                        var1.println("Per-PID Stats:");
                        var5 = true;
                    }

                    long var13 = var12.mWakeSum;
                    long var15;
                    if(var12.mWakeStart != 0L) {
                        var15 = var6 - var12.mWakeStart;
                    } else {
                        var15 = 0L;
                    }

                    long var17 = var13 + var15;
                    var1.print("  PID ");
                    var1.print(var9.keyAt(var10));
                    var1.print(" wake time: ");
//                    TimeUtils.formatDuration(var17, var1);
                    var1.println("");
                    ++var10;
                }
            }
        }

        if(var5) {
            var1.println("");
        }

        var1.println("Statistics since last charge:");
        var1.println("  System starts: " + this.getStartCount() + ", currently on battery: " + this.getIsOnBattery());
        this.dumpLocked(var1, "", 0, -1);
        var1.println("");
        var1.println("Statistics since last unplugged:");
        this.dumpLocked(var1, "", 3, -1);
    }

    public final void dumpLocked(PrintWriter var1, String var2, int var3, int var4) {
        long var5 = 1000L * SystemClock.uptimeMillis();
        long var7 = 1000L * SystemClock.elapsedRealtime();
        long var9 = this.getBatteryUptime(var5);
        long var11 = this.getBatteryRealtime(var7);
        long var13 = this.computeBatteryUptime(var5, var3);
        long var15 = this.computeBatteryRealtime(var7, var3);
        long var17 = this.computeRealtime(var7, var3);
        long var19 = this.computeUptime(var5, var3);
        StringBuilder var21 = new StringBuilder(128);
        SparseArray var22 = this.getUidStats();
        int var23 = var22.size();
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Time on battery: ");
        formatTimeMs(var21, var15 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var15, var17));
        var21.append(") realtime, ");
        formatTimeMs(var21, var13 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var13, var17));
        var21.append(") uptime");
        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Total run time: ");
        formatTimeMs(var21, var17 / 1000L);
        var21.append("realtime, ");
        formatTimeMs(var21, var19 / 1000L);
        var21.append("uptime, ");
        var1.println(var21.toString());
        long var36 = this.getScreenOnTime(var11, var3);
        long var38 = this.getPhoneOnTime(var11, var3);
        long var40 = this.getGlobalWifiRunningTime(var11, var3);
        long var42 = this.getWifiOnTime(var11, var3);
        long var44 = this.getBluetoothOnTime(var11, var3);
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Screen on: ");
        formatTimeMs(var21, var36 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var36, var15));
        var21.append("), Input events: ");
        var21.append(this.getInputEventCount(var3));
        var21.append(", Active phone call: ");
        formatTimeMs(var21, var38 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var38, var15));
        var21.append(")");
        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Screen brightnesses: ");
        boolean var58 = false;

        for(int var59 = 0; var59 < 5; ++var59) {
            long var252 = this.getScreenBrightnessTime(var59, var11, var3);
            if(var252 != 0L) {
                if(var58) {
                    var21.append(", ");
                }

                var58 = true;
                var21.append(SCREEN_BRIGHTNESS_NAMES[var59]);
                var21.append(" ");
                formatTimeMs(var21, var252 / 1000L);
                var21.append("(");
                var21.append(this.formatRatioLocked(var252, var36));
                var21.append(")");
            }
        }

        if(!var58) {
            var21.append("No activity");
        }

        var1.println(var21.toString());
        long var60 = 0L;
        long var62 = 0L;
        long var64 = 0L;
        long var66 = 0L;
        if(var4 < 0) {
            Map var244 = this.getKernelWakelockStats();
            if(var244.size() > 0) {
                Iterator var245 = var244.entrySet().iterator();

                while(var245.hasNext()) {
                    Entry var246 = (Entry)var245.next();
                    var21.setLength(0);
                    var21.append(var2);
                    var21.append("  Kernel Wake lock ");
                    var21.append((String)var246.getKey());
                    if(!printWakeLock(var21, (BatteryStats.Timer)var246.getValue(), var11, (String)null, var3, ": ").equals(": ")) {
                        var21.append(" realtime");
                        var1.println(var21.toString());
                    }
                }
            }
        }

        for(int var68 = 0; var68 < var23; ++var68) {
            BatteryStats.Uid var238 = (BatteryStats.Uid)var22.valueAt(var68);
            var60 += var238.getTcpBytesReceived(var3);
            var62 += var238.getTcpBytesSent(var3);
            Map var239 = var238.getWakelockStats();
            if(var239.size() > 0) {
                Iterator var240 = var239.entrySet().iterator();

                while(var240.hasNext()) {
                    BatteryStats.Wakelock var241 = (BatteryStats.Wakelock)((Entry)var240.next()).getValue();
                    BatteryStats.Timer var242 = var241.getWakeTime(1);
                    if(var242 != null) {
                        var64 += var242.getTotalTimeLocked(var11, var3);
                    }

                    BatteryStats.Timer var243 = var241.getWakeTime(0);
                    if(var243 != null) {
                        var66 += var243.getTotalTimeLocked(var11, var3);
                    }
                }
            }
        }

        var1.print(var2);
        var1.print("  Total received: ");
        var1.print(this.formatBytesLocked(var60));
        var1.print(", Total sent: ");
        var1.println(this.formatBytesLocked(var62));
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Total full wakelock time: ");
        formatTimeMs(var21, (500L + var64) / 1000L);
        var21.append(", Total partial waklock time: ");
        formatTimeMs(var21, (500L + var66) / 1000L);
        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Signal levels: ");
        boolean var74 = false;

        for(int var75 = 0; var75 < 5; ++var75) {
            long var228 = this.getPhoneSignalStrengthTime(var75, var11, var3);
            if(var228 != 0L) {
                if(var74) {
                    var21.append(", ");
                }

                var74 = true;
                var21.append(SIGNAL_STRENGTH_NAMES[var75]);
                var21.append(" ");
                formatTimeMs(var21, var228 / 1000L);
                var21.append("(");
                var21.append(this.formatRatioLocked(var228, var15));
                var21.append(") ");
                var21.append(this.getPhoneSignalStrengthCount(var75, var3));
                var21.append("x");
            }
        }

        if(!var74) {
            var21.append("No activity");
        }

        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Signal scanning time: ");
        formatTimeMs(var21, this.getPhoneSignalScanningTime(var11, var3) / 1000L);
        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Radio types: ");
        boolean var80 = false;

        for(int var81 = 0; var81 < 14; ++var81) {
            long var217 = this.getPhoneDataConnectionTime(var81, var11, var3);
            if(var217 != 0L) {
                if(var80) {
                    var21.append(", ");
                }

                var80 = true;
                var21.append(DATA_CONNECTION_NAMES[var81]);
                var21.append(" ");
                formatTimeMs(var21, var217 / 1000L);
                var21.append("(");
                var21.append(this.formatRatioLocked(var217, var15));
                var21.append(") ");
                var21.append(this.getPhoneDataConnectionCount(var81, var3));
                var21.append("x");
            }
        }

        if(!var80) {
            var21.append("No activity");
        }

        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Radio data uptime when unplugged: ");
        var21.append(this.getRadioDataUptime() / 1000L);
        var21.append(" ms");
        var1.println(var21.toString());
        var21.setLength(0);
        var21.append(var2);
        var21.append("  Wifi on: ");
        formatTimeMs(var21, var42 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var42, var15));
        var21.append("), Wifi running: ");
        formatTimeMs(var21, var40 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var40, var15));
        var21.append("), Bluetooth on: ");
        formatTimeMs(var21, var44 / 1000L);
        var21.append("(");
        var21.append(this.formatRatioLocked(var44, var15));
        var21.append(")");
        var1.println(var21.toString());
        var1.println(" ");
        if(var3 == 3) {
            if(this.getIsOnBattery()) {
                var1.print(var2);
                var1.println("  Device is currently unplugged");
                var1.print(var2);
                var1.print("    Discharge cycle start level: ");
                var1.println(this.getDischargeStartLevel());
                var1.print(var2);
                var1.print("    Discharge cycle current level: ");
                var1.println(this.getDischargeCurrentLevel());
            } else {
                var1.print(var2);
                var1.println("  Device is currently plugged into power");
                var1.print(var2);
                var1.print("    Last discharge cycle start level: ");
                var1.println(this.getDischargeStartLevel());
                var1.print(var2);
                var1.print("    Last discharge cycle end level: ");
                var1.println(this.getDischargeCurrentLevel());
            }

            var1.println(" ");
        } else {
            var1.print(var2);
            var1.println("  Device battery use since last full charge");
            var1.print(var2);
            var1.print("    Amount discharged (lower bound): ");
            var1.println(this.getLowDischargeAmountSinceCharge());
            var1.print(var2);
            var1.print("    Amount discharged (upper bound): ");
            var1.println(this.getHighDischargeAmountSinceCharge());
            var1.println(" ");
        }

        for(int var97 = 0; var97 < var23; ++var97) {
            int var98 = var22.keyAt(var97);
            if(var4 < 0 || var98 == var4 || var98 == 1000) {
                BatteryStats.Uid var99 = (BatteryStats.Uid)var22.valueAt(var97);
                var1.println(var2 + "  #" + var98 + ":");
                long var100 = var99.getTcpBytesReceived(var3);
                long var102 = var99.getTcpBytesSent(var3);
                long var104 = var99.getFullWifiLockTime(var11, var3);
                long var106 = var99.getScanWifiLockTime(var11, var3);
                long var108 = var99.getWifiRunningTime(var11, var3);
                if(var100 != 0L || var102 != 0L) {
                    var1.print(var2);
                    var1.print("    Network: ");
                    var1.print(this.formatBytesLocked(var100));
                    var1.print(" received, ");
                    var1.print(this.formatBytesLocked(var102));
                    var1.println(" sent");
                }

                if(var99.hasUserActivity()) {
                    boolean var208 = false;

                    for(int var209 = 0; var209 < 5; ++var209) {
                        int var210 = var99.getUserActivityCount(var209, var3);
                        if(var210 != 0) {
                            if(!var208) {
                                var21.setLength(0);
                                var21.append("    User activity: ");
                                var208 = true;
                            } else {
                                var21.append(", ");
                            }

                            var21.append(var210);
                            var21.append(" ");
                            var21.append(BatteryStats.Uid.USER_ACTIVITY_TYPES[var209]);
                        }
                    }

                    if(var208) {
                        var1.println(var21.toString());
                    }
                }

                if(var104 != 0L || var106 != 0L || var108 != 0L) {
                    var21.setLength(0);
                    var21.append(var2);
                    var21.append("    Wifi Running: ");
                    formatTimeMs(var21, var108 / 1000L);
                    var21.append("(");
                    var21.append(this.formatRatioLocked(var108, var15));
                    var21.append(")\n");
                    var21.append(var2);
                    var21.append("    Full Wifi Lock: ");
                    formatTimeMs(var21, var104 / 1000L);
                    var21.append("(");
                    var21.append(this.formatRatioLocked(var104, var15));
                    var21.append(")\n");
                    var21.append(var2);
                    var21.append("    Scan Wifi Lock: ");
                    formatTimeMs(var21, var106 / 1000L);
                    var21.append("(");
                    var21.append(this.formatRatioLocked(var106, var15));
                    var21.append(")");
                    var1.println(var21.toString());
                }

                Map var125 = var99.getWakelockStats();
                int var126 = var125.size();
                boolean var127 = false;
                if(var126 > 0) {
                    Iterator var199 = var125.entrySet().iterator();

                    while(var199.hasNext()) {
                        Entry var200 = (Entry)var199.next();
                        BatteryStats.Wakelock var201 = (BatteryStats.Wakelock)var200.getValue();
                        var21.setLength(0);
                        var21.append(var2);
                        var21.append("    Wake lock ");
                        var21.append((String)var200.getKey());
                        String var205 = printWakeLock(var21, var201.getWakeTime(1), var11, "full", var3, ": ");
                        String var206 = printWakeLock(var21, var201.getWakeTime(0), var11, "partial", var3, var205);
                        if(!printWakeLock(var21, var201.getWakeTime(2), var11, "window", var3, var206).equals(": ")) {
                            var21.append(" realtime");
                            var1.println(var21.toString());
                            var127 = true;
                        }
                    }
                }

                Map var128 = var99.getSensorStats();
                if(var128.size() > 0) {
                    for(Iterator var180 = var128.entrySet().iterator(); var180.hasNext(); var127 = true) {
                        Entry var181 = (Entry)var180.next();
                        BatteryStats.Sensor var182 = (BatteryStats.Sensor)var181.getValue();
                        ((Integer)var181.getKey()).intValue();
                        var21.setLength(0);
                        var21.append(var2);
                        var21.append("    Sensor ");
                        int var186 = var182.getHandle();
                        if(var186 == -10000) {
                            var21.append("GPS");
                        } else {
                            var21.append(var186);
                        }

                        var21.append(": ");
                        BatteryStats.Timer var189 = var182.getSensorTime();
                        if(var189 != null) {
                            long var191 = (500L + var189.getTotalTimeLocked(var11, var3)) / 1000L;
                            int var193 = var189.getCountLocked(var3);
                            if(var191 != 0L) {
                                formatTimeMs(var21, var191);
                                var21.append("realtime (");
                                var21.append(var193);
                                var21.append(" times)");
                            } else {
                                var21.append("(not used)");
                            }
                        } else {
                            var21.append("(not used)");
                        }

                        var1.println(var21.toString());
                    }
                }

                Map var129 = var99.getProcessStats();
                if(var129.size() > 0) {
                    Iterator var156 = var129.entrySet().iterator();

                    label233:
                    while(true) {
                        Entry var157;
                        BatteryStats.Proc var158;
                        long var159;
                        long var161;
                        int var163;
                        int var164;
                        do {
                            if(!var156.hasNext()) {
                                break label233;
                            }

                            var157 = (Entry)var156.next();
                            var158 = (BatteryStats.Proc)var157.getValue();
                            var159 = var158.getUserTime(var3);
                            var161 = var158.getSystemTime(var3);
                            var163 = var158.getStarts(var3);
                            if(var3 == 0) {
                                var164 = var158.countExcessivePowers();
                            } else {
                                var164 = 0;
                            }
                        } while(var159 == 0L && var161 == 0L && var163 == 0 && var164 == 0);

                        var21.setLength(0);
                        var21.append(var2);
                        var21.append("    Proc ");
                        var21.append((String)var157.getKey());
                        var21.append(":\n");
                        var21.append(var2);
                        var21.append("      CPU: ");
                        formatTime(var21, var159);
                        var21.append("usr + ");
                        formatTime(var21, var161);
                        var21.append("krn");
                        if(var163 != 0) {
                            var21.append("\n");
                            var21.append(var2);
                            var21.append("      ");
                            var21.append(var163);
                            var21.append(" proc starts");
                        }

                        var1.println(var21.toString());

                        for(int var173 = 0; var173 < var164; ++var173) {
                            BatteryStats.ExcessivePower var174 = var158.getExcessivePower(var173);
                            if(var174 != null) {
                                var1.print(var2);
                                var1.print("      * Killed for ");
                                if(var174.type == 1) {
                                    var1.print("wake lock");
                                } else if(var174.type == 2) {
                                    var1.print("cpu");
                                } else {
                                    var1.print("unknown");
                                }

                                var1.print(" use: ");
//                                TimeUtils.formatDuration(var174.usedTime, var1);
                                var1.print(" over ");
//                                TimeUtils.formatDuration(var174.overTime, var1);
                                var1.print(" (");
                                var1.print(100L * var174.usedTime / var174.overTime);
                                var1.println("%)");
                            }
                        }

                        var127 = true;
                    }
                }

                Map var130 = var99.getPackageStats();
                if(var130.size() > 0) {
                    for(Iterator var131 = var130.entrySet().iterator(); var131.hasNext(); var127 = true) {
                        Entry var132 = (Entry)var131.next();
                        var1.print(var2);
                        var1.print("    Apk ");
                        var1.print((String)var132.getKey());
                        var1.println(":");
                        BatteryStats.Pkg var133 = (BatteryStats.Pkg)var132.getValue();
                        int var134 = var133.getWakeups(var3);
                        boolean var135 = false;
                        if(var134 != 0) {
                            var1.print(var2);
                            var1.print("      ");
                            var1.print(var134);
                            var1.println(" wakeup alarms");
                            var135 = true;
                        }

                        Map var136 = var133.getServiceStats();
                        if(var136.size() > 0) {
                            Iterator var137 = var136.entrySet().iterator();

                            label206:
                            while(true) {
                                Entry var138;
                                long var140;
                                int var142;
                                int var143;
                                do {
                                    if(!var137.hasNext()) {
                                        break label206;
                                    }

                                    var138 = (Entry)var137.next();
                                    BatteryStats.Serv var139 = (BatteryStats.Serv)var138.getValue();
                                    var140 = var139.getStartTime(var9, var3);
                                    var142 = var139.getStarts(var3);
                                    var143 = var139.getLaunches(var3);
                                } while(var140 == 0L && var142 == 0 && var143 == 0);

                                var21.setLength(0);
                                var21.append(var2);
                                var21.append("      Service ");
                                var21.append((String)var138.getKey());
                                var21.append(":\n");
                                var21.append(var2);
                                var21.append("        Created for: ");
                                formatTimeMs(var21, var140 / 1000L);
                                var21.append(" uptime\n");
                                var21.append(var2);
                                var21.append("        Starts: ");
                                var21.append(var142);
                                var21.append(", launches: ");
                                var21.append(var143);
                                var1.println(var21.toString());
                                var135 = true;
                            }
                        }

                        if(!var135) {
                            var1.print(var2);
                            var1.println("      (nothing executed)");
                        }
                    }
                }

                if(!var127) {
                    var1.print(var2);
                    var1.println("    (nothing executed)");
                }
            }
        }

    }

    public abstract long getBatteryRealtime(long var1);

    public abstract long getBatteryUptime(long var1);

    public abstract long getBluetoothOnTime(long var1, int var3);

    public abstract int getCpuSpeedSteps();

    public abstract int getDischargeCurrentLevel();

    public abstract int getDischargeStartLevel();

    public abstract long getGlobalWifiRunningTime(long var1, int var3);

    public abstract int getHighDischargeAmountSinceCharge();

    public abstract BatteryStats.HistoryItem getHistory();

    public abstract long getHistoryBaseTime();

    public abstract int getInputEventCount(int var1);

    public abstract boolean getIsOnBattery();

    public abstract Map<String, ? extends BatteryStats.Timer> getKernelWakelockStats();

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract boolean getNextHistoryLocked(BatteryStats.HistoryItem var1);

    public abstract int getPhoneDataConnectionCount(int var1, int var2);

    public abstract long getPhoneDataConnectionTime(int var1, long var2, int var4);

    public abstract long getPhoneOnTime(long var1, int var3);

    public abstract long getPhoneSignalScanningTime(long var1, int var3);

    public abstract int getPhoneSignalStrengthCount(int var1, int var2);

    public abstract long getPhoneSignalStrengthTime(int var1, long var2, int var4);

    public abstract long getRadioDataUptime();

    public long getRadioDataUptimeMs() {
        return this.getRadioDataUptime() / 1000L;
    }

    public abstract long getScreenBrightnessTime(int var1, long var2, int var4);

    public abstract long getScreenOnTime(long var1, int var3);

    public abstract int getStartCount();

    public abstract SparseArray<? extends BatteryStats.Uid> getUidStats();

    public abstract long getWifiOnTime(long var1, int var3);

    void printBitDescriptions(PrintWriter var1, int var2, int var3, BatteryStats.BitDescription[] var4) {
        int var5 = var2 ^ var3;
        if(var5 != 0) {
            for(int var6 = 0; var6 < var4.length; ++var6) {
                BatteryStats.BitDescription var7 = var4[var6];
                if((var5 & var7.mask) != 0) {
                    if(var7.shift < 0) {
                        String var9;
                        if((var3 & var7.mask) != 0) {
                            var9 = " +";
                        } else {
                            var9 = " -";
                        }

                        var1.print(var9);
                        var1.print(var7.name);
                    } else {
                        var1.print(" ");
                        var1.print(var7.name);
                        var1.print("=");
                        int var8 = (var3 & var7.mask) >> var7.shift;
                        if(var7.values != null && var8 >= 0 && var8 < var7.values.length) {
                            var1.print(var7.values[var8]);
                        } else {
                            var1.print(var8);
                        }
                    }
                }
            }
        }

    }

    public abstract boolean startIteratingHistoryLocked();

    public static final class BitDescription {
        public final int mask;
        public final String name;
        public final int shift;
        public final String[] values;

        public BitDescription(int var1, int var2, String var3, String[] var4) {
            this.mask = var1;
            this.shift = var2;
            this.name = var3;
            this.values = var4;
        }

        public BitDescription(int var1, String var2) {
            this.mask = var1;
            this.shift = -1;
            this.name = var2;
            this.values = null;
        }
    }

    public abstract static class Counter {
        public Counter() {
        }

        public abstract int getCountLocked(int var1);

        public abstract void logState(Printer var1, String var2);
    }

    public static final class HistoryItem implements Parcelable {
        public static final byte CMD_OVERFLOW = 2;
        public static final byte CMD_START = 1;
        public static final byte CMD_UPDATE = 0;
        public static final int MOST_INTERESTING_STATES = 2013265920;
        public static final int STATE_AUDIO_ON_FLAG = 524288;
        public static final int STATE_BATTERY_PLUGGED_FLAG = 1073741824;
        public static final int STATE_BLUETOOTH_ON_FLAG = 1048576;
        public static final int STATE_BRIGHTNESS_MASK = 15;
        public static final int STATE_BRIGHTNESS_SHIFT = 0;
        public static final int STATE_DATA_CONNECTION_MASK = 61440;
        public static final int STATE_DATA_CONNECTION_SHIFT = 12;
        public static final int STATE_GPS_ON_FLAG = 268435456;
        public static final int STATE_PHONE_IN_CALL_FLAG = 134217728;
        public static final int STATE_PHONE_SCANNING_FLAG = 67108864;
        public static final int STATE_PHONE_STATE_MASK = 3840;
        public static final int STATE_PHONE_STATE_SHIFT = 8;
        public static final int STATE_SCREEN_ON_FLAG = 536870912;
        public static final int STATE_SENSOR_ON_FLAG = 65536;
        public static final int STATE_SIGNAL_STRENGTH_MASK = 240;
        public static final int STATE_SIGNAL_STRENGTH_SHIFT = 4;
        public static final int STATE_VIDEO_ON_FLAG = 262144;
        public static final int STATE_WAKE_LOCK_FLAG = 131072;
        public static final int STATE_WIFI_FULL_LOCK_FLAG = 8388608;
        public static final int STATE_WIFI_MULTICAST_ON_FLAG = 2097152;
        public static final int STATE_WIFI_ON_FLAG = 33554432;
        public static final int STATE_WIFI_RUNNING_FLAG = 16777216;
        public static final int STATE_WIFI_SCAN_LOCK_FLAG = 4194304;
        public byte batteryHealth;
        public byte batteryLevel;
        public byte batteryPlugType;
        public byte batteryStatus;
        public char batteryTemperature;
        public char batteryVoltage;
        public byte cmd;
        public BatteryStats.HistoryItem next;
        public int states;
        public long time;

        public HistoryItem() {
        }

        public HistoryItem(long var1, Parcel var3) {
            this.time = var1;
            int var4 = var3.readInt();
            this.cmd = (byte)(var4 & 255);
            this.batteryLevel = (byte)(255 & var4 >> 8);
            this.batteryStatus = (byte)(15 & var4 >> 16);
            this.batteryHealth = (byte)(15 & var4 >> 20);
            this.batteryPlugType = (byte)(15 & var4 >> 24);
            int var5 = var3.readInt();
            this.batteryTemperature = (char)(var5 & '\uffff');
            this.batteryVoltage = (char)('\uffff' & var5 >> 16);
            this.states = var3.readInt();
        }

        protected HistoryItem(Parcel in) {
            batteryHealth = in.readByte();
            batteryLevel = in.readByte();
            batteryPlugType = in.readByte();
            batteryStatus = in.readByte();
            batteryTemperature = (char) in.readInt();
            batteryVoltage = (char) in.readInt();
            cmd = in.readByte();
            next = in.readParcelable(HistoryItem.class.getClassLoader());
            states = in.readInt();
            time = in.readLong();
        }

        public static final Creator<HistoryItem> CREATOR = new Creator<HistoryItem>() {
            @Override
            public HistoryItem createFromParcel(Parcel in) {
                return new HistoryItem(in);
            }

            @Override
            public HistoryItem[] newArray(int size) {
                return new HistoryItem[size];
            }
        };

        public int describeContents() {
            return 0;
        }

        public boolean same(BatteryStats.HistoryItem var1) {
            return this.batteryLevel == var1.batteryLevel && this.batteryStatus == var1.batteryStatus && this.batteryHealth == var1.batteryHealth && this.batteryPlugType == var1.batteryPlugType && this.batteryTemperature == var1.batteryTemperature && this.batteryVoltage == var1.batteryVoltage && this.states == var1.states;
        }

        public void setTo(long var1, byte var3, BatteryStats.HistoryItem var4) {
            this.time = var1;
            this.cmd = var3;
            this.batteryLevel = var4.batteryLevel;
            this.batteryStatus = var4.batteryStatus;
            this.batteryHealth = var4.batteryHealth;
            this.batteryPlugType = var4.batteryPlugType;
            this.batteryTemperature = var4.batteryTemperature;
            this.batteryVoltage = var4.batteryVoltage;
            this.states = var4.states;
        }

        public void setTo(BatteryStats.HistoryItem var1) {
            this.time = var1.time;
            this.cmd = var1.cmd;
            this.batteryLevel = var1.batteryLevel;
            this.batteryStatus = var1.batteryStatus;
            this.batteryHealth = var1.batteryHealth;
            this.batteryPlugType = var1.batteryPlugType;
            this.batteryTemperature = var1.batteryTemperature;
            this.batteryVoltage = var1.batteryVoltage;
            this.states = var1.states;
        }

        public void writeToParcel(Parcel var1, int var2) {
            var1.writeLong(this.time);
            var1.writeInt(255 & this.cmd | '\uff00' & this.batteryLevel << 8 | 983040 & this.batteryStatus << 16 | 15728640 & this.batteryHealth << 20 | 251658240 & this.batteryPlugType << 24);
            var1.writeInt('\uffff' & this.batteryTemperature | -65536 & this.batteryVoltage << 16);
            var1.writeInt(this.states);
        }
    }

    public abstract static class Timer {
        public Timer() {
        }

        public abstract int getCountLocked(int var1);

        public abstract long getTotalTimeLocked(long var1, int var3);

        public abstract void logState(Printer var1, String var2);
    }

    public abstract static class Uid {
        public static final int NUM_USER_ACTIVITY_TYPES = 7;
        static final String[] USER_ACTIVITY_TYPES = new String[]{"other", "cheek", "touch", "long_touch", "touch_up", "button", "unknown"};

        public Uid() {
        }

        public abstract long getAudioTurnedOnTime(long var1, int var3);

        public abstract long getFullWifiLockTime(long var1, int var3);

        public abstract Map<String, ? extends BatteryStats.Pkg> getPackageStats();

        public abstract SparseArray<? extends BatteryStats.Pid> getPidStats();

        public abstract Map<String, ? extends BatteryStats.Proc> getProcessStats();

        public abstract long getScanWifiLockTime(long var1, int var3);

        public abstract Map<Integer, ? extends BatteryStats.Sensor> getSensorStats();

        public abstract long getTcpBytesReceived(int var1);

        public abstract long getTcpBytesSent(int var1);

        public abstract int getUid();

        public abstract int getUserActivityCount(int var1, int var2);

        public abstract long getVideoTurnedOnTime(long var1, int var3);

        public abstract Map<String, ? extends BatteryStats.Wakelock> getWakelockStats();

        public abstract long getWifiMulticastTime(long var1, int var3);

        public abstract long getWifiRunningTime(long var1, int var3);

        public abstract boolean hasUserActivity();

        public abstract void noteAudioTurnedOffLocked();

        public abstract void noteAudioTurnedOnLocked();

        public abstract void noteFullWifiLockAcquiredLocked();

        public abstract void noteFullWifiLockReleasedLocked();

        public abstract void noteScanWifiLockAcquiredLocked();

        public abstract void noteScanWifiLockReleasedLocked();

        public abstract void noteUserActivityLocked(int var1);

        public abstract void noteVideoTurnedOffLocked();

        public abstract void noteVideoTurnedOnLocked();

        public abstract void noteWifiMulticastDisabledLocked();

        public abstract void noteWifiMulticastEnabledLocked();

        public abstract void noteWifiRunningLocked();

        public abstract void noteWifiStoppedLocked();
    }

    public class Pid {
        public long mWakeStart;
        public long mWakeSum;

        public Pid() {
        }
    }

    public abstract static class Pkg {
        public Pkg() {
        }

        public abstract Map<String, ? extends BatteryStats.Serv> getServiceStats();

        public abstract int getWakeups(int var1);
    }

    public abstract class Serv {
        public Serv() {
        }

        public abstract int getLaunches(int var1);

        public abstract long getStartTime(long var1, int var3);

        public abstract int getStarts(int var1);
    }

    public abstract static class Proc {
        public Proc() {
        }

        public abstract int countExcessivePowers();

        public abstract BatteryStats.ExcessivePower getExcessivePower(int var1);

        public abstract long getForegroundTime(int var1);

        public abstract int getStarts(int var1);

        public abstract long getSystemTime(int var1);

        public abstract long getTimeAtCpuSpeedStep(int var1, int var2);

        public abstract long getUserTime(int var1);
    }

    public static class ExcessivePower {
        public static final int TYPE_CPU = 2;
        public static final int TYPE_WAKE = 1;
        public long overTime;
        public int type;
        public long usedTime;

        public ExcessivePower() {
        }
    }

    public abstract static class Sensor {
        public static final int GPS = -10000;

        public Sensor() {
        }

        public abstract int getHandle();

        public abstract BatteryStats.Timer getSensorTime();
    }

    public abstract static class Wakelock {
        public Wakelock() {
        }

        public abstract BatteryStats.Timer getWakeTime(int var1);
    }
}
