package com.example.cricket.usecase

import com.example.cricket.ui.viewModel.data.Batsman
import com.example.cricket.ui.viewModel.data.MatchLost
import com.example.cricket.ui.viewModel.data.MatchProgress
import com.example.cricket.ui.viewModel.data.MatchWON
import com.example.cricket.ui.viewModel.data.PROGRESS
import com.example.cricket.ui.viewModel.data.ScoreCard
import com.example.cricket.ui.viewModel.data.TOTAL_BALLS
import kotlin.random.Random

interface GenerateRunUseCase {

    fun onNextBall(): MatchProgress

    fun getCurrentScoreCard(): ScoreCard
}

class GenerateRunUseCaseImpl : GenerateRunUseCase {

    private var scoreCard: ScoreCard = ScoreCard(
        status = PROGRESS,
        stricker = Batsman("Kirat Boli", 30, listOf(5, 20, 35, 10, 15, 1, 9, 5)),
        nonStricker = Batsman("N. S Nodhi", 20, listOf(10, 40, 20, 5, 10, 1, 4, 10)),
        runs = 40,
        toWin = 40,
        wicketsDown = 7,
        ballsBowled = 96,
        runsOnThisBowl = ""
    )

    override fun getCurrentScoreCard(): ScoreCard = scoreCard

    override fun onNextBall(): MatchProgress {
        val totalBalls = scoreCard.ballsBowled + 1
        val randomNo = Random.nextInt(0, 100)
        val runsThisBall = generateRamdomNo(randomNo, scoreCard.stricker.probability)

        return if (totalBalls < TOTAL_BALLS) {
            when (runsThisBall) {

                7 -> { // Wicket
                    val wicket = scoreCard.wicketsDown + 1
                    if (wicket < 10) {
                        val strickerBatsman = nextBatsman[0]
                        nextBatsman.removeAt(0)
                        scoreCard.apply {
                            stricker = strickerBatsman
                            wicketsDown = wicket
                            ballsBowled = totalBalls
                            runsOnThisBowl = "W"
                        }
                    } else {
                        MatchLost()
                    }
                }
                0, 2, 4, 6 -> {
                    scoreCard.toWin = scoreCard.toWin - runsThisBall
                    scoreCard.apply {
                        scoreCard.stricker.runsScored += runsThisBall
                        ballsBowled = totalBalls
                        runsOnThisBowl = runsThisBall.toString()
                        runs += runsThisBall
                    }
                    if (hasWon()) {
                        return MatchWON()
                    } else {
                        scoreCard
                    }
                }
                else -> {
                    scoreCard.toWin = scoreCard.toWin - runsThisBall
                    val nonStrickerBatsman = scoreCard.stricker
                    scoreCard.apply {
                        scoreCard.stricker.runsScored += runsThisBall
                        ballsBowled = totalBalls
                        runsOnThisBowl = runsThisBall.toString()
                        stricker = this.nonStricker
                        nonStricker = nonStrickerBatsman
                        runs += runsThisBall
                    }
                    if (hasWon()) {
                        return MatchWON()
                    } else {
                        scoreCard
                    }
                }
            }
        } else {
            MatchLost()
        }
    }

    private fun hasWon(): Boolean =
        scoreCard.toWin <= 0

    private fun generateRamdomNo(runsThisBall: Int, probability: List<Int>): Int {
        var randomNo = runsThisBall
        probability.forEachIndexed { index, it ->
            randomNo -= it
            if (randomNo <= 0) {
                randomNo = it
                return index
            }
        }
        return 0
    }
}

val nextBatsman = mutableListOf(
    Batsman(
        "R Rumrah",
        0,
        listOf(20, 30, 15, 5, 5, 1, 4)
    ), Batsman(
        "Shashi Henra",
        0,
        listOf(30, 25, 5, 0, 5, 1, 4, 30)
    )
)
