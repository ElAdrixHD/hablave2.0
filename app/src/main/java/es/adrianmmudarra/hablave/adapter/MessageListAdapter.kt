package es.adrianmmudarra.hablave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Message
import es.adrianmmudarra.hablave.utils.*

class MessageListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    private val list = ArrayList<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT){
            SentMessageHolder(LayoutInflater.from(parent.context).inflate(R.layout.send_message_item,parent,false))
        }else{
            ReciviedMessageHolder(LayoutInflater.from(parent.context).inflate(R.layout.recivied_message_item,parent,false))

        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT-> {
                (holder as SentMessageHolder).messageText.text = list[position].message
                holder.timeText.text = list[position].createdAt.toFormatHour()
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReciviedMessageHolder).messageText.text = list[position].message
                holder.timeText.text = list[position].createdAt.toFormatHour()
                holder.nameText.text = list[position].user
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return if (message.userId == HablaveApplication.context.user?.uid || message.user == HablaveApplication.context.user?.nameAndSurname ){
            VIEW_TYPE_MESSAGE_SENT
        }else{
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    fun addAll(list: ArrayList<Message>){
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    fun add(message: Message){
        this.list.add(message)
        this.notifyDataSetChanged()
    }

    fun clear(){
        this.list.clear()
        this.notifyDataSetChanged()
    }

    class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText = itemView.findViewById<TextView>(R.id.text_message_body)
        val timeText = itemView.findViewById<TextView>(R.id.text_message_time)
    }

    class ReciviedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText = itemView.findViewById<TextView>(R.id.text_message_body)
        val timeText = itemView.findViewById<TextView>(R.id.text_message_time)
        val nameText = itemView.findViewById<TextView>(R.id.text_message_name)
    }
}