package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class RegistrationUserActivity : AppCompatActivity() {


    private var code = 0

    var email = ""
    val codesArray:ArrayList<EditText> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        val nextStep:TextView = findViewById(R.id.next)
        val mail:EditText = findViewById(R.id.ed_login)

        val codes:RelativeLayout = findViewById(R.id.codes_rel)
        codesArray.add(findViewById(R.id.code1))
        codesArray.add(findViewById(R.id.code2))
        codesArray.add(findViewById(R.id.code3))
        codesArray.add(findViewById(R.id.code4))

        code = (Math.random() * (9999 - 1000 + 1) + 1000).toInt()

        nextStep.setOnClickListener {
            nextStep.visibility = View.GONE
            codes.visibility = View.VISIBLE
            email = mail.text.toString()
            RetrieveFeedTask(code).execute(email)
        }


        val res = ArrayList<Char>(4)

        for(i in 0 until codesArray.size){
            res.add('n')

            val editText = codesArray[i]

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus){
                    editText.text.clear()
                    res[i] = 'n'
                }
            }


            editText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {

                }

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    if(s.isNotEmpty()) {
                        if (s[0].isDigit()) {
                            if (i != 3) {
                                codesArray[i + 1].requestFocus()
                            }
                            res[i] = s[0]
                        }

                        if (editText.text.length == 2) {
                            editText.setText(editText.text.toString()[1].toString())
                            res[i] = editText.text.toString()[0]
                        }

                        checkCode(res, i)
                    }
                }
            })

            editText.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (i != 0 && !res[i].isDigit()) {
                        codesArray[i - 1].requestFocus()
                    }
                }
                false
            }


        }





    }

    private fun checkCode(result: ArrayList<Char>, i:Int) {


        for(c in result){
            if(!c.isDigit()){
                return
            }
        }
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        codesArray[i].clearFocus()

        val builder: StringBuilder = StringBuilder(result.size)
        for (ch in result) {
            builder.append(ch)
        }

        if(code == builder.toString().toInt()){
            val intent = Intent(this@RegistrationUserActivity, RegistrationInfoActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Неверный код", Toast.LENGTH_SHORT).show()
        }

    }


    internal class RetrieveFeedTask(
        private val code:Int
    ) :
        AsyncTask<String, Void, Int>() {
        private var exception: Exception? = null


        override fun doInBackground(vararg params: String): Int {



            // Recipient's email ID needs to be mentioned.
            val to = params[0]
            Log.d("1", to)

            // Sender's email ID needs to be mentioned

            val from = "knasibullin@itl4u.ru"

            // Get system properties
            val properties: Properties = System.getProperties()


            // Setup mail server
            properties.put("mail.smtp.host", "smtp-mail.outlook.com")
            properties.put("mail.smtp.port", "587")
            properties.put("mail.smtp.ssl.enable", "true")
            properties.put("mail.smtp.starttls.enable", "true")
            properties.put("mail.smtp.auth", "true")
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2")

            // Get the Session object.// and pass username and password
            val session: Session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication("knasibullin@itl4u.ru", "Vup97027")
                }
            })

            // Used to debug SMTP issues

            session.debug = true


            try {
                // Create a default MimeMessage object.
                val message = MimeMessage(session)

                // Set From: header field of the header.
                message.setFrom(InternetAddress(from))

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, InternetAddress(to))

                // Set Subject: header field
                message.subject = "Your code to enter ITeam account"

                // Now set the actual message
                message.setText(code.toString())
                println("sending...")

                // Send message
                Transport.send(message)
                println("Sent message successfully....")

            } catch (mex: MessagingException) {
                mex.printStackTrace()
            }
            return code
        }


        override fun onPostExecute(feed: Int) {
        }
    }






}