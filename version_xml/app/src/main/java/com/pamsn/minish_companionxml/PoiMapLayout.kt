package com.pamsn.minish_companionxml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Contenedor para el mapa detallado de una región.
 * - Su alto se calcula a partir del ancho con [imageRatio] (alto / ancho de la imagen), así la imagen no se deforma.
 * - Los hijos con una [Position] en su tag se centran en esa posición relativa (0–1) del mapa.
 *
 * Todo se resuelve en onMeasure/onLayout, sin esperar callbacks de layout.
 */
class PoiMapLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    data class Position(val x: Float, val y: Float)

    var imageRatio: Float = 1f
        set(value) {
            field = value
            requestLayout()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = (width * imageRatio).toInt()
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val width = right - left
        val height = bottom - top
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val pos = child.tag as? Position ?: continue
            val childLeft = (pos.x * width).toInt() - child.measuredWidth / 2
            val childTop = (pos.y * height).toInt() - child.measuredHeight / 2
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
        }
    }
}
