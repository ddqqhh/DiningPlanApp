package com.cxt.diningplan.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxt.diningplan.repository.UserRepository
import com.cxt.diningplan.extend.startActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            UserRepository.uid > 0 -> startActivity(MainActivity::class.java)
            else -> startActivity(LoginActivity::class.java)
        }
        finish()
    }
}