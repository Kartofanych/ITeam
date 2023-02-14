package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.math.log

class LoginUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        val login:TextView = findViewById(R.id.login)
        val noAcc:TextView = findViewById(R.id.no_account)

        login.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        noAcc.setOnClickListener {
            startActivity(Intent(this, RegistrationUserActivity::class.java))
        }
    }
}