package uz.gita.quizzbyme.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quizzbyme.R
import uz.gita.quizzbyme.model.DataStatistics

class HolderForStatistics(view: View) : RecyclerView.ViewHolder(view) {
    private val player: TextView = view.findViewById(R.id.player_item)
    private val score: TextView = view.findViewById(R.id.score_item)

    @SuppressLint("SetTextI18n")
    fun bind(dataStatistics: DataStatistics) {
        this.player.text = dataStatistics.player
        this.score.text = "${dataStatistics.score}/10"
    }
}