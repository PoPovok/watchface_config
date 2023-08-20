package hu.popoapps.watchface_config_example_multiple_config_activity.model

import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.rendering.ComplicationDrawable

data class ComplicationDataProvider(
    var complicationDrawable: ComplicationDrawable,
    var complicationData: ComplicationData?,
)