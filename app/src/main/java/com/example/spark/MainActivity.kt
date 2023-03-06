package com.example.spark

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.spark.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var db = Firebase.firestore
    private   var name :String? = null

    private lateinit var firebaseAuth : FirebaseAuth
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid

        binding.search.setOnClickListener {
            val url = "http://www.google.com"
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
        binding.update.setOnClickListener {
            db.collection("User").document(uid!!).get().addOnSuccessListener {
                name = it.data!!["Name"].toString()
                binding.heading.text = "Welcome to Spark $name"

            }.addOnFailureListener {
                Toast.makeText(this,"Not Working",Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CreatePostActivity::class.java)
            startActivity(intent)
        }
        binding.meme.setOnClickListener {
            val intent = Intent(this@MainActivity,MemeActivity::class.java)
            startActivity(intent)
        }
    }
}