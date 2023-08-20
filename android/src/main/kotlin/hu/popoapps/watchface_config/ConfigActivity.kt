package hu.popoapps.watchface_config

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderChooserIntent
import android.support.wearable.complications.ProviderInfoRetriever
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import hu.popoapps.watchface_config.databinding.ActivityConfigBinding
import hu.popoapps.watchface_config.debug.DebugComponent
import hu.popoapps.watchface_config.model.ComplicationInfo
import hu.popoapps.watchface_config.model.ComplicationSigning
import hu.popoapps.watchface_config.model.ComplicationSlotInfo
import hu.popoapps.watchface_config.model.WatchFaceInfo
import hu.popoapps.watchface_config.util.ComplicationInfoReceiver
import hu.popoapps.wearable_flutter_fragment_application.activity.WearableCoreFragmentActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import java.util.concurrent.Executors

open class ConfigActivity : WearableCoreFragmentActivity() {
    private lateinit var binding: ActivityConfigBinding
    private lateinit var providerInfoRetriever: ProviderInfoRetriever

    private var configMethodChannel: MethodChannel? = null
    private var watchfaceInfoMethodChannel: MethodChannel? = null
    private lateinit var watchFaceComponentName: ComponentName
    private lateinit var complicationIdNumberForClass: String

