package uz.gita.quizzbyme.presenter

import android.content.Context
import uz.gita.quizzbyme.Contract
import uz.gita.quizzbyme.model.ModelImpl
import uz.gita.quizzbyme.model.Test
import java.util.ArrayList

class PresenterImpl(context: Context, private var view: Contract.TestView) : Contract.Presenter {
    private var model: Contract.Model
    private var modeAnswer = false
    private var test: Test? = null

    init {
        model = ModelImpl(context)
    }

    override fun setView() {
        test = model.question()
        if (test != null) {
            view.setTest(test!!)
            setScore()
            setNumberOfQuestion()
        } else {
            model.setWinnerList()
            view.finishTests(model.nummberOfQuestion, model.score)
        }
    }

    override fun setView(index: Int, score: Int, numberOfQuestion: Int) {
        model.score = score
        model.nummberOfQuestion = numberOfQuestion
        test = model.getTest(index)
        if (test != null) {
            view.setTest(test!!)
            setScore()
            setNumberOfQuestion()
        } else view.finishTests(model.nummberOfQuestion, model.score)
    }

    override fun selectedNextButton(answer: String) {
        modeAnswer = model.isCurrentlyAnswer(answer)
        setView()
    }

    override fun indexOfOut(): ArrayList<Int> {
        return model.indexOfOut
    }

    override fun setTestIndex(indexOfOut: ArrayList<Int>) {
        model.indexOfOut.clear()
        model.indexOfOut.addAll(indexOfOut)
    }

    private fun setScore() {
        view.setScore(model.score)
    }

    private fun setNumberOfQuestion() {
        view.setNumberOfQuestions(model.nummberOfQuestion)
    }
}