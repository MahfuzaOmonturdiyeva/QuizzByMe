package uz.gita.quizzbyme.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.android.material.textfield.TextInputEditText
import uz.gita.quizzbyme.Contract
import uz.gita.quizzbyme.R
import uz.gita.quizzbyme.app.LocalStorage
import uz.gita.quizzbyme.model.Test
import uz.gita.quizzbyme.presenter.PresenterImpl

class TestActivity : AppCompatActivity(), Contract.TestView {
    private lateinit var player: TextInputEditText
    private lateinit var presenter: Contract.Presenter
    private lateinit var numberOfQuestions: TextView
    private var score: Int = 0
    private lateinit var questionImage: ImageView
    private lateinit var selectedButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var buttonNext: ConstraintLayout
    private lateinit var buttonCheck: ConstraintLayout
    private lateinit var selectedAnswer: String
    private lateinit var test: Test
    private val keyOutputTests = "out test"
    private val keyScore = "out score"
    private val keyNumberOfQuestion = "out number"
    private var soundPool: SoundPool? = null
    private var sound: Int = 0

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        this.numberOfQuestions = findViewById(R.id.number_progress_test_activity)
        this.questionImage = findViewById(R.id.image_question_test_activity)
        this.radioGroup = findViewById(R.id.containerAnswers)
        this.buttonNext = findViewById(R.id.btn_next)
        this.buttonCheck = findViewById(R.id.btn_check)
        this.presenter = PresenterImpl(this, this)
        player = findViewById(R.id.player)
        if(LocalStorage(this).player==" " || LocalStorage(this).player==""){
            player.setText("Player")
        }else
        player.setText(LocalStorage(this).player)

        player.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val enterImageView = findViewById<ImageView>(R.id.enter)
                enterImageView.visibility = View.VISIBLE
                enterImageView.setOnClickListener {
                    val view = currentFocus
                    if (view != null) {
                        val imm =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        player.isCursorVisible = false
                    }
                    enterImageView.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                val player1=player.text.toString().trim()
                LocalStorage(this@TestActivity).player = player1
                player.isCursorVisible = true
            }
        })

        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        sound = soundPool?.load(this, R.raw.tab, 1)!!

        presenter.setView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(keyOutputTests, presenter.indexOfOut())
        outState.putInt(keyScore, score)
        outState.putInt(keyNumberOfQuestion, numberOfQuestions.text.toString().toInt())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val list = savedInstanceState.getIntegerArrayList(keyOutputTests)
        if (list != null) {
            presenter.setTestIndex(list)
            presenter.setView(
                list[list.size - 1],
                savedInstanceState.getInt(keyScore),
                savedInstanceState.getInt(keyNumberOfQuestion)
            )
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun setNumberOfQuestions(totalQuestions: Int) {
        val progressBar: ProgressBar = findViewById(R.id.progress_horizontal_my)
        progressBar.progress = totalQuestions * 10
        if (totalQuestions == 0)
            progressBar.secondaryProgress = 0
        else progressBar.secondaryProgress = totalQuestions + 5
        numberOfQuestions.text = totalQuestions.toString()
    }

    override fun userSelected(v: View) {
        selectedButton = v as RadioButton
        selectedAnswer = selectedButton.text.toString()
        buttonNext.isClickable = true
        buttonCheck.isClickable = true
        buttonNext.isEnabled=true
        buttonCheck.isEnabled=true
    }

    override fun setScore(score: Int) {
        this.score = score
    }

    override fun setTest(test: Test) {
        this.test = test
        trueClickable()
        buttonNext.isEnabled=false
        buttonCheck.isEnabled=false
        buttonNext.isClickable = false
        buttonCheck.isClickable = false
        radioGroup.clearCheck()
        questionImage.setImageResource(test.getImage())
        val list: List<String> = test.shuffleOptions() as List<String>
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = list[i]
        }
    }

    override fun clickCheckButton(v: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)

        falseClickable()
        if (selectedAnswer == test.getAnswer()) {
            selectedButton.setBackgroundResource(R.color.teal_200)
        } else {

            selectedButton.setBackgroundResource(R.color.red)
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                if (radioButton.text == test.getAnswer())
                    radioButton.setBackgroundResource(R.color.teal_200)
            }
        }
    }


    private fun falseClickable() {
        for (item in 0 until radioGroup.childCount) {
            radioGroup[item].isClickable = false
        }
        radioGroup[0].setBackgroundResource(R.drawable.background_radio_button_1)
        radioGroup[1].setBackgroundResource(R.drawable.background_radio_button_2)
        radioGroup[2].setBackgroundResource(R.drawable.background_radio_button_3)
    }

    private fun trueClickable() {
        for (item in 0..radioGroup.childCount - 1) {
            radioGroup.get(item).isClickable = true
        }
    }

    private fun clickedNext() {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)

        falseClickable()
        presenter.selectedNextButton(selectedAnswer)
    }

    @SuppressLint("SetTextI18n")
    override fun finishTests(numberOfQuestions: Int, score: Int) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_win)

        val window = dialog.window ?: return

        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val player = dialog.findViewById<TextView>(R.id.player_win)
        val scoreOfWin = dialog.findViewById<TextView>(R.id.score_win)
        val star1 = dialog.findViewById<ImageView>(R.id.image_star1)
        val star2 = dialog.findViewById<ImageView>(R.id.image_star2)
        val star3 = dialog.findViewById<ImageView>(R.id.image_star3)

        when {
            score<5 -> {
                star1.setImageResource(R.drawable.ic_star_white)
                star2.setImageResource(R.drawable.ic_star_white)
                star3.setImageResource(R.drawable.ic_star_white)
            }
            score in 5..6 -> {
                star1.setImageResource(R.drawable.ic_star)
                star2.setImageResource(R.drawable.ic_star_white)
                star3.setImageResource(R.drawable.ic_star_white)
            }
            score in 7..8 -> {
                star1.setImageResource(R.drawable.ic_star)
                star2.setImageResource(R.drawable.ic_star)
                star3.setImageResource(R.drawable.ic_star_white)
            }
            score in 9..10 -> {
                star1.setImageResource(R.drawable.ic_star)
                star2.setImageResource(R.drawable.ic_star)
                star3.setImageResource(R.drawable.ic_star)
            }
        }

        if(LocalStorage(this).player==" " || LocalStorage(this).player==""){
            player.text = "Player"
        }
        else {
            val player1=this.player.text.toString().trim()
            player.text = player1
        }
            scoreOfWin.text = "$score/10"
        val buttonTest = dialog.findViewById<Button>(R.id.test_dialog_win)
        val buttonStatistics = dialog.findViewById<Button>(R.id.statistics_dialog_win)

        buttonTest.setOnClickListener {
            soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
            startActivity(Intent(this@TestActivity, TestActivity::class.java))
            finish()
            dialog.dismiss()
        }

        buttonStatistics.setOnClickListener {
            soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
            startActivity(Intent(this@TestActivity, StatisticsActivity::class.java))
            finish()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    fun clickedNext(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        clickedNext()
    }
}