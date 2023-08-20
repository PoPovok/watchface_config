package hu.popoapps.watchface_config

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import hu.popoapps.watchface_config.debug.DebugComponent
import hu.popoapps.watchface_config.model.ComplicationInfo
import hu.popoapps.watchface_config.model.ComplicationSlotInfo
import hu.popoapps.watchface_config.model.WatchFaceInfo
import hu.popoapps.watchface_config.util.toBase64

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

/** WatchfaceConfigPlugin */
class WatchfaceConfigPlugin : FlutterPlugin {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var configChannel: MethodChannel
    private lateinit var watchfaceInfoChannel: MethodChannel
    private lateinit var applicationContext: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        configChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "Config").apply {
            setMethodCallHandler { call, result -> configMethodCall(call, result) }
        }
        watchfaceInfoChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "WatchFaceInfo").apply {
            setMethodCallHandler { call, result -> watchfaceInfoMethodCall(call, result) }
        }
        applicationContext = flutterPluginBinding.applicationContext
    }

    private fun configMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
            "retrieveInitialComplicationsData" -> {
                Log.d("WatchfaceConfig", "Please open the Watchface's config in order to get valid data")
                val supportedTypeList = call.argument<List<List<Int>>>("supportedTypeList")!!
                configChannel.invokeMethod(
                    "retrieveInitialComplicationsData",
                    List(supportedTypeList.size) { index ->
                        ComplicationSlotInfo(
                            ComplicationInfo(
                                providerName = "Complication $index",
                                complicationIcon = applicationContext.getDrawable(R.drawable.debug)!!.toBase64(),
                                complicationType = 1
                            ),
                            supportedTypeList[index]
                        )
                    }.map(ComplicationSlotInfo::toMap)
                )
            }
            "launchHelperActivity" -> {
                Log.d("WatchfaceConfig", "Please open the Watchface's config in order to use this function")
            }
            else -> result.notImplemented()
        }
    }

    private fun watchfaceInfoMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "retrieveWatchFaceInfo" -> {
                val watchFaceInfo = ComponentName(applicationContext, DebugComponent::class.java).let { debug ->
                    WatchFaceInfo(
                        componentName = debug,
                        name = "Debug Watchface Name"
                    ).toMap()
                }
                result.success(watchFaceInfo)
            }
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        configChannel.setMethodCallHandler(null)
        watchfaceInfoChannel.setMethodCallHandler(null)
    }
}
