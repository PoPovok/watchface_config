package hu.popoapps.watchface_config.model

import android.content.Context
import android.support.wearable.complications.ComplicationProviderInfo
import hu.popoapps.watchface_config.util.toBase64

data class ComplicationInfo(
    val providerName: String,
    var complicationIcon: String? = null, //base64
    val complicationType: Int
) {
    constructor(
        complicationProvider: ComplicationProviderInfo,
        applicationContext: Context
    ) : this(
        complicationProvider.providerName,
        complicationProvider.providerIcon.loadDrawable(applicationContext)!!.toBase64(),
        complicationProvider.complicationType
    )

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "providerName" to providerName,
            "complicationIcon" to complicationIcon,
            "complicationType" to complicationType
        )
    }
}
