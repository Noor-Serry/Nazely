package noor.serry.nazely.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.Button
import androidx.core.content.ContextCompat.getColor
import noor.serry.nazely.R

@SuppressLint("AppCompatCustomView")
class CustomDownloadButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Button(context, attrs, defStyleAttr) {

   private val paint  = Paint()
    private var angel = 0f

    init {
        paint.color = getColor(context, R.color.colorAccent)
    }

    fun addNewAngel(angel: Float){
        this.angel = angel
        invalidate()
    }

    fun removeDraw(){
      angel = 0f
       invalidate()
    }

    @SuppressLint("DrawAllocation", "SuspiciousIndentation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
         val oval = RectF(width-200f,(height/2)-50f,width-100f,(height/2)+50f)
            canvas?.drawArc(oval,0f, angel, true, paint)
    }



    }


