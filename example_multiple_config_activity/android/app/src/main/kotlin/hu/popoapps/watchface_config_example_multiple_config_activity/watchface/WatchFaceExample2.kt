package hu.popoapps.watchface_config_example_multiple_config_activity.watchface

import android.graphics.Color
import hu.popoapps.watchface_config.ConfigProvider
import hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds.Bounds
import hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds.BoundsForWatchface1
import hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds.BoundsForWatchface2
import hu.popoapps.watchface_config_example_multiple_config_activity.watchface.ancestors.CustomWatchfaceComplication

class WatchFaceExample2 : CustomWatchfaceComplication() {

    override fun onCreateEngine(): Engine {
        return EngineWatchFaceExample()
    }

    inner class EngineWatchFaceExample : CustomWatchfaceComplication.EngineComplication() {

        override var complicationIds = ConfigProvider.createComplicationIdsIntArray(3)
        override var componentName =
            ConfigProvider.getComponentName(applicationContext, this@WatchFaceExample2::class.java)


        // making some differences between the watchfaces

        override var theme = Color.GREEN

        override fun getBounds(width: Int): Bounds {
            return BoundsForWatchface2(width)
        }
    }
}