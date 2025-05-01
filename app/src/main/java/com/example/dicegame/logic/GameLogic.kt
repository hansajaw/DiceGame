package com.example.dicegame.logic

object GameLogic {
    fun calculateScore(dice: List<Int>): Int {
        return dice.sum()
    }
}
