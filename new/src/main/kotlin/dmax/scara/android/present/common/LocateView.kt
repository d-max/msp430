package dmax.scara.android.present.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import dmax.scara.android.R

typealias LocateListener = (PointF) -> Unit

class LocateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val radius = 10f
        private const val stoke = 2f
    }

    var listener: LocateListener? = null

    var point: PointF = PointF(-1f, -1f)
        set(value) {
            field = value
            invalidate()
        }

    private val pointPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.accent_active)
    }

    private val linePaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.accent_active_50)
        strokeWidth = stoke
    }

    init {
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                point = PointF(event.x, event.y)
                listener?.invoke(point)
                invalidate()
                false
            } else {
                true
            }
        }
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        canvas.draw(point.x, point.y)
    }

    private fun Canvas.draw(x: Float, y: Float) {
        drawLine(0f, y, this@LocateView.width.toFloat(), y, linePaint)
        drawLine(x, 0f, x, this@LocateView.height.toFloat(), linePaint)
        drawCircle(x, y, radius, pointPaint)
    }
}
