package com.example.spark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private var signUp : Button? = null
    private var mauth : FirebaseAuth? = null
    private var loginBtn : Button? = null
    private var naam : String? = null
    private var emailbtn : EditText? = null
    private  var passwordbtn : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signUp = findViewById(R.id.signUp)
        emailbtn = findViewById(R.id.email)
        passwordbtn = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        mauth = FirebaseAuth.getInstance()
        naam = intent.getStringExtra("NaamofUser")
        signUp?.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        loginBtn?.setOnClickListener {
            val email = emailbtn?.text.toString()
            val password = passwordbtn?.text.toString()
            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Please Enter the Credentials",Toast.LENGTH_SHORT).show()
            }
            else
            {
                login(email,password)
            }

        }
    }
    private fun login(email: String, password: String) {
        mauth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(mauth!!.currentUser!!.isEmailVerified)
                    {
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "User does not Exist Sign Up!",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }
}