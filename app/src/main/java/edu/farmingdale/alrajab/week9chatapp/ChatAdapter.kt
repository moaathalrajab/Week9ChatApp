package edu.farmingdale.alrajab.week9chatapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: Context, val msgList: ArrayList<Message>) :
    RecyclerView.Adapter<ChatAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(msgList[position], context)
    }



    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.message, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return msgList.size
    }


    inner class Holder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val chatMsg = view?.findViewById<TextView>(R.id.chat_msg_body)
        val authr = view?.findViewById<TextView>(R.id.author_holder)
        val dte = view?.findViewById<TextView>(R.id.date_holder)

        fun bind(msg: Message, context: Context) {
            chatMsg?.text = msg.body
            authr?.text = msg.author
            if(msg.author=="Moaath") chatMsg?.setTextColor(Color.BLUE)

            dte?.text = msg.date

        }
    }
}