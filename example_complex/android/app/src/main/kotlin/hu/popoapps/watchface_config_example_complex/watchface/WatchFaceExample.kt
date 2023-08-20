package hu.popoapps.watchface_config_example_complex.watchface

import android.content.res.Resources
import android.support.wearable.complications.rendering.ComplicationDrawable
import android.util.Log
import hu.popoapps.watchface_config.ConfigProvider
import hu.popoapps.watchface_config_example_complex.watchface.ancestors.CustomWatchfaceComplication
import hu.popoapps.watchface_config_example_complex.watchface.model.ComplicationDataProvider

class WatchFaceExample : CustomWatchfaceComplication() {

    override fun onCreateEngine(): Engine {
        return EngineWatchFaceExample()
    }

    inner class EngineWatchFaceExample : CustomWatchfaceComplication.EngineComplication() {

        private var _complicationIds: IntArray? = null
        override var complicationIds: IntArray
            get() = _complicationIds ?: requireComplicationIds()
            set(value) {
                _complicationIds = value
            }

        private fun requireComplicationIds(): IntArray {
            return ConfigProvider.getComplicationIds(applicationContext, this@WatchFaceExample::class.java)
        }

        override var componentName =
            ConfigProvider.getComponentName(applicationContext, this@WatchFaceExample::class.java)

        override fun onVisible() {
            val newComplicationIds = requireComplicationIds()
            if (!newComplicationIds.contentEquals(_complicationIds)) {
                complicationIds = newComplicationIds

                checkOnComplicationNumberDecrease()
                checkOnComplicationNumberIncrease()
                Resources.getSystem().displayMetrics.run {
                    complicationSurfaceChanged(widthPixels, heightPixels)
                }
            }
            super.onVisible()
        }

        private fun checkOnComplicationNumberDecrease() {
            if (complicationIds.size < complications.size) {
                complications = complications.subList(0, complicationIds.size)
                setActiveComplications(*complicationIds)
            }
        }

        private fun checkOnComplicationNumberIncrease() {
            val oldSize = complications.size
            if (complicationIds.size > oldSize) {
                val increase = complicationIds.size - oldSize
                val newComplications = (0 until increase).map {
                    ComplicationDataProvider(
                        complicationDrawable = ComplicationDrawable(applicationContext),
                        complicationData = null
                    )
                }
                complications = complications.plus(newComplications)
                setActiveComplications(*complicationIds)
            }
        }
    }
}