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

    private val padding=25f


    private val bgColor=Color.rgb(46,51,89)
    private val firstColor=Color.rgb(0,190,224)
    private val secondColor=Color.rgb(255,225,0)
    private val thirdColor=Color.rgb(180,152,207)
    private val legendColorDark=Color.rgb(0,0,0)
    private val legendColorLight=Color.rgb(255,255,255)

    var rightCorner:Float=0f
    lateinit var rectPie:RectF

//    private var maxValue : Int = 0
//    private var widthPerView = 0
//    private var heightPerValue = 0

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
        val desiredHeight = 900
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
        rectPie= RectF(padding,padding,rightCorner-padding, rightCorner-padding)
        canvas.drawArc(rectPie,0f,firstItemAngel,true,paint)
//        canvas.drawRoundRect(rectPadding,)
        paint.color=secondColor
        canvas.drawArc(rectPie,firstItemAngel,secondItemAngel,true,paint)
        paint.color=thirdColor
        canvas.drawArc(rectPie,secondItemAngel+firstItemAngel,thirdItemAngel,true,paint)
//Draw first legend
        paint.color=firstColor
        canvas.drawRoundRect(padding,601f,width*0.85f,670f,20f,25f,paint)
        paint.color=legendColorLight
        paint.textSize= 50F
        canvas.drawText(Clients[0].name+" "+Clients[0].pays.toString(),1.5f*padding,650f,paint)
//Draw second legend
        paint.color=secondColor
        canvas.drawRoundRect(padding,690f,width*0.85f,760f,20f,25f,paint)
        paint.color=legendColorDark
        paint.textSize= 50F
        canvas.drawText(Clients[1].name+" "+Clients[1].pays.toString(),1.5f*padding,740f,paint)
//Draw first legend
        paint.color=thirdColor
        canvas.drawRoundRect(padding,780f,width*0.85f,850f,20f,25f,paint)
        paint.color=legendColorLight
        paint.textSize= 50F
        canvas.drawText(Clients[2].name+" "+Clients[2].pays.toString(),1.5f*padding,830f,paint)
    }
}

//class Legend:View{
//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//
//    private val paint= Paint(Paint.ANTI_ALIAS_FLAG)
//
//    private val rectPadding=25f
//
//
//    private val bgColor=Color.rgb(46,51,89)
//    private val firstColor=Color.rgb(0,190,224)
//    private val secondColor=Color.rgb(255,225,0)
//    private val thirdColor=Color.rgb(180,152,207)
//
//
//}
data class Client(val name:String="Kan",var trenings:Int,var pays:Int)
val Clients= listOf<Client>(
    Client("Иванов",15,15000),
    Client("Петров",6,19000),
    Client("Сидорренко",17,18000))
