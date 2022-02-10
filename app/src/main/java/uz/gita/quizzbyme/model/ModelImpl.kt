package uz.gita.quizzbyme.model

import android.content.Context
import uz.gita.quizzbyme.Contract
import uz.gita.quizzbyme.R
import uz.gita.quizzbyme.app.LocalStorage
import java.util.*
import kotlin.collections.ArrayList

class ModelImpl(private val context: Context) : Contract.Model {
    private var localStorage: LocalStorage
    private var tests: ArrayList<Test>
    override var nummberOfQuestion: Int = 0
    override var score = 0
    private var test: Test? = null
    private var random: Random
    override lateinit var indexOfOut: ArrayList<Int>

    init {
        localStorage = LocalStorage(context)
        tests = ArrayList()
        database()
        random = Random()
        indexOfOut = ArrayList<Int>()
    }

    private fun database() {
        tests.add(Test(R.drawable.a2, "a"))
        tests.add(Test(R.drawable.b, "B"))
        tests.add(Test(R.drawable.d, "D"))
        tests.add(Test(R.drawable.q, "q"))
        tests.add(Test(R.drawable.q1, "Q"))
        tests.add(Test(R.drawable.m, "M"))
        tests.add(Test(R.drawable.m1, "m"))
        tests.add(Test(R.drawable.k, "K"))
        tests.add(Test(R.drawable.k1, "t"))
        tests.add(Test(R.drawable.z, "Z"))
        tests.add(Test(R.drawable.x, "X"))
        tests.add(Test(R.drawable.ch, "ch"))
        tests.add(Test(R.drawable.ch1, "Ch"))
        tests.add(Test(R.drawable.q2, "Q"))
        tests.add(Test(R.drawable.f, "F"))
        tests.add(Test(R.drawable.j, "J"))
        tests.add(Test(R.drawable.y, "Y"))
        tests.add(Test(R.drawable.u, "U"))
        tests.add(Test(R.drawable.o6, "o\'"))
        tests.add(Test(R.drawable.i, "I"))
        tests.add(Test(R.drawable.o, "O"))
        tests.add(Test(R.drawable.s, "S"))
    }


    override fun question(): Test? {
        var index = random.nextInt(tests.size)
        while (indexOfOut.indexOf(index) != -1) {
            index = random.nextInt(tests.size)
        }
        if (tests.size != 0 && nummberOfQuestion != 10) {
            test = tests.get(index)
            indexOfOut.add(index)
            nummberOfQuestion++
        } else test = null
        return test
    }

    override fun isCurrentlyAnswer(answer: String): Boolean {
        return if (test?.getAnswer().equals(answer)) {
            score++
            true
        } else false
    }


    override fun getTest(index: Int): Test {
        test = tests.get(index)
        return test as Test
    }

    override fun getPlayer(): String {
        return localStorage.player
    }

    override fun setWinnerList() {
        if(localStorage.player==" " || localStorage.player==""){
            localStorage.statistics += "%Player%$score"
        }else {
            val string=localStorage.player.trim()
            localStorage.statistics += "%$string%$score"
        }
    }
}