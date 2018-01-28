package com.common.utils.battery;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.BatteryStats;
import android.os.BatteryStats.Uid;
import android.os.IBinder;
import android.os.Parcel;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;

import com.common.utils.process.ProcessManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatteryInfo {

    private static final String TAG = "BatteryInfo";
    private static final boolean DEBUG = false;

    public static final int MSG_UPDATE_NAME_ICON = 1;
    private static final int MIN_POWER_THRESHOLD = 5;

    private Object mBatteryInfo;
    private int mStatsType = 2;
    private Object mPowerProfile;
    private static Object mStats;

    private double mMinPercentOfTotal = 0;
    private long mStatsPeriod = 0;
    public double mMaxPower = 1;
    private double mTotalPower;
    private double mWifiPower;
    private double mBluetoothPower;

    private long mAppWifiRunning;

    private final List<BatterySipper> mUsageList = new ArrayList<BatterySipper>();
    private final List<BatterySipper> mWifiSippers = new ArrayList<BatterySipper>();
    private final List<BatterySipper> mBluetoothSippers = new ArrayList<BatterySipper>();
    private Context mContext;
    public int testType;

    public enum DrainType {
        IDLE, CELL, PHONE, WIFI, BLUETOOTH, SCREEN, APP, KERNEL, MEDIASERVER;
    }

    public BatteryInfo(Context context) {
        testType = 1;
        mContext = context;
        try {
            mBatteryInfo = RefInvoker.getObjectFromMethod(null, RefInvoker.getClass("com.android.internal.app.IBatteryStats.Stub"), "asInterface", new Class[]{IBinder.class}, new Object[]{HackServiceManager.getIBinderMap().get("batterystats")});
            mPowerProfile = HackPowerProfile.getPowerProfile(context);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * 设置最小百分比，小于该值的程序将被过滤掉
     *
     * @param minPercentOfTotal
     */
    public void setMinPercentOfTotal(double minPercentOfTotal) {
        this.mMinPercentOfTotal = minPercentOfTotal;
    }

    /**
     * 获取消耗的总量
     *
     * @return
     */
    public double getTotalPower() {
        return mTotalPower;
    }

    /**
     * 获取电池的使用时间
     *
     * @return
     */
    public long getStatsPeriod() {
        return mStatsPeriod;
    }

    public List<BatterySipper> getBatteryStats() {
        if (mStats == null) {
            mStats = load();
        }

        if (mStats == null) {
            Log.i("BatteryInfo", "data isExist");
            return getAppListCpuTime();
        }

        mMaxPower = 0;
        mTotalPower = 0;
        mWifiPower = 0;
        mBluetoothPower = 0;
        mAppWifiRunning = 0;

        mUsageList.clear();
        mWifiSippers.clear();
        mBluetoothSippers.clear();
        processAppUsage();
        processMiscUsage();

        final List<BatterySipper> list = new ArrayList<BatterySipper>();

        Collections.sort(mUsageList);
        for (BatterySipper sipper : mUsageList) {
            if (sipper.getValue() < MIN_POWER_THRESHOLD)
                continue;
            final double percentOfTotal = ((sipper.getValue() / mTotalPower) * 100);
            sipper.setPercent(percentOfTotal);
            if (percentOfTotal < mMinPercentOfTotal)
                continue;
            list.add(sipper);
        }

        if (list.size() <= 1) {
            return getAppListCpuTime();
        }

        return list;
    }

    private long getAppProcessTime(int pid) {
        FileInputStream in = null;
        String ret = null;
        try {
            in = new FileInputStream("/proc/" + pid + "/stat");
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            ret = os.toString();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (ret == null) {
            return 0;
        }

        String[] s = ret.split(" ");
        if (s == null || s.length < 17) {
            return 0;
        }

        final long utime = string2Long(s[13]);
        final long stime = string2Long(s[14]);
        final long cutime = string2Long(s[15]);
        final long cstime = string2Long(s[16]);

        return utime + stime + cutime + cstime;
    }

    private long string2Long(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    private List<BatterySipper> getAppListCpuTime() {
        testType = 2;
        final List<BatterySipper> list = new ArrayList<BatterySipper>();

        long totalTime = 0;
        List<RunningAppProcessInfo> runningApps = ProcessManager.getIProcess().getRunningAppProcesses(mContext);

        HashMap<String, BatterySipper> tempList = new HashMap<String, BatterySipper>();
        for (RunningAppProcessInfo info : runningApps) {
            final long time = getAppProcessTime(info.pid);
            String[] pkgNames = info.pkgList;
            if (pkgNames == null) {
                if (tempList.containsKey(info.processName)) {
                    BatterySipper sipper = tempList.get(info.processName);
                    sipper.setValue(sipper.getValue() + time);
                } else {
                    tempList.put(info.processName, new BatterySipper(mContext, info.processName, time));
                }
                totalTime += time;
            } else {
                for (String pkgName : pkgNames) {
                    if (tempList.containsKey(pkgName)) {
                        BatterySipper sipper = tempList.get(pkgName);
                        sipper.setValue(sipper.getValue() + time);
                    } else {
                        tempList.put(pkgName, new BatterySipper(mContext, pkgName, time));
                    }
                    totalTime += time;
                }
            }
        }

        if (totalTime == 0) totalTime = 1;

        list.addAll(tempList.values());
        for (int i = list.size() - 1; i >= 0; i--) {
            BatterySipper sipper = list.get(i);
            double percentOfTotal = sipper.getValue() * 100 / totalTime;
            if (percentOfTotal < mMinPercentOfTotal) {
                list.remove(i);
            } else {
                sipper.setPercent(percentOfTotal);
            }
        }

        Collections.sort(list, new Comparator<BatterySipper>() {
            @Override
            public int compare(BatterySipper object1, BatterySipper object2) {
                double d1 = object1.getPercentOfTotal();
                double d2 = object2.getPercentOfTotal();
                if (d1 - d2 < 0) {
                    return 1;
                } else if (d1 - d2 > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return list;
    }

    private void processMiscUsage() {
        final int which = mStatsType;
        long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long uSecNow = ((Long) HackBatteryStatsImpl.computeBatteryRealtime(mStats, elapsedRealtime, which)).longValue();
        final long timeSinceUnplugged = uSecNow;
        if (DEBUG) {
            Log.i(TAG, "Uptime since last unplugged = " + (timeSinceUnplugged / 1000));
        }

        addPhoneUsage(uSecNow);
        addScreenUsage(uSecNow);
        addWiFiUsage(uSecNow);
        addBluetoothUsage(uSecNow);
        addIdleUsage(uSecNow); // Not including cellular idle power
        addRadioUsage(uSecNow);
    }

    private void addPhoneUsage(long uSecNow) {
        long phoneOnTimeMs = ((Long) HackBatteryStatsImpl.getPhoneOnTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        double phoneOnPower = (((Double) HackPowerProfile.getAveragePower(mPowerProfile, "radio.active")).doubleValue() * ((double) phoneOnTimeMs)) / 1000.0d;
        addEntry(DrainType.PHONE, phoneOnTimeMs, phoneOnPower);
    }

    private void addScreenUsage(long uSecNow) {
        double power = 0;
        long screenOnTimeMs = ((Long) HackBatteryStatsImpl.getPhoneOnTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        power += (((double) screenOnTimeMs) * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "screen.on")).doubleValue());
        final double screenFullPower = ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "screen.full")).doubleValue();
        for (int i = 0; i < 5; i++) {
            double screenBinPower = screenFullPower * (i + 0.5f) / 5.0d;
            long brightnessTime = ((Long) HackBatteryStatsImpl.getScreenBrightnessTime(mStats, i, uSecNow, mStatsType)).longValue() / 1000;
            power += screenBinPower * brightnessTime;
            if (DEBUG) {
                Log.i(TAG, "Screen bin power = " + (int) screenBinPower + ", time = " + brightnessTime);
            }
        }
        power /= 1000; // To seconds
        addEntry(DrainType.SCREEN, screenOnTimeMs, power);
    }

    private void addWiFiUsage(long uSecNow) {
        if (!versionValid()) {// less than 2.3.3
            return;
        }

        long onTimeMs = ((Long) HackBatteryStatsImpl.getWifiOnTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        long runningTimeMs = ((Long) HackBatteryStatsImpl.getGlobalWifiRunningTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        if (DEBUG)
            Log.i(TAG, "WIFI runningTime=" + runningTimeMs + " app runningTime=" + mAppWifiRunning);
        runningTimeMs -= mAppWifiRunning;
        if (runningTimeMs < 0)
            runningTimeMs = 0;
        double wifiPower = ((((Double) HackPowerProfile.getAveragePower(mPowerProfile, "wifi.on")).doubleValue() * ((double) runningTimeMs)) + (((double) (onTimeMs * 0)) * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "wifi.on")).doubleValue())) / 1000.0d;
        if (DEBUG)
            Log.i(TAG, "WIFI power=" + wifiPower + " from procs=" + mWifiPower);
        BatterySipper bs = addEntry(DrainType.WIFI, runningTimeMs, wifiPower + mWifiPower);
        aggregateSippers(bs, mWifiSippers, "WIFI");
    }

    private void addBluetoothUsage(long uSecNow) {
        long btOnTimeMs = ((Long) HackBatteryStatsImpl.getBluetoothOnTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        double btPower = btOnTimeMs * ((((Double) HackPowerProfile.getAveragePower(mPowerProfile, "bluetooth.on")).doubleValue() * (double) btOnTimeMs)) / 1000.0d;
        int btPingCount = ((Integer) HackBatteryStatsImpl.getBluetoothPingCount(mStats)).intValue();
        btPower += (btPingCount * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "bluetooth.at")).doubleValue()) / 1000.d;
        BatterySipper bs = addEntry(DrainType.BLUETOOTH, btOnTimeMs, btPower + mBluetoothPower);
        aggregateSippers(bs, mBluetoothSippers, "Bluetooth");
    }

    private void addIdleUsage(long uSecNow) {
        long idleTimeMs = (uSecNow - ((Long) HackBatteryStatsImpl.getScreenOnTime(mStats, uSecNow, mStatsType)).longValue()) / 1000;
        double idlePower = (((Double) HackPowerProfile.getAveragePower(mPowerProfile, "cpu.idle")).doubleValue() * ((double) idleTimeMs)) / 1000.0d;
        addEntry(DrainType.IDLE, idleTimeMs, idlePower);
    }

    private void addRadioUsage(long uSecNow) {
        double power = 0;
        final int BINS = 5;
        long signalTimeMs = 0;
        for (int i = 0; i < BINS; i++) {
            long strengthTimeMs = ((Long) HackBatteryStatsImpl.getPhoneSignalStrengthTime(mStats, i, uSecNow, mStatsType)).longValue() / 1000;
            power += ((double) (strengthTimeMs / 1000)) * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "cpu.idle", i)).doubleValue();
            signalTimeMs += strengthTimeMs;
        }

        long scanningTimeMs = ((Long) HackBatteryStatsImpl.getPhoneSignalScanningTime(mStats, uSecNow, mStatsType)).longValue() / 1000;
        power += scanningTimeMs / 1000 * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "radio.scanning")).doubleValue();
        BatterySipper bs = addEntry(DrainType.CELL, signalTimeMs, power);
        if (signalTimeMs != 0) {
            bs.noCoveragePercent = (((double) (((Long) HackBatteryStatsImpl.getPhoneSignalStrengthTime(mStats, 0, uSecNow, mStatsType)).longValue() / 1000)) * 100.0d) / ((double) signalTimeMs);
        }
    }

    private void aggregateSippers(BatterySipper bs, List<BatterySipper> from, String tag) {
        for (int i = 0; i < from.size(); i++) {
            BatterySipper wbs = from.get(i);
            if (DEBUG)
                Log.i(TAG, tag + " adding sipper " + wbs + ": cpu=" + wbs.cpuTime);
            bs.cpuTime += wbs.cpuTime;
            bs.gpsTime += wbs.gpsTime;
            bs.wifiRunningTime += wbs.wifiRunningTime;
            bs.cpuFgTime += wbs.cpuFgTime;
            bs.wakeLockTime += wbs.wakeLockTime;
            bs.tcpBytesReceived += wbs.tcpBytesReceived;
            bs.tcpBytesSent += wbs.tcpBytesSent;
        }
    }

    private BatterySipper addEntry(DrainType drainType, long time, double power) {
        if (power > mMaxPower)
            mMaxPower = power;
        mTotalPower += power;
        BatterySipper bs = new BatterySipper(mContext, drainType, null, new double[]{power});
        bs.usageTime = time;
        mUsageList.add(bs);
        return bs;
    }

    private boolean versionValid() {
        return android.os.Build.VERSION.SDK_INT >= 10;// less than 2.3.3
    }

    private void processAppUsage() {
        SensorManager sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        final int which = mStatsType;
        int speedSteps = ((Integer) HackPowerProfile.getNumSpeedSteps(mPowerProfile)).intValue();
        final double[] powerCpuNormal = new double[speedSteps];
        final long[] cpuSpeedStepTimes = new long[speedSteps];
        for (int p = 0; p < speedSteps; p++) {
            powerCpuNormal[p] = ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "cpu.active", p)).doubleValue();
        }

        final double averageCostPerByte = getAverageDataCost();
        long uSecTime = ((Long) HackBatteryStatsImpl.computeBatteryRealtime(mStats, SystemClock.elapsedRealtime() * 1000, which)).longValue();
        mStatsPeriod = uSecTime;
        SparseArray uidStats = (SparseArray) HackBatteryStatsImpl.getUidStats(mStats);
        final int NU = uidStats.size();
        for (int iu = 0; iu < NU; iu++) {
            Uid u = (Uid) uidStats.valueAt(iu);
            double power = 0;
            double highestDrain = 0;
            String packageWithHighestDrain = null;
            Map<String, ? extends BatteryStats.Proc> processStats = u.getProcessStats();
            long cpuTime = 0;
            long cpuFgTime = 0;
            long wakelockTime = 0;
            long gpsTime = 0;
            if (processStats.size() > 0) {
                // 1, Process CPU time
                for (Map.Entry<String, ? extends BatteryStats.Proc> ent : processStats.entrySet()) {
                    if (DEBUG)
                        Log.i(TAG, "Process name = " + ent.getKey());

                    BatteryStats.Proc ps = ent.getValue();
                    final long userTime = ps.getUserTime(which);
                    final long systemTime = ps.getSystemTime(which);
                    final long foregroundTime = ps.getForegroundTime(which);
                    cpuFgTime += foregroundTime * 10; // convert to millis
                    final long tmpCpuTime = (userTime + systemTime) * 10; // convert to millis
                    int totalTimeAtSpeeds = 0;
                    // Get the total first
                    for (int step = 0; step < speedSteps; step++) {
                        cpuSpeedStepTimes[step] = ps.getTimeAtCpuSpeedStep(step, which);
                        totalTimeAtSpeeds += cpuSpeedStepTimes[step];
                    }
                    if (totalTimeAtSpeeds == 0)
                        totalTimeAtSpeeds = 1;
                    // Then compute the ratio of time spent at each speed
                    double processPower = 0;
                    for (int step = 0; step < speedSteps; step++) {
                        double ratio = (double) cpuSpeedStepTimes[step] / totalTimeAtSpeeds;
                        processPower += ratio * tmpCpuTime * powerCpuNormal[step];
                    }
                    cpuTime += tmpCpuTime;
                    power += processPower;
                    if (packageWithHighestDrain == null || packageWithHighestDrain.startsWith("*")) {
                        highestDrain = processPower;
                        packageWithHighestDrain = ent.getKey();
                    } else if (highestDrain < processPower && !ent.getKey().startsWith("*")) {
                        highestDrain = processPower;
                        packageWithHighestDrain = ent.getKey();
                    }
                }
            }
            if (cpuFgTime > cpuTime) {
                if (DEBUG && cpuFgTime > cpuTime + 10000) {
                    Log.i(TAG, "WARNING! Cputime is more than 10 seconds behind Foreground time");
                }
                cpuTime = cpuFgTime; // Statistics may not have been gathered yet.
            }
            power /= 1000;

            // 2, Process wake lock usage
            Map<String, ? extends BatteryStats.Wakelock> wakelockStats = u.getWakelockStats();
            for (Map.Entry<String, ? extends BatteryStats.Wakelock> wakelockEntry : wakelockStats.entrySet()) {
                BatteryStats.Wakelock wakelock = wakelockEntry.getValue();
                // Only care about partial wake locks since full wake locks are canceled when the user turns the screen off.
                BatteryStats.Timer timer = wakelock.getWakeTime(BatteryStats.WAKE_TYPE_PARTIAL);
                if (timer != null) {
                    wakelockTime += timer.getTotalTimeLocked(uSecTime, which);
                }
            }
            wakelockTime /= 1000; // convert to millis
            // Add cost of holding a wake lock
            power += (wakelockTime * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "cpu.awake")).doubleValue()) / 1000;

            // 3, Add cost of data traffic
            long tcpBytesReceived = u.getTcpBytesReceived(mStatsType);
            long tcpBytesSent = u.getTcpBytesSent(mStatsType);
            power += (tcpBytesReceived + tcpBytesSent) * averageCostPerByte;

            // 4, Add cost of keeping WIFI running.
            if (versionValid()) {
                long wifiRunningTimeMs = u.getWifiRunningTime(uSecTime, which) / 1000;
                mAppWifiRunning += wifiRunningTimeMs;
                power += (wifiRunningTimeMs * ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "wifi.on")).doubleValue()) / 1000.0d;
            }

            // 5, Process Sensor usage
            Map<Integer, ? extends BatteryStats.Sensor> sensorStats = u.getSensorStats();
            for (Map.Entry<Integer, ? extends BatteryStats.Sensor> sensorEntry : sensorStats.entrySet()) {
                BatteryStats.Sensor sensor = sensorEntry.getValue();
                int sensorType = sensor.getHandle();
                BatteryStats.Timer timer = sensor.getSensorTime();
                long sensorTime = timer.getTotalTimeLocked(uSecTime, which) / 1000;
                double multiplier = 0;
                switch (sensorType) {
                    case BatteryStats.Sensor.GPS:
                        multiplier = ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "gps.on")).doubleValue();
                        gpsTime = sensorTime;
                        break;
                    default:
                        android.hardware.Sensor sensorData = sensorManager.getDefaultSensor(sensorType);
                        if (sensorData != null) {
                            multiplier = sensorData.getPower();
                            if (DEBUG) {
                                Log.i(TAG, "Got sensor " + sensorData.getName() + " with power = " + multiplier);
                            }
                        }
                }
                power += (multiplier * sensorTime) / 1000;
            }

            if (DEBUG)
                Log.i(TAG, "UID " + u.getUid() + ": power=" + power);

            // Add the app to the list if it is consuming power
            if (power != 0) {
                BatterySipper app = new BatterySipper(mContext, DrainType.APP, u, new double[]{power});
                app.cpuTime = cpuTime;
                app.gpsTime = gpsTime;
                // app.wifiRunningTime = wifiRunningTimeMs;
                app.cpuFgTime = cpuFgTime;
                app.wakeLockTime = wakelockTime;
                app.tcpBytesReceived = tcpBytesReceived;
                app.tcpBytesSent = tcpBytesSent;
//                if (u.getUid() == Process.WIFI_UID) {
//                    mWifiSippers.add(app);
//                } else if (u.getUid() == Process.BLUETOOTH_GID) {
//                    mBluetoothSippers.add(app);
//                } else {
//                    mUsageList.add(app);
//                }
            }
