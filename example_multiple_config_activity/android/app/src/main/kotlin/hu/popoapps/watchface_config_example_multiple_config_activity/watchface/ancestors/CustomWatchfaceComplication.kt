package hu.popoapps.watchface_config_example_multiple_config_activity.watchface.ancestors

import android.app.PendingIntent
import android.content.ComponentName
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationHelperActivity
import android.support.wearable.complications.rendering.ComplicationDrawable
import android.support.wearable.watchface.WatchFaceService
import android.util.SparseArray
import android.view.SurfaceHolder
import hu.popoapps.watchface_config_example_multiple_config_activity.R
import hu.popoapps.watchface_config_example_multiple_config_activity.model.ComplicationDataProvider
import hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds.Bounds
import hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds.BoundsForWatchface1

abstract class CustomWatchfaceComplication : CustomWatchface() {

    abstract inner class EngineComplication : Engine() {

        protected abstract var complicationIds: IntArray
        protected abstract var componentName: ComponentName

        protected lateinit var complications: List<ComplicationDataProvider>

        private fun initializeComplications() {
            complications = complicationIds.map {
                ComplicationDataProvider(
                    complicationDrawable = ComplicationDrawable(applicationContext),
                    complicationData = null,
                )
            }
            setActiveComplications(*complicationIds)
        }

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)
            initializeComplications()
        }

        override fun onDraw(canvas: Canvas, bounds: Rect) {
            super.onDraw(canvas, bounds)
            drawBackground(canvas)
            drawComplications(canvas)
            drawWatchFace(canvas)
        }

        override fun onComplicationDataUpdate(complicationId: Int, complicationData: ComplicationData?) {
            complications.getOrNull(complicationId)?.apply {
                complicationDrawable.setComplicationData(complicationData)
                this.complicationData = complicationData
            }
            invalidate()
        }

        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            when (tapType) {
                WatchFaceService.TAP_TYPE_TAP ->
                    getTappedComplicationId(x, y)?.let { tappedComplicationId ->
                        onComplicationTap(tappedComplicationId)
                    }
            }
            invalidate()
        }

        protected fun onComplicationTap(complicationId: Int) {
            complications[complicationId].complicationData?.let { complicationData ->
                complicationData.tapAction?.run {
                    runCatching { send() }
                } ?: complicationData
                    .takeIf { it.type == ComplicationData.TYPE_NO_PERMISSION }
                    ?.also { startPermissionRequestHelperActivity() }
            }
        }

        private fun startPermissionRequestHelperActivity() {
            startActivity(
                ComplicationHelperActivity.createPermissionRequestHelperIntent(applicationContext, componentName)
            )
        }

        protected fun getTappedComplicationId(x: Int, y: Int): Int? {
            val currentTimeMillis = System.currentTimeMillis()
            return complications.firstNotNullOfOrNull { complication ->
                complication.complicationData?.run {
                    complication.complicationDrawable
                        .takeIf { isActive(currentTimeMillis) && (type !in UNCONFIGURED_COMPLICATION_DATA_TYPES) }
                        ?.takeIf { it.bounds.contains(x, y) }
                        ?.run { complications.indexOf(complication) }
                }
            }
        }

        private fun drawComplications(canvas: Canvas) {
            complications.forEach {
                it.complicationDrawable.draw(canvas, System.currentTimeMillis())
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            complicationSurfaceChanged(width, height)
        }

        open fun getBounds(width: Int): Bounds {
            return BoundsForWatchface1(width)
        }

        protected fun complicationSurfaceChanged(
            width: Int,
            height: Int // Watch with chin is not round
        ) {
            val complicationBounds = getBounds(width).arrayOfBounds()

            complications.zip(complicationBounds).forEach {
                it.first.complicationDrawable.apply {
                    bounds = it.second
                    setRangedValuePrimaryColorActive(theme)
                    setRangedValueSecondaryColorActive(Color.BLACK)
                    setRangedValueRingWidthActive(5)
                    setBorderColorActive(Color.GRAY)
                    setBorderStyleActive(1)
                    setBorderWidthActive(3)
                    setIconColorActive(theme)
                    setTextSizeActive(22)
                    setTextSizeAmbient(22)
                    setTitleSizeActive(18)
                    setTextSizeAmbient(18)
                }
            }
        }
    }

    companion object {
        private val UNCONFIGURED_COMPLICATION_DATA_TYPES =
            arrayOf(ComplicationData.TYPE_NOT_CONFIGURED, ComplicationData.TYPE_EMPTY)
    }
}