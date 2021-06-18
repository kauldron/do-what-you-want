package com.lockwood.automata.android

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import java.util.*

private object LegacyServiceMapHolder {

    val SERVICES = HashMap<Class<*>, String>()

    init {
        if (Build.VERSION.SDK_INT >= 22) {
            SERVICES[SubscriptionManager::class.java] = Context.TELEPHONY_SUBSCRIPTION_SERVICE
            SERVICES[UsageStatsManager::class.java] = Context.USAGE_STATS_SERVICE
        }
        SERVICES[AppWidgetManager::class.java] = Context.APPWIDGET_SERVICE
        SERVICES[BatteryManager::class.java] = Context.BATTERY_SERVICE
        SERVICES[CameraManager::class.java] = Context.CAMERA_SERVICE
        SERVICES[JobScheduler::class.java] = Context.JOB_SCHEDULER_SERVICE
        SERVICES[LauncherApps::class.java] = Context.LAUNCHER_APPS_SERVICE
        SERVICES[MediaProjectionManager::class.java] = Context.MEDIA_PROJECTION_SERVICE
        SERVICES[MediaSessionManager::class.java] = Context.MEDIA_SESSION_SERVICE
        SERVICES[RestrictionsManager::class.java] = Context.RESTRICTIONS_SERVICE
        SERVICES[TelecomManager::class.java] = Context.TELECOM_SERVICE
        SERVICES[TvInputManager::class.java] = Context.TV_INPUT_SERVICE
        SERVICES[AppOpsManager::class.java] = Context.APP_OPS_SERVICE
        SERVICES[CaptioningManager::class.java] = Context.CAPTIONING_SERVICE
        SERVICES[ConsumerIrManager::class.java] = Context.CONSUMER_IR_SERVICE
        SERVICES[PrintManager::class.java] = Context.PRINT_SERVICE
        SERVICES[BluetoothManager::class.java] = Context.BLUETOOTH_SERVICE
        SERVICES[DisplayManager::class.java] = Context.DISPLAY_SERVICE
        SERVICES[UserManager::class.java] = Context.USER_SERVICE
        SERVICES[InputManager::class.java] = Context.INPUT_SERVICE
        SERVICES[MediaRouter::class.java] = Context.MEDIA_ROUTER_SERVICE
        SERVICES[NsdManager::class.java] = Context.NSD_SERVICE
        SERVICES[AccessibilityManager::class.java] = Context.ACCESSIBILITY_SERVICE
        SERVICES[AccountManager::class.java] = Context.ACCOUNT_SERVICE
        SERVICES[ActivityManager::class.java] = Context.ACTIVITY_SERVICE
        SERVICES[AlarmManager::class.java] = Context.ALARM_SERVICE
        SERVICES[AudioManager::class.java] = Context.AUDIO_SERVICE
        SERVICES[ClipboardManager::class.java] = Context.CLIPBOARD_SERVICE
        SERVICES[ConnectivityManager::class.java] = Context.CONNECTIVITY_SERVICE
        SERVICES[DevicePolicyManager::class.java] = Context.DEVICE_POLICY_SERVICE
        SERVICES[DownloadManager::class.java] = Context.DOWNLOAD_SERVICE
        SERVICES[DropBoxManager::class.java] = Context.DROPBOX_SERVICE
        SERVICES[InputMethodManager::class.java] = Context.INPUT_METHOD_SERVICE
        SERVICES[KeyguardManager::class.java] = Context.KEYGUARD_SERVICE
        SERVICES[LayoutInflater::class.java] = Context.LAYOUT_INFLATER_SERVICE
        SERVICES[LocationManager::class.java] = Context.LOCATION_SERVICE
        SERVICES[NfcManager::class.java] = Context.NFC_SERVICE
        SERVICES[NotificationManager::class.java] = Context.NOTIFICATION_SERVICE
        SERVICES[PowerManager::class.java] = Context.POWER_SERVICE
        SERVICES[SearchManager::class.java] = Context.SEARCH_SERVICE
        SERVICES[SensorManager::class.java] = Context.SENSOR_SERVICE
        SERVICES[StorageManager::class.java] = Context.STORAGE_SERVICE
        SERVICES[TelephonyManager::class.java] = Context.TELEPHONY_SERVICE
        SERVICES[TextServicesManager::class.java] = Context.TEXT_SERVICES_MANAGER_SERVICE
        SERVICES[UiModeManager::class.java] = Context.UI_MODE_SERVICE
        SERVICES[UsbManager::class.java] = Context.USB_SERVICE
        SERVICES[Vibrator::class.java] = Context.VIBRATOR_SERVICE
        SERVICES[WallpaperManager::class.java] = Context.WALLPAPER_SERVICE
        SERVICES[WifiP2pManager::class.java] = Context.WIFI_P2P_SERVICE
        SERVICES[WifiManager::class.java] = Context.WIFI_SERVICE
        SERVICES[WindowManager::class.java] = Context.WINDOW_SERVICE
    }
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun <reified T> Context.getSystemService(): T {
    if (Build.VERSION.SDK_INT >= 23) {
        return getSystemService(T::class.java)
    }

    val serviceName = getCompatSystemServiceName(T::class.java)
    checkNotNull(serviceName)

    return getSystemService(serviceName) as T
}

fun Context.getCompatSystemServiceName(
    serviceClass: Class<*>,
): String? {
    return if (Build.VERSION.SDK_INT >= 23) {
        getSystemServiceName(serviceClass)
    } else {
        LegacyServiceMapHolder.SERVICES[serviceClass]
    }
}