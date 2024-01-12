package com.example.canvus

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.canvus.game.GameState

@Composable
fun TikTakTow(state: GameState, onTap: (x : Int, y : Int) -> Unit) {


    Canvas(
        modifier = Modifier
            .size(250.dp)
            .padding(16.dp)
            .pointerInput(true) {
                detectTapGestures {
                    val x = (3 * it.x.toInt() / size.width)
                    val y = (3 * it.y.toInt() / size.height)
                    Log.d("main", "TikTakTow: $x $y")
                    onTap(x, y)
                }
            }
    ) {

        drawBoard()
        state.field.forEachIndexed{ y , _ ->
            state.field[y].forEachIndexed{ x ,player ->
                val offset = Offset( x = y * size.width  * (1/3f )+ size.width / 6f ,
                    y = x * size.height * (1/3f )+ size.height / 6f)

                if (state.field[y][x] == 'x')
                {
                    drawCross(center = offset)
                }
                if (state.field[y][x] == 'o')
                {
                    drawCircle(offset =  offset)
                }
            }
        }

    }


}



private fun DrawScope.drawBoard() {
        //first Vertical line
        drawLine(
            color = Color.Black,
            start = Offset(
                x =  this.size.width * (1/3f) ,
                y = 0.dp.toPx()
            ),
            end = Offset(
                x = this.size.width * (1/3f),
                y = this.size.height
            ),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        //second vertical Line
        drawLine(
            color = Color.Black,
            start = Offset(
                x = this.size.width * ( 2/3f),
                y = 0.dp.toPx()
            ),
            end = Offset(
                x = this.size.width * (2/3f),
                y = this.size.height
            ) ,
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        //first horizontal
        drawLine(
            color = Color.Black ,
            start = Offset(
                x = 0.dp.toPx(),
                y =this.size.height * (1/3f)
            ),
            end = Offset(
                x = this.size.width,
                y = this.size.height * (1/3f)
            ),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )

        //second horizontal

        drawLine(
            color = Color.Black ,
            start = Offset(
                x =0.dp.toPx(),
                y = this.size.height *( 2/3f)
            ),
            end = Offset(
                x = this.size.width,
                y = this.size.height *  (2/3f)
            ),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round

        )
}


private fun DrawScope.drawCircle(offset: Offset)
{
    drawCircle(
        color = Color.Red,
        radius = (this.size.width * (1/6f))/2,
        style = Stroke(width = 5.dp.toPx() , cap = StrokeCap.Round),
        center = offset
    )
}

private fun DrawScope.drawCross(
    center: Offset ,
    size: Size = Size(width = 30.dp.toPx() , 30.dp.toPx())
){
    drawLine(
        color = Color.Blue ,
        start = Offset(
            x = center.x - size.width /2f,
            y =  center.y + size.height /2f
        ),
        end = Offset(
            x = center.x + size.width /2f,
            y =  center.y - size.height /2f
        ),
        strokeWidth = 5.dp.toPx(),
        cap = StrokeCap.Round,
    )

    drawLine(
        color = Color.Blue ,
        start = Offset(
            x = center.x - size.width /2f,
            y =  center.y - size.height /2f
        ),
        end = Offset(
            x = center.x + size.width /2f,
            y =  center.y + size.height /2f
        ),
        strokeWidth = 5.dp.toPx(),
        cap = StrokeCap.Round,
    )
}

//@Preview(showBackground = true)
//@Composable
//fun Prev(){
//    TikTakTow(state = boxState(
//        arrayOf(
//            arrayOf('x' , null , null),
//            arrayOf(null , null , null),
//            arrayOf(null , null , null),
//        )
//    ), onTap = {
//        x, y ->
//    }
//    )
//}