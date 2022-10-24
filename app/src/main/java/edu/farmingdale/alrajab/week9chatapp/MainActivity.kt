package edu.farmingdale.alrajab.week9chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.farmingdale.alrajab.week9chatapp.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sendText.setOnClickListener {
            sendMsg()
        }
    }

    private fun sendMsg() {
        val database = Firebase.database
        val myRef = database.getReference("message")

        val key = myRef.push().key!!

        myRef.child(key).setValue(
            Message(key,
                "Moaath",
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