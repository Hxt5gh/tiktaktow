package com.example.canvus

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.canvus.game.GameState
import com.example.canvus.game.UiEvent
import com.example.canvus.game.gameViewModel
import com.example.canvus.ui.theme.CanvusTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvusTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    game()
                }
            }
        }
    }
}

@Composable
fun game() {
     val TAG = "MainActivity"
    val viewModel: gameViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    var showTurn by remember { mutableStateOf(true)}



    if (state.value.winningPlayer == null && state.value.isBoardFull == true)
    {
        showTurn = false
    }
    else if (state.value.winningPlayer != null){
        showTurn = false
    }
    else{
        showTurn = true
    }


    Column(
        modifier = Modifier.fillMaxSize() ,
//        verticalArrangement = Arrangement.Top ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(50.dp))
        if (showTurn)
        {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
//                .padding(32.dp) ,
                text = state.value.playerAtTurn.toString() +" is Playing" ,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold ,
                textAlign = TextAlign.Center)
        }
        if (!showTurn)
        {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
//                .padding(32.dp) ,
                text = "Loading..." ,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold ,
                textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.size(150.dp))



                TikTakTow(state = state.value, onTap ={
                        x, y ->
                    viewModel.onEvent(UiEvent.OnTap(x,y,state.value.playerAtTurn))
                } )




        Spacer(modifier = Modifier.size(50.dp))


        if (!showTurn)
        {
            Text(
                text = when(state.value.winningPlayer){
                     'x' -> "X is Winner"
                      'o' ->"O is winner"
                      else ->"its a draw"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

          
            viewModel.onEvent(UiEvent.Restart)
        }
    }

}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun prev() {
    game()
}


































































@Composable
fun Instagram(name: String, modifier: Modifier = Modifier) {

    val color = listOf(Color.Blue , Color.Red , Color.Green)

        Canvas(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
                .padding(16.dp)){

            drawRoundRect(
                brush = Brush.linearGradient(color) ,
                cornerRadius = CornerRadius(60f , 60f),
                style = Stroke(width = 15f , cap = StrokeCap.Round )
            )

            drawCircle(
                brush = Brush.linearGradient(color),
                radius = 45f,
                style = Stroke(width = 15f , cap = StrokeCap.Round)
            )

            drawCircle(
                brush = Brush.linearGradient(color),
                radius = 13f,
                center = Offset(this.size.width * .8f , this.size.width * .2f)
            )



        }

}



@OptIn(ExperimentalTextApi::class)
@Composable
fun Facebook( modifier: Modifier = Modifier) {

    val color = listOf(Color.Blue,Color.Blue)
    val textMeasurer = rememberTextMeasurer()

    val asser = LocalContext.current.assets
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray)
            .padding(16.dp)){

        drawRoundRect(
            brush = Brush.linearGradient(color),
            cornerRadius = CornerRadius(10f , 10f)
        )

        drawText(textMeasurer = textMeasurer , "F" ,
            size = size,
            style = TextStyle(fontSize = 200.sp))
       




    }

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Arc( modifier: Modifier = Modifier) {


    val asser = LocalContext.current.assets
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray)
            .padding(16.dp)){

        drawArc(
            color = Color.Red,
            startAngle = 0f,  // 3o clock
            sweepAngle =180f, // angel after c 0 clock
            useCenter = true
        )


    }

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Path( modifier: Modifier = Modifier) {


    val asser = LocalContext.current.assets
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray)
            .padding(16.dp)){




    }

}
//
//@Preview(showBackground = true )
//@Composable
//fun GreetingPreview() {
//
//
//    Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
//       // Instagram("Android")
//       // Facebook()
//        Arc()
//    }
//
//}