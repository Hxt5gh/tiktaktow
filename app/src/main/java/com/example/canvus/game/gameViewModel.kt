package com.example.canvus.game


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class gameViewModel @Inject constructor(): ViewModel() {


//      private val _state = MutableStateFlow(GameState())
//      val state :StateFlow<GameState> = _state


    var count = mutableIntStateOf(5)

    private val _state = MutableStateFlow(GameState())
    val state = _state

    fun onEvent(event : UiEvent)
    {
        when(event)
        {
            is UiEvent.OnTap ->{
                val next = if (event.ox == 'x') 'o' else 'x'

                _state.value = _state.value.copy(
                    field = _state.value.field.mapIndexed { rowIndex, row ->
                        row.mapIndexed { columnIndex, element ->
                            if (rowIndex == event.x && columnIndex == event.y) {
                                event.ox
                            } else {
                                element
                            }
                        }.toTypedArray()
                    }.toTypedArray(),
                    playerAtTurn = next,

                )
                if (isMatrixFull())
                {

                    //bord full draw
                    _state.value = _state.value.copy(
                        isBoardFull = true,
                    )
                }
                val winner = getWinningPlayer()
                if (winner != null)
                {
                    _state.value = _state.value.copy(
                        winningPlayer = winner
                    )
                }





            }
            is UiEvent.WhoWon ->{}
            is UiEvent.WhoTurnNext ->{}
            is UiEvent.Restart->{
                // Restart
                viewModelScope.launch {

                    // Delay for an additional 5 seconds after the loop
                    delay(3000)
                    // Reset the game state after the delay
                    _state.value = GameState()
                }

            }
        }
    }

    fun isMatrixFull(): Boolean {
        return state.value.field.all { row -> row.all { it != null } }
    }

    private fun getWinningPlayer(): Char? {
        val field = state.value.field
        return if (field[0][0] != null && field[0][0] == field[0][1] && field[0][1] == field[0][2]) {
            field[0][0]
        } else if (field[1][0] != null && field[1][0] == field[1][1] && field[1][1] == field[1][2]) {
            field[1][0]
        } else if (field[2][0] != null && field[2][0] == field[2][1] && field[2][1] == field[2][2]) {
            field[2][0]
        } else if (field[0][0] != null && field[0][0] == field[1][0] && field[1][0] == field[2][0]) {
            field[0][0]
        } else if (field[0][1] != null && field[0][1] == field[1][1] && field[1][1] == field[2][1]) {
            field[0][1]
        } else if (field[0][2] != null && field[0][2] == field[1][2] && field[1][2] == field[2][2]) {
            field[0][2]
        } else if (field[0][0] != null && field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
            field[0][0]
        } else if (field[0][2] != null && field[0][2] == field[1][1] && field[1][1] == field[2][0]) {
            field[0][2]
        } else null
    }


}

sealed class UiEvent{
    data class OnTap(val x : Int , val  y : Int , val ox : Char) : UiEvent()
    data class WhoWon(val winner : String) : UiEvent()
    data class WhoTurnNext(val nextPlayer : Char) :UiEvent()
    object Restart : UiEvent()
}