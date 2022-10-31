package edu.farmingdale.alrajab.week9chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import edu.farmingdale.alrajab.week9chatapp.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val database = Firebase.database
    lateinit var lst:ArrayList<Message>
    lateinit var myRef:DatabaseReference
    lateinit var recyclerView:RecyclerView
    lateinit var adptr:ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lst=ArrayList<Message>()
         myRef = database.getReference("message")

        myRef.get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")

            for (e in it.children) {
                val dataSip = e.getValue(Message::class.java)
                lst?.add(dataSip!!)

            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }.addOnCompleteListener { adptr.notifyDataSetChanged() }

         recyclerView= binding.recycler1
         adptr = ChatAdapter(this, lst)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.adapter = adptr
        recyclerView.smoothScrollToPosition(recyclerView.getBottom());


        binding.sendText.setOnClickListener {
            sendMsg()
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());

        }

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var msg:Message?= Message()
                for (e in snapshot.children)
                    msg = e.getValue(Message::class.java)
                    lst?.add(msg!!)
                adptr.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        }
            )
    }
    private fun sendMsg() {
        val key = myRef.push().key!!
        myRef.child(key).setValue(
            Message(key,
                "Nader",
                binding.inputMsg.text.toString(),
                getTimeFormated(),
                "none"
            )
        )
    }

    private fun getTimeFormated(): String? {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }
}