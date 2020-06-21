package br.com.mobileti.cryptonews.feature.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.feature.news.ui.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
