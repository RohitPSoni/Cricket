package com.example.cricket.ui.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cricket.ui.viewModel.data.MatchLost
import com.example.cricket.ui.viewModel.data.MatchWON
import com.example.cricket.ui.viewModel.data.ScoreCard
import com.example.cricket.usecase.GenerateRunUseCase
import com.example.cricket.usecase.GenerateRunUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainActivityViewModel () : ViewModel() {
    private val useCase: GenerateRunUseCase = GenerateRunUseCaseImpl()

    val scoreData = MutableLiveData<ScoreCard>()
    val matchResult = MutableLiveData<String>()

    init {
        scoreData.value = (useCase.getCurrentScoreCard())
    }

    fun bowlBowled() {
        when (val nextBall = useCase.onNextBall()) {
            is ScoreCard -> scoreData.postValue(nextBall)
            is MatchLost -> matchResult.value = "You have lost the match"
            is MatchWON -> matchResult.value = "You have Won the match \n Congratulations!!!!!"
        }
    }
}