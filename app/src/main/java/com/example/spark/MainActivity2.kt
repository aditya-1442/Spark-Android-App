package com.example.spark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var userArraylist : ArrayList<ReceivedUser>
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userArraylist = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("Messages")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for(dataSnapShot in snapshot.children){
                        val denewale = dataSnapShot.getValue(ReceivedUser::class.java)
                        if(!(userArraylist.contains(denewale)))
                        {
                            userArraylist.add(denewale!!)
                        }
                    }
                    recyclerView.adapter = MyAdapter(userArraylist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}