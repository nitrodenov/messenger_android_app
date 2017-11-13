package ru.nitrodenov.messenger.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView

class CircleImageView(context: Context, attributes: AttributeSet) : ImageView(context, attributes) {

    private val path = Path()
    private val rect = RectF()

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            path.reset()
            rect.set(0f, 0f, width.toFloat(), height.toFloat())
            path.addOval(rect, Path.Direction.CW)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }
}