package uz.gita.quizzbyme.model

import androidx.annotation.DrawableRes
import java.util.*

class Test(@DrawableRes private var imageResource: Int, private var answer: String) {
    private var listLetters: MutableList<String>


    init {
        listLetters = ArrayList()

        if (answer[0].toInt() in 65..90) {
            listLetters = getUpperLetters()
        } else if (answer[0].toInt() in 97..122) {
            listLetters = getLowerLetter()
        }

        listLetters.remove(answer)
        listLetters.shuffle()
    }

    private fun getUpperLetters(): MutableList<String> {
        val letters: MutableList<String> = ArrayList()
        for (i in 65..90) {
            letters.add((i.toChar()).toString())
        }
        return letters
    }

    private fun getLowerLetter(): MutableList<String> {
        val letters: MutableList<String> = ArrayList()
        for (i in 97..122) {
            letters.add((i.toChar()).toString())
        }
        return letters
    }

    fun shuffleOptions(): List<String?> {
        val options = listLetters.subList(0, 2)
        options.add(answer)
        options.shuffle()
        return options
    }

    fun getImage(): Int {
        return imageResource
    }

    fun getAnswer(): String {
        return answer
    }
}