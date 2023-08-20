package hu.popoapps.watchface_config_example.watchface.ancestors

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.wearable.complications.ComplicationData.ComplicationType
import android.support.wearable.watchface.CanvasWatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.text.TextPaint
import android.view.SurfaceHolder

abstract class CustomWatchface : CanvasWatchFaceService() {
    abstract override fun onCreateEngine(): Engine

    abstract inner class Engine : CanvasWatchFaceService.Engine() {

        private val textPaint = TextPaint().apply {
            color = Color.WHITE
            textSize = 20f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)

            setWatchFaceStyle(
                WatchFaceStyle.Builder(this@CustomWatchface)
                    .setAcceptsTapEvents(true)
                    .build()
            )
        }

        protected fun drawWatchFace(canvas: Canvas) {
            canvas.run {
                canvas.drawText("Example", width / 2f, height / 2f + 10, textPaint)
            }
        }

        protected fun drawBackground(canvas: Canvas) {
            canvas.drawColor(Color.BLACK)
        }

        override fun onTimeTick() {
            super.onTimeTick()
            invalidate()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            if (visible) {
                onVisible()
                invalidate()
            } else {
                onInvisible()
            }
        }

        open fun onVisible() {}
        open fun onInvisible() {}
    }
}