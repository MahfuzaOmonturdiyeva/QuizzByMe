package uz.gita.quizzbyme

import android.view.View
import uz.gita.quizzbyme.model.Test
import java.util.ArrayList

interface Contract {
    interface Model {
        var  nummberOfQuestion:Int
        var score:Int
        var indexOfOut: ArrayList<Int>
        fun question(): Test?
        fun isCurrentlyAnswer(answer: String): Boolean
        fun getTest(index: Int): Test
        fun getPlayer():String
        fun setWinnerList()
    }

    interface Presenter {
        fun setView()
        fun selectedNextButton(answer: String)
        fun indexOfOut(): ArrayList<Int>
        fun setTestIndex(indexOfOut: ArrayList<Int>)
        fun setView(index: Int, score: Int, numberOfQuestion: Int)
    }

    interface TestView {
        fun setNumberOfQuestions(totalQuestions: Int)
        fun userSelected(v: View)
        fun setScore(score: Int)
        fun setTest(test: Test)
        fun clickCheckButton(v: View)
        fun finishTests(numberOfQuestions: Int, score: Int)
    }
}