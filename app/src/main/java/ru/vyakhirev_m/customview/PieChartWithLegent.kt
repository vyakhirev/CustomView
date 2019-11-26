package ru.vyakhirev_m.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi


class PieChart : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//
    private val paint= Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectPadding=25f


    private val bgColor=Color.rgb(46,51,89)
    private val firstColor=Color.rgb(0,190,224)
    private val secondColor=Color.rgb(255,225,0)
    private val thirdColor=Color.rgb(180,152,207)

    var rightCorner:Float=0f
    lateinit var rectF:RectF

    private var maxValue : Int = 0
    private var widthPerView = 0
    private var heightPerValue = 0

    fun PercentCount(Clients:List<Client>,pos:Int=0):Float {
        var totalAmount=0f
        for (client in Clients) totalAmount+=client.pays
        return (Clients[pos].pays*100/totalAmount)*3.6f
    }
    var firstItemAngel=PercentCount(Clients)
    var secondItemAngel=PercentCount(Clients,1)
    var thirdItemAngel=PercentCount(Clients,2)

    //Draw rectagle vdol ili poperek

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 600
        val desiredHeight = 600
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width: Int= 0
        var height: Int= 0

        when(widthMode){
            MeasureSpec.EXACTLY->{
                width=widthSize
            }
            MeasureSpec.AT_MOST->{
                width=Math.min(desiredWidth,widthSize)
            }
            MeasureSpec.UNSPECIFIED->{
                width=desiredWidth
            }
        }

        when(heightMode){
            MeasureSpec.EXACTLY->{
                height=heightSize
            }
            MeasureSpec.AT_MOST->{
                height=Math.min(desiredHeight,heightSize)
            }
            MeasureSpec.UNSPECIFIED->{
                height=desiredHeight
            }
        }
        setMeasuredDimension(width, height)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(bgColor)
        paint.color=firstColor
        rightCorner= Math.min(height,width).toFloat()
        rectF= RectF(rectPadding,rectPadding,rightCorner-rectPadding, rightCorner-rectPadding)
        canvas.drawArc(rectF,0f,firstItemAngel,true,paint)
//        canvas.drawRoundRect(rectPadding,)
        paint.color=secondColor
        canvas.drawArc(rectF,firstItemAngel,secondItemAngel,true,paint)
        paint.color=thirdColor
        canvas.drawArc(rectF,secondItemAngel+firstItemAngel,thirdItemAngel,true,paint)
//        canvas.drawRect(0f,100f,width+200f,height+200f,paint)
    }
}

class Legend:View{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint= Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectPadding=25f


    private val bgColor=Color.rgb(46,51,89)
    private val firstColor=Color.rgb(0,190,224)
    private val secondColor=Color.rgb(255,225,0)
    private val thirdColor=Color.rgb(180,152,207)


}
data class Client(val name:String="Kan",var trenings:Int,var pays:Int)
val Clients= listOf<Client>(
    Client("Иванов",15,15000),
    Client("Петров",6,19000),
    Client("Сидоров",17,18000))
