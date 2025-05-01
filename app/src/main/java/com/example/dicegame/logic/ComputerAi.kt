package com.example.dicegame.logic

object ComputerAI {
    fun shouldReroll(dice: List<Int>): Boolean {
        return dice.count { it < 3 } >= 3  // If 3 or more dice are below 3, reroll
    }
}
