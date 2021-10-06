package com.example.cricket.binder

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cricket.ui.viewModel.data.BALLS_IN_OVER
import com.example.cricket.ui.viewModel.data.Batsman

@BindingAdapter("overs")
fun showTotalOvers(view: TextView, totalBalls: Int){
    val overs = totalBalls / BALLS_IN_OVER
    val balls = totalBalls % BALLS_IN_OVER
    view.text = "${overs}.${balls}"
}

@BindingAdapter("batsman")
fun showBatsmalInfo(view: TextView, batsman: Batsman){
    view.text = "${batsman.name} (${batsman.runsScored})"
}