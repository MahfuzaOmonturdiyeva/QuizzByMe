package uz.gita.quizzbyme.model

import android.content.Context
import uz.gita.quizzbyme.ContractForStatistics
import uz.gita.quizzbyme.app.LocalStorage
import java.util.*
import kotlin.collections.ArrayList

class ModelStatistics(context: Context) : ContractForStatistics.Model {
    private var sharedPreferences: LocalStorage

    init {
        sharedPreferences = LocalStorage(context)
    }

    override fun getStatistics(): List<DataStatistics> {
        val list = ArrayList<DataStatistics>()
        val set = TreeSet<Int>()
        val string1 = sharedPreferences.statistics
        if (string1 != "") {
            val string =
                sharedPreferences.statistics.substring(1, sharedPreferences.statistics.length)
                    .split("%")
            val listSort = ArrayList<DataStatistics>()

            val player = ArrayList<String>()
            val score = ArrayList<String>()
            if (string.isNotEmpty()) {
                for (item in string.indices) {
                    if (item % 2 == 0) {
                        player.add(string.get(item))

                    } else {
                        score.add(string[item])
                        set.add(string[item].toInt())
                    }
                }
            }
            for (i in 0 until player.size) {
                list.add(DataStatistics(player[i], score[i]))
            }

            val listBests = TreeSet<Int>()
            if (set.size > 5) {
                for (i in 1..5) {
                    listBests.add(set.pollLast())
                }
            } else {
                for (i in 1..set.size) {
                    listBests.add(set.pollLast())
                }
            }

            while (listBests.size != 0) {
                val int = listBests.pollLast()
                for (j in list) {
                    if (j.score == int.toString()) {
                        listSort.add(j)
                    }
                }
            }
            return if (listSort.size > 5)
                listSort.subList(0, 5)
            else listSort
        }
        return list
    }

}