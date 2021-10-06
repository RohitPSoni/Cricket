package com.example.cricket.ui.viewModel.data

abstract class MatchProgress(status: Int)

data class ScoreCard(
    val status: Int,
    var stricker: Batsman,
    var nonStricker: Batsman,
    var runs: Int,
    var toWin: Int,
    var wicketsDown: Int,
    var ballsBowled: Int,
    var runsOnThisBowl: String
): MatchProgress(status)

class MatchLost(private val status: Int = LOOSE): MatchProgress(LOOSE)
class MatchWON(private val status: Int = WIN): MatchProgress(WIN)