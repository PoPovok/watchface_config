package hu.popoapps.watchface_config

import android.support.wearable.complications.ComplicationProviderInfo
import android.support.wearable.complications.ProviderInfoRetriever

class ProviderInfoReceiveHandler(
    val onReceive: (Int, ComplicationProviderInfo?) -> Unit
) : ProviderInfoRetriever.OnProviderInfoReceivedCallback() {
    override fun onProviderInfoReceived(
        watchFaceComplicationId: Int,
        complicationProviderInfo: ComplicationProviderInfo?
    ) {
        onReceive(watchFaceComplicationId, complicationProviderInfo)
    }
}