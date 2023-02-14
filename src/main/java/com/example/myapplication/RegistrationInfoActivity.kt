package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationInfoActivity : AppCompatActivity() {


    private val db = Firebase.database("https://iteam-e45a8-default-rtdb.europe-west1.firebasedatabase.app")
    private val usersRef: DatabaseReference = db.getReference("Users").child("Accounts")

    private val auth = FirebaseAuth.getInstance()

    var email:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_info)

        val extras = intent.extras
        email = extras!!.getString("email")!!

        init()



    }

    private fun init(){
        val handle:EditText = findViewById(R.id.ed_handle)
        val pass1:EditText = findViewById(R.id.ed_pass1)
        val pass2:EditText = findViewById(R.id.ed_pass2)

        val handleWrong:TextView = findViewById(R.id.handle_wrong)
        val pass1Wrong:TextView = findViewById(R.id.password1_wrong)
        val pass2Wrong:TextView = findViewById(R.id.password2_wrong)

        val register:TextView = findViewById(R.id.register)

        register.setOnClickListener {
            when(ready(handle.text.toString(), pass1.text.toString(), pass2.text.toString())){
                1 -> {
                    handleWrong.visibility = View.VISIBLE
                }
                2 -> {
                    pass1Wrong.visibility = View.VISIBLE
                }
                3 -> {
                    pass2Wrong.visibility = View.VISIBLE
                }
                4 -> {
                    registration(handle.text.toString(), pass1.text.toString())
                    register.isClickable = false
                    register.text = "Секундочку"
                }
            }
        }


        handle.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(handleWrong.isVisible){
                    handleWrong.visibility = View.INVISIBLE
                }
            }

        })


        pass1.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(pass1Wrong.isVisible){
                    pass1Wrong.visibility = View.INVISIBLE
                }
            }

        })

        pass2.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(pass2Wrong.isVisible){
                    pass2Wrong.visibility = View.INVISIBLE
                }
            }

        })



    }

    private fun ready(handle: String, pass1: String, pass2: String): Int {
        if(handle.length < 3){
            return 1
        }
        if(pass1.length < 6){
            return 2
        }
        if(pass1 != pass2){
            return 3
        }
        return 4
    }


    private fun registration(handle: String, pass1: String) {
        auth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener {
            if(it.isSuccessful) {
                val user = User(auth.uid.toString(), handle,"name", "surname", email, "null", pass1)
                usersRef.push().setValue(user)
                startActivity(Intent(this@RegistrationInfoActivity, NavigationActivity::class.java))
            }else{
                Toast.makeText(this, "Непредвиденная ошибка, попробуйте позже", Toast.LENGTH_SHORT).show()
            }
        }
    }


}