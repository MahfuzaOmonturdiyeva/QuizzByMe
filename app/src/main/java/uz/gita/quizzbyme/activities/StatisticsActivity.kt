package uz.gita.quizzbyme.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quizzbyme.ContractForStatistics
import uz.gita.quizzbyme.R
import uz.gita.quizzbyme.adapter.AdapterForStatistics
import uz.gita.quizzbyme.model.DataStatistics
import uz.gita.quizzbyme.presenter.PresenterImplForStatistics

class StatisticsActivity : AppCompatActivity(), ContractForStatistics.View {
    private var soundPool: SoundPool? = null
    private var sound: Int = 0
    private lateinit var presenter: ContractForStatistics.Presenter
    private val adapter = AdapterForStatistics()
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
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

        recyclerView = findViewById(R.id.container_statics)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter = PresenterImplForStatistics(this, this)
        presenter.reload()
    }

    fun clickHomeStatistics(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    fun clickTestStatistics(view: View) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        startActivity(Intent(this, TestActivity::class.java))
        finish()
    }

    override fun showList(list: List<DataStatistics>) {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
        adapter.submitList(list)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}