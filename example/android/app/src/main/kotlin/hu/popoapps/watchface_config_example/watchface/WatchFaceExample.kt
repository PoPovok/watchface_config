package hu.popoapps.watchface_config_example.watchface

import android.content.res.Resources
import android.util.Log
import hu.popoapps.watchface_config.ConfigProvider
import hu.popoapps.watchface_config_example.watchface.ancestors.CustomWatchfaceComplication

class WatchFaceExample : CustomWatchfaceComplication() {

    override fun onCreateEngine(): Engine {
        return EngineWatchFaceExample()
    }

    /**
     * Shows what values must be used in order to make the plugin working.
     */
    inner class EngineWatchFaceExample : CustomWatchfaceComplication.EngineComplication() {

        override var complicationIds = requireComplicationIds()
        override var componentName =
            ConfigProvider.getComponentName(applicationContext, this@WatchFaceExample::class.java)

        /**
         * Alternatively you can use ConfigProvider.createComplicationIdsIntArray(size: Int)
         * if there is only one watchface available and its complicationIds' size is not changing dynamically.
         */
        private fun requireComplicationIds(): IntArray {
            return ConfigProvider.getComplicationIds(
                applicationContext = applicationContext,
                javaClass = this@WatchFaceExample::class.java,
                defaultSize = 3
            )
        }
    }
}