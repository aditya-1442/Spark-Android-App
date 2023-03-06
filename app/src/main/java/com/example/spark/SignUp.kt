package com.example.spark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private var myname : EditText? = null
    private var signUp : Button? = null
    private var mauth : FirebaseAuth? = null
    private var nameRef : String? = null
    private var emailbtn : EditText? = null
    private  var passwordbtn : EditText? = null
    private var uid : String ? = null
    private var db : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUp = findViewById(R.id.signUp)
        myname = findViewById(R.id.edtname)
        nameRef = myname?.text.toString()
        uid = mauth?.currentUser?.uid
        emailbtn = findViewById(R.id.email)
        passwordbtn = findViewById(R.id.password)
        mauth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        signUp?.setOnClickListener {
            val email = emailbtn?.text.toString()
            val password = passwordbtn?.text.toString()
            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Please Fill the credentials",Toast.LENGTH_SHORT).show()
            }
            else
            {
                signMeUp(email,password)
            }

        }
    }

    private fun signMeUp(email: String, password: String) {
        mauth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mauth?.currentUser?.sendEmailVerification()?.addOnCompleteListener(this) {
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this,"Email Verification link Sent! Kindly Verify",Toast.LENGTH_LONG).show()
                            saveData()
                            val intent = Intent(this,Login::class.java)
                            startActivity(intent)
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Cannot Sign Up Now Try Again!",Toast.LENGTH_LONG).show()
                    }
                }
                }
            }
    }

    private fun saveData() {
        val userId  = FirebaseAuth.getInstance().currentUser!!.uid
        val name = myname?.text.toString()
        val email = emailbtn?.text.toString()
        val password = passwordbtn?.text.toString()
        val userMap = hashMapOf(
            "Name" to name,"email" to email,"password" to password
        )
        db!!.collection("User").document(userId).set(userMap)
    }
}