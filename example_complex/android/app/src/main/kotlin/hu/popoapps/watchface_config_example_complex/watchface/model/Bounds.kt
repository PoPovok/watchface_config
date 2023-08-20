package hu.popoapps.watchface_config_example_complex.watchface.model

import android.graphics.Rect

class Bounds(
    width: Int,
    complicationSize: Int = width / 4
) {
    val leftBounds: Rect
    val topBounds: Rect
    val rightBounds: Rect
    val bottomBounds: Rect

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

        rightBounds =
            Rect(
                width / 2 + horizontalOffset,
                verticalOffset,
                width / 2 + horizontalOffset + complicationSize,
                verticalOffset + complicationSize
            )

        bottomBounds =
            Rect(
                verticalOffset,
                width / 2 + horizontalOffset,
                verticalOffset + complicationSize,
                width / 2 + horizontalOffset + complicationSize,
            )
    }
}