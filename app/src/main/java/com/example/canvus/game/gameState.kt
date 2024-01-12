package com.example.canvus.game

data  class GameState(
    var playerAtTurn : Char = 'x',
    var field : Array<Array<Char?>> = emptyField(),
    var winningPlayer : Char? = null,
    var isBoardFull : Boolean = false,

    ){

    companion object
    {
        fun emptyField(): Array<Array<Char?>> {

            return arrayOf(
                arrayOf(null , null ,null),
                arrayOf(null , null ,null),
                arrayOf(null , null ,null)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (playerAtTurn != other.playerAtTurn) return false
        if (!field.contentDeepEquals(other.field)) return false
        if (winningPlayer != other.winningPlayer) return false
        if (isBoardFull != other.isBoardFull) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerAtTurn?.hashCode() ?: 0
        result = 31 * result + field.contentDeepHashCode()
        result = 31 * result + (winningPlayer?.hashCode() ?: 0)
        result = 31 * result + isBoardFull.hashCode()
        return result
    }

}