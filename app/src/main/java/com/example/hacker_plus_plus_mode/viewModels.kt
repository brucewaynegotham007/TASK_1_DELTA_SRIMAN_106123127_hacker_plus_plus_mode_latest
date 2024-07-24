package com.example.hacker_plus_plus_mode

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class ThirdPageModel() : ViewModel() {
    val whoseTurn = mutableIntStateOf(0)

    val playerGrid = mutableStateOf(mutableListOf<MutableList<Int?>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { null }) }
    })

    val numberGrid = mutableStateOf(mutableListOf<MutableList<Int>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { 0 }) }
    })

    val eachPlayerVal = mutableStateOf(mutableListOf<Int>().apply {
        repeat(noOfPlayers.value) {add(0)}
    })

    val eachPlayerLosingCondition = mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    })

    val eachPlayerWinningCondition = mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    })

    val count = mutableIntStateOf(0)
}

class ThirdPageForTimedModel : ViewModel() {
    val whoseTurn = mutableIntStateOf(0)

    val playerGrid = mutableStateOf(mutableListOf<MutableList<Int?>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { null }) }
    })

    val numberGrid = mutableStateOf(mutableListOf<MutableList<Int>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { 0 }) }
    })

    val eachPlayerVal = mutableStateOf(mutableListOf<Int>().apply {
        repeat(noOfPlayers.value) {add(0)}
    })

    val eachPlayerLosingCondition = mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    })

    val eachPlayerWinningCondition = mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    })

    val eachPlayerTimer = mutableStateOf(mutableListOf<Int>().apply {
        repeat(8) {add(timerVal.value)}
    })

    val isEachPlayerTimerRunning = mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(8) {add(true)}
    })

    val count =  mutableIntStateOf(0)
}

class ThirdPageForSinglePlayer : ViewModel() {
    val isScreenBlue = mutableStateOf(false)

    val booleanGrid = mutableStateOf(mutableListOf<MutableList<Boolean?>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { null }) }
    })

    val numberGrid = mutableStateOf(mutableListOf<MutableList<Int>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { 0 }) }
    })

    val blueVal = mutableIntStateOf(0)
    val redVal = mutableIntStateOf(0)

    val blueWinningCondition = mutableStateOf(false)
    val redWinningCondition = mutableStateOf(false)

    val count = mutableIntStateOf(0)

    val countOther = mutableIntStateOf(0)
}