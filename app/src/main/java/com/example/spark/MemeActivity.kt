package com.example.spark

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MemeActivity : AppCompatActivity() {
    private var imageView : ImageView? = null
    private var nextBtn : Button ? = null
    private var shareBtn : Button? = null
    private var progress : ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)
        imageView = findViewById(R.id.MemeKaimage)
        progress = findViewById(R.id.progressBar)
        nextBtn = findViewById(R.id.nextButton)
        shareBtn = findViewById(R.id.ShareButton)
        loadmeme()
        nextBtn!!.setOnClickListener {
            nextMeme()
        }
        shareBtn!!.setOnClickListener {
            shareMeme()
        }




    }
    private fun loadmeme() {
        // Instantiate the RequestQueue.
        progress!!.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"
// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val url = it.getString("url")
                Glide.with(this).load(url).listener(
                    object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress!!.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress!!.visibility = View.GONE
                            return false
                        }
                    }
                ).into(imageView!!)

            },
            {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
            }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    private fun nextMeme() {
        loadmeme()
    }

    private fun shareMeme() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey CheckOut this Meme Isn't it Funny?")
        val chooser =
            Intent.createChooser(intent, "Sharing this meme using https://meme-api.com/gimme")
        startActivity(chooser)
    }
}