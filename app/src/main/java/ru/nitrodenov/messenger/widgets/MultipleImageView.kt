package ru.nitrodenov.messenger.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import ru.nitrodenov.messenger.R

class MultipleImageView(context: Context, attributes: AttributeSet) : ImageView(context, attributes) {

    private val path = Path()
    private val rect = RectF()
    private val bitmaps = ArrayList<Bitmap>()

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            path.reset()
            rect.set(0f, 0f, width.toFloat(), height.toFloat())
            path.addOval(rect, Path.Direction.CW)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        refresh()
    }

    override fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap != null) {
            bitmaps.add(bitmap)
        } else {
            bitmaps.clear()
        }
        refresh()
    }


    private fun refresh() {
        val multipleDrawable = MultipleDrawable(bitmaps)
        setImageDrawable(multipleDrawable)
    }

    inner class MultipleDrawable(private val bitmaps: List<Bitmap>) : Drawable() {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val items = ArrayList<ImageItem>()

        override fun draw(canvas: Canvas?) {
            if (canvas != null) {
                items.forEach {
                    canvas.drawBitmap(it.bitmap, bounds, it.position, paint)
                }

                val linePaint = Paint()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    linePaint.color = context.resources.getColor(R.color.white, context.theme)
                    linePaint.strokeWidth = 5f
                }
                when {
                    bitmaps.size == 2 -> {
                        canvas.drawLine(bounds.width().toFloat() / 2, 0f, bounds.width().toFloat() / 2, bounds.height().toFloat(), linePaint)
                    }
                    bitmaps.size == 3 -> {
                        canvas.drawLine(bounds.width().toFloat() / 2, 0f, bounds.width().toFloat() / 2, bounds.height().toFloat(), linePaint)
                        canvas.drawLine(bounds.width().toFloat() / 2, bounds.height().toFloat() / 2, bounds.width().toFloat(), bounds.height().toFloat() / 2, linePaint)
                    }
                    bitmaps.size >= 4 -> {
                        canvas.drawLine(bounds.width().toFloat() / 2, 0f, bounds.width().toFloat() / 2, bounds.height().toFloat(), linePaint)
                        canvas.drawLine(0f, bounds.height().toFloat() / 2, bounds.width().toFloat(), bounds.height().toFloat() / 2, linePaint)
                    }
                }
            }
        }

        override fun onBoundsChange(bounds: Rect?) {
            super.onBoundsChange(bounds)
            initialize()
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        private fun initialize() {
            items.clear()
            when {
                bitmaps.size == 1 -> {
                    val bitmap = Bitmap.createScaledBitmap(bitmaps[0], bounds.width(), bounds.height(), false)
                    items.add(ImageItem(bitmap, Rect(0, 0, bounds.width(), bounds.height())))
                }
                bitmaps.size == 2 -> {
                    val bitmap1 = Bitmap.createScaledBitmap(bitmaps[0], bounds.width(), bounds.height() / 2, false)
                    val bitmap2 = Bitmap.createScaledBitmap(bitmaps[1], bounds.width(), bounds.height() / 2, false)
                    items.add(ImageItem(bitmap1, Rect(0, 0, bounds.width() / 2, bounds.height())))
                    items.add(ImageItem(bitmap2, Rect(bounds.width() / 2, 0, bounds.width(), bounds.height())))
                }
                bitmaps.size == 3 -> {
                    val bitmap1 = Bitmap.createScaledBitmap(bitmaps[0], bounds.width(), bounds.height() / 2, false)
                    val bitmap2 = Bitmap.createScaledBitmap(bitmaps[1], bounds.width() / 2, bounds.height() / 2, false)
                    val bitmap3 = Bitmap.createScaledBitmap(bitmaps[2], bounds.width() / 2, bounds.height() / 2, false)
                    items.add(ImageItem(bitmap1, Rect(0, 0, bounds.width() / 2, bounds.height())))
                    items.add(ImageItem(bitmap2, Rect(bounds.width() / 2, 0, bounds.width(), bounds.height() / 2)))
                    items.add(ImageItem(bitmap3, Rect(bounds.width() / 2, bounds.height() / 2, bounds.width(), bounds.height())))
                }
                bitmaps.size >= 4 -> {
                    val bitmap1 = Bitmap.createScaledBitmap(bitmaps[0], bounds.width(), bounds.height() / 2, false)
                    val bitmap2 = Bitmap.createScaledBitmap(bitmaps[1], bounds.width() / 2, bounds.height() / 2, false)
                    val bitmap3 = Bitmap.createScaledBitmap(bitmaps[2], bounds.width() / 2, bounds.height() / 2, false)
                    val bitmap4 = Bitmap.createScaledBitmap(bitmaps[3], bounds.width() / 2, bounds.height() / 2, false)
                    items.add(ImageItem(bitmap1, Rect(0, 0, bounds.width() / 2, bounds.height() / 2)))
                    items.add(ImageItem(bitmap2, Rect(bounds.width() / 2, 0, bounds.width(), bounds.height() / 2)))
                    items.add(ImageItem(bitmap3, Rect(bounds.width() / 2, bounds.height() / 2, bounds.width(), bounds.height())))
                    items.add(ImageItem(bitmap4, Rect(0, bounds.height() / 2, bounds.width() / 2, bounds.height())))
                }
            }
        }

    }

    data class ImageItem(val bitmap: Bitmap, val position: Rect)
}