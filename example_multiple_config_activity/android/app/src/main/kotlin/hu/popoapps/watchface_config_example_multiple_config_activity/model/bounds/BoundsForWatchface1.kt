package hu.popoapps.watchface_config_example_multiple_config_activity.model.bounds

import android.graphics.Rect

class BoundsForWatchface1(
    width: Int,
    complicationSize: Int = width / 4
) : Bounds {

    private val leftBounds: Rect
    private val topBounds: Rect
    private val bottomBounds: Rect

    init {
        val horizontalOffset = (width / 2 - complicationSize) / 2
        val verticalOffset = width / 2 - complicationSize / 2

        leftBounds =
            Rect(
                horizontalOffset,
                verticalOffset,
                horizontalOffset + complicationSize,
                verticalOffset + complicationSize
            )

        topBounds =
            Rect(
                width / 2 - complicationSize / 2,
                horizontalOffset,
                width / 2 + complicationSize / 2,
                horizontalOffset + complicationSize,
            )

        bottomBounds =
            Rect(
                verticalOffset,
                width / 2 + horizontalOffset,
                verticalOffset + complicationSize,
                width / 2 + horizontalOffset + complicationSize,
            )
    }

    override fun arrayOfBounds(): Array<Rect> {
        return arrayOf(leftBounds, topBounds, bottomBounds)
    }

}