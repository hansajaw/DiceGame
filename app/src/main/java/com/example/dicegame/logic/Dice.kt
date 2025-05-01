package com.example.dicegame.logic

fun rollDice(): List<Int> {
    return List(5) { (1..6).random() }
}
