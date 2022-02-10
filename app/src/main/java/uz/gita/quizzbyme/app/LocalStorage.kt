package uz.gita.quizzbyme.app

import android.content.Context
import android.content.SharedPreferences

class LocalStorage(private val context: Context) {
    private var sharedPreferences: SharedPreferences
    private val keyStatics = "Key2"
    private val keyPlayer = "Player"

    init {
        sharedPreferences = context.getSharedPreferences("Lokal", Context.MODE_PRIVATE)
    }

    var statistics: String
        get() = sharedPreferences.getString(keyStatics, "").toString()
        set(value) = sharedPreferences.edit().putString(keyStatics, value).apply()

    var player: String
        get() = sharedPreferences.getString(keyPlayer, " ").toString()
        set(value) = sharedPreferences.edit().putString(keyPlayer, value).apply()
}