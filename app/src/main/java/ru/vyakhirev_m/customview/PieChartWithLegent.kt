package ru.vyakhirev_m.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi


class PieChart @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {
    //Colors
    private val bgColor = Color.rgb(46, 51, 89)
    private val firstColor = Color.rgb(0, 190, 224)
    private val secondColor = Color.rgb(255, 225, 0)
    private val thirdColor = Color.rgb(180, 152, 207)
    private val legendColorDark = Color.rgb(0, 0, 0)
    private val legendColorLight = Color.rgb(255, 255, 255)
    //Parameters
    private var diameterPie = 0f
    lateinit var rectPie: RectF
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val padding = 25f
    private val legendHeight = 60f

    //Count percents for pie chart and add some data
    data class Client(val name: String = "Kan", var trainings: Int, var pays: Int)

    val Clients = listOf<Client>(
        Client("Иванов", 15, 15000),
        Client("Петров", 6, 19000),
        Client("Сидоренко", 17, 18000)
    )

    private fun percentCount(Clients: List<Client>, pos: Int = 0): Float {
        var totalAmount = 0f
        for (client in Clients) totalAmount += client.pays
        return (Clients[pos].pays * 100 / totalAmount) * 3.6f
    }

    var firstItemAngel = percentCount(Clients)
    var secondItemAngel = percentCount(Clients, 1)
    var thirdItemAngel = percentCount(Clients, 2)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 600
        val desiredHeight = 900
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = 0
        var height= 0

        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                width = widthSize
            }
            MeasureSpec.AT_MOST -> {
                width = Math.min(desiredWidth, widthSize)
            }
            MeasureSpec.UNSPECIFIED -> {
                width = desiredWidth
            }
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                height = heightSize
            }
            MeasureSpec.AT_MOST -> {
                height = Math.min(desiredHeight, heightSize)
            }
            MeasureSpec.UNSPECIFIED -> {
                height = desiredHeight
            }
        }
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(bgColor)


        val nextLegendRectOffset: Float = 0.1f * height
        val nextLegendTextOffset: Float = 0.55f * nextLegendRectOffset

        paint.color = firstColor
        diameterPie = Math.min(height, width).toFloat()
        rectPie = RectF(padding, padding, diameterPie - padding, diameterPie - padding)
        canvas.drawArc(rectPie, 0f, firstItemAngel, true, paint)
        paint.color = secondColor
        canvas.drawArc(rectPie, firstItemAngel, secondItemAngel, true, paint)
        paint.color = thirdColor
        canvas.drawArc(rectPie, secondItemAngel + firstItemAngel, thirdItemAngel, true, paint)
//Draw first legend
        paint.color = firstColor
        canvas.drawRoundRect(
            padding,
            diameterPie,
            width * 0.85f,
            diameterPie + 60f,
            20f,
            25f,
            paint
        )
        paint.color = legendColorLight
//        paint.color = Color.BLACK
        paint.textSize = 40F
        val rect = Rect()

        var text=Clients[0].name + " " + Clients[0].pays.toString()
        paint.getTextBounds(text, 0, text.length - 1, rect)
        canvas.drawText(
             text,
            padding+10F,
            diameterPie + legendHeight-padding  ,
            paint
        )
//Draw second legend
        paint.color = secondColor
        canvas.drawRoundRect(
            padding,
            diameterPie + nextLegendRectOffset,
            width * 0.85f,
            diameterPie + nextLegendRectOffset + 60f,
            20f,
            25f,
            paint
        )
        paint.color = legendColorDark
        paint.textSize = 40F
        canvas.drawText(
            Clients[1].name + " " + Clients[1].pays.toString(),
            padding+10F,
            diameterPie + nextLegendRectOffset + nextLegendTextOffset,
            paint
        )
//Draw third legend
        paint.color = thirdColor
        canvas.drawRoundRect(
            padding,
            diameterPie + 2f * nextLegendRectOffset,
            width * 0.85f,
            diameterPie + 2f * nextLegendRectOffset + 60f,
            20f,
            25f,
            paint
        )
        paint.color = legendColorLight
        paint.textSize = 40F
        canvas.drawText(
            Clients[2].name + " " + Clients[2].pays.toString(),
            padding+10F,
            diameterPie + 2f * nextLegendRectOffset + nextLegendTextOffset,
            paint
        )
    }
}