//            if (u.getUid() == Process.WIFI_UID) {
//                mWifiPower += power;
//            } else if (u.getUid() == Process.BLUETOOTH_GID) {
//                mBluetoothPower += power;
//            } else {
//                if (power > mMaxPower)
//                    mMaxPower = power;
//                mTotalPower += power;
//            }

            if (DEBUG)
                Log.i(TAG, "Added power = " + power);
        }
    }

    /**
     * 计算每字节的平均消耗
     *
     * @return
     */
    private double getAverageDataCost() {
        final long WIFI_BPS = 1000000; // TODO: Extract average bit rates from system
        final long MOBILE_BPS = 200000; // TODO: Extract average bit rates from system
        final double WIFI_POWER = ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "radio.active")).doubleValue() / 3600.0d;
        final double MOBILE_POWER = ((Double) HackPowerProfile.getAveragePower(mPowerProfile, "wifi.active")).doubleValue() / 3600.0d;

        // 传输字节数量
        long mobileData = ((Long) HackBatteryStatsImpl.getMobileTcpBytesReceived(mStats, mStatsType)).longValue() + ((Long) HackBatteryStatsImpl.getMobileTcpBytesSent(mStats, mStatsType)).longValue();
        long wifiData = (((Long) HackBatteryStatsImpl.getTotalTcpBytesSent(mStats, mStatsType)).longValue() + ((Long) HackBatteryStatsImpl.getTotalTcpBytesReceived(mStats, mStatsType)).longValue()) - mobileData;
        // 传输时间(毫秒)
        long radioDataUptimeMs = ((Long) HackBatteryStatsImpl.getRadioDataUptime(mStats)).longValue() / 1000;
        // 比特率(bps)
        final long mobileBps = radioDataUptimeMs != 0 ? mobileData * 8 * 1000 / radioDataUptimeMs : MOBILE_BPS;
        // 每秒每字节的消耗
        double mobileCostPerByte = MOBILE_POWER / (mobileBps / 8);
        // wifi每秒每字节的消耗
        double wifiCostPerByte = WIFI_POWER / (WIFI_BPS / 8);

        // 平均消耗
        if (wifiData + mobileData != 0) {
            return (mobileCostPerByte * mobileData + wifiCostPerByte * wifiData) / (mobileData + wifiData);
        } else {
            return 0;
        }
    }

    private Object load() {
        Object mStats = null;
        try {
            byte[] data = (byte[]) RefInvoker.getObjectFromMethod(mBatteryInfo, mBatteryInfo.getClass(), "getStatistics", null, null);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(data, 0, data.length);
            parcel.setDataPosition(0);
            mStats = RefInvoker.getObjectFromMethod(null, RefInvoker.getClass("com.android.internal.os.BatteryStatsImpl.CREATOR"), "createFromParcel", new Class[]{Parcel.class}, new Object[]{parcel});
            if (versionValid()) {
                HackBatteryStatsImpl.distributeWorkLocked(mStats, 0);
            }
        } catch (Exception e) {
            Log.e(TAG, "RemoteException:", e);
        } catch (Error e) {
            Log.e(TAG, "Error:", e);
        }
        return mStats;
    }
}