    companion object {
        private const val COMPONENT_NAME_EXTRA = "android.support.wearable.watchface.extra.WATCH_FACE_COMPONENT"

        private const val CONFIG_CHANNEL = "Config"
        private const val WATCHFACE_INFO_CHANNEL = "WatchFaceInfo"

        private const val COMPLICATION_CONFIG_REQUEST_NUMBER = 1
        private const val VALUE_TO_GENERATE_REQUEST_NUMBER = 1000

        fun generateRequestCode(requestNumber: Int, requestId: Int): Int {
            return requestNumber * VALUE_TO_GENERATE_REQUEST_NUMBER + requestId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        triggerExtras {
            watchFaceComponentName = (intent.extras?.get(COMPONENT_NAME_EXTRA)).let {
                it as? ComponentName ?: onNoWatchFaceBound(it)
            }
        }
        val className = watchFaceComponentName.flattenToString()
        complicationIdNumberForClass = ConfigProvider.COMPLICATION_ID_NUMBER + className

        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDismissableApplicationConfigurator(binding.fragmentSwipeable)

        initConfigMethodChannel()
        initWatchFaceInfoMethodChannel()

        providerInfoRetriever = ProviderInfoRetriever(applicationContext, Executors.newCachedThreadPool()).apply {
            init()
        }
    }

    open fun getWatchFaceName(): String {
        return watchFaceComponentName.className
    }

    protected fun getCustomMethod(watchfaceToMethod: Map<Class<*>, String>): String {
        return getCustomName(watchfaceToMethod)
    }

    protected fun getCustomWatchFaceName(watchfaceToName: Map<Class<*>, String>): String {
        return getCustomName(watchfaceToName)
    }

    private fun getCustomName(watchfaceToMethod: Map<Class<*>, String>): String {
        val className = watchFaceComponentName.flattenToString()
        return watchfaceToMethod.entries.first {
            ConfigProvider.getComponentNameString(this, it.key) == className
        }.value
    }

    open fun onNoWatchFaceBound(componentNameExtra: Any?): ComponentName {
        return ComponentName(applicationContext, DebugComponent::class.java)
    }

    override fun onDestroy() {
        providerInfoRetriever.release()
        super.onDestroy()
    }

    private fun initConfigMethodChannel() {
        configMethodChannel = MethodChannel(
            dismissibleApplicationConfigurator.flutterComponentsProvider.flutterEngine.dartExecutor.binaryMessenger,
            CONFIG_CHANNEL
        ).apply {
            setMethodCallHandler { call, _result ->
                when (call.method) {
                    "retrieveInitialComplicationsData" -> {
                        retrieveInitialComplicationsDataHandler(call.argument<List<List<Int>>>("supportedTypeList")!!)
                    }
                    "launchHelperActivity" -> {
                        val ordinalNumber = call.argument<Int>("ordinalNumber")!!
                        val supportedTypes = call.argument<List<Int>>("supportedTypes")!!
                        launchComplicationHelperActivity(ComplicationSigning(ordinalNumber, supportedTypes))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initWatchFaceInfoMethodChannel() {
        watchfaceInfoMethodChannel = MethodChannel(
            dismissibleApplicationConfigurator.flutterComponentsProvider.flutterEngine.dartExecutor.binaryMessenger,
            WATCHFACE_INFO_CHANNEL
        ).apply {
            setMethodCallHandler { call, result ->
                when (call.method) {
                    "retrieveWatchFaceInfo" -> {
                        val watchFaceInfo = WatchFaceInfo(
                            componentName = watchFaceComponentName,
                            name = getWatchFaceName()
                        ).toMap()

                        result.success(watchFaceInfo)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun retrieveInitialComplicationsDataHandler(supportedTypeList: List<List<Int>>) {
        PreferenceManager
            .getDefaultSharedPreferences(applicationContext)
            .edit(commit = true) {
                putInt(complicationIdNumberForClass, supportedTypeList.size)
            }

        val complicationList = supportedTypeList.map { supportedTypes -> ComplicationSlotInfo(null, supportedTypes) }
        val complicationInfoReceiver = ComplicationInfoReceiver(complicationList) {
            configMethodChannel!!.invokeMethod(
                "retrieveInitialComplicationsData",
                complicationList.map(ComplicationSlotInfo::toMap)
            )
        }

        retrieveInitialComplicationsData(complicationList, complicationInfoReceiver)
    }

    fun retrieveInitialComplicationsData(
        complicationList: List<ComplicationSlotInfo>,
        complicationInfoReceiver: ComplicationInfoReceiver
    ) {
        ProviderInfoReceiveHandler { watchFaceComplicationId, complicationProviderInfo ->
            complicationProviderInfo?.let {
                complicationList[watchFaceComplicationId].complicationInfo =
                    ComplicationInfo(it, applicationContext)
            }
            complicationInfoReceiver.infoReceived()
        }.let {
            val complicationIds = List(complicationList.size) { index -> index }.toIntArray()
            providerInfoRetriever.retrieveProviderInfo(it, watchFaceComponentName, *complicationIds)
        }
    }

    private fun launchComplicationHelperActivity(complicationSigning: ComplicationSigning) {
        startActivityForResult(
            ComplicationHelperActivity.createProviderChooserHelperIntent(
                applicationContext,
                watchFaceComponentName,
                complicationSigning.ordinalNumber,
                *complicationSigning.supportedTypes.toIntArray()
            ),
            generateRequestCode(COMPLICATION_CONFIG_REQUEST_NUMBER, complicationSigning.ordinalNumber)
        )
    }

    /**
     * DO NOT REMOVE
     * Without triggering the extras, watchface component name cannot be determined
     */
    private fun triggerExtras(getExtra: () -> Unit) {
        intent.hasExtra("trigger").let { _triggermMap ->
            getExtra()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == FlutterActivity.RESULT_OK) {
            val requestId = requestCode % VALUE_TO_GENERATE_REQUEST_NUMBER

            when (requestCode / VALUE_TO_GENERATE_REQUEST_NUMBER) {
                COMPLICATION_CONFIG_REQUEST_NUMBER -> data?.run {
                    getParcelableExtra<ComplicationProviderInfo>(ProviderChooserIntent.EXTRA_PROVIDER_INFO).let { complicationProviderInfo ->
                        val indexForRequest = mapOf("index" to requestId)

                        configMethodChannel!!.invokeMethod(
                            "updateComplicationsData",
                            indexForRequest
                                .takeIf { complicationProviderInfo != null }
                                ?.plus(ComplicationInfo(complicationProviderInfo!!, applicationContext).toMap())
                                ?: indexForRequest
                        )
                    }
                }
            }
        }
    }
}