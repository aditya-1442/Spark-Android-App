package com.example.spark

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

@Suppress("DEPRECATION")
class CreatePostActivity : AppCompatActivity() {

    private var post : Button? = null
    private var message : EditText? = null
    private   var name :String? = null
    private lateinit var imagelink :Uri
    private lateinit var imageUri : String
    private var uploadImage : ImageView? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var mstore : StorageReference
    private lateinit var database: DatabaseReference
    private lateinit var firebaseFireStore : FirebaseFirestore
    private var realUid : String? = null //using it for saving realtime database
    private var realMessage : String? = null //using it for saving into realtime database
    private var db : FirebaseFirestore? = null
    private var mauth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        post = findViewById(R.id.postBtn)
        db = Firebase.firestore
        storage = FirebaseStorage.getInstance()
        mstore = FirebaseStorage.getInstance().getReference("Images")
        database = Firebase.database.reference
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        message = findViewById(R.id.postInput)
        firebaseFireStore = FirebaseFirestore.getInstance()
        mauth = FirebaseAuth.getInstance()
        uploadImage?.setOnClickListener {
                selectImage()
        }

        post!!.setOnClickListener {
            val text = message?.text.toString()
            val currentTime = System.currentTimeMillis()
            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            if(text.isEmpty())
            {
                Toast.makeText(this,"Enter the Message First",Toast.LENGTH_SHORT).show()
            }
            else{
                val postDetails = hashMapOf(
                    "uid" to uid,
                    "text" to text,"currentTime" to currentTime,
                )
                db!!.collection("Post").document(uid).set(postDetails).isSuccessful
                saveData()
                //yaha pe ayga upload image function
            }

        }
    }

    private fun saveData() {
        realUid = FirebaseAuth.getInstance().currentUser!!.uid
        realMessage = message?.text.toString().trim()
        db!!.collection("User").document(realUid!!).get().addOnSuccessListener {
            name = it.data!!["Name"].toString()


        }.addOnFailureListener {
            Toast.makeText(this,"Not Working",Toast.LENGTH_SHORT).show()
        }

        val upyog = UserModel(name,realUid,realMessage)
        database.child("Messages").child(realUid!!).setValue(upyog)
    }



    private fun selectImage()
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == 100 && resultCode == RESULT_OK)
            {
                imageUri = data?.data!!.toString()
                imagelink = imageUri.toUri()
                uploadImage?.setImageURI(imageUri.toUri())
            }

    }
}

