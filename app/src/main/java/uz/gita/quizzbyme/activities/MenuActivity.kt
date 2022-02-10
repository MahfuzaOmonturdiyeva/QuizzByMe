package uz.gita.quizzbyme.activities

import android.annotation.SuppressLint
import android.app.Dialog
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
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import uz.gita.quizzbyme.R
import uz.gita.quizzbyme.app.LocalStorage

class MenuActivity : AppCompatActivity() {
    private var soundPool: SoundPool? = null
    private var sound: Int = 0

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        sound = soundPool?.load(this, R.raw.tab, 1)!!
    }

    @SuppressLint("ResourceAsColor")
    fun clickTestMenu(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        startActivity(Intent(this@MenuActivity, TestActivity::class.java))
        finish()
    }

    fun clickStatisticsMenu(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        startActivity(Intent(this, StatisticsActivity::class.java))
        finish()
    }

    fun clickExitMenu(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        finishAffinity()
    }
}