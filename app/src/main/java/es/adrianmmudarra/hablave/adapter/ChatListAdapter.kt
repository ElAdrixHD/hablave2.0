package es.adrianmmudarra.hablave.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.utils.toFormatDate

class ChatListAdapter(private val listener: CharListInterface): RecyclerView.Adapter<ChatListAdapter.VH>() {

    private val list = ArrayList<Trip>()

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.findViewById<TextView>(R.id.tvDateChatList)
        val tvOrigin = itemView.findViewById<TextView>(R.id.tvStationOriginChatList)
        val tvDestiny = itemView.findViewById<TextView>(R.id.tvStationDestinyChatList)

        val constraint = itemView.findViewById<ConstraintLayout>(R.id.clChatTripItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tvDate.text = list[position].dateTrip.toFormatDate()
        holder.tvOrigin.text = list[position].stationOrigin
        holder.tvDestiny.text = list[position].stationDest

        holder.itemView.setOnClickListener {
            listener.onClickChat(list[position])
        }

        if (list[position].owner != HablaveApplication.context.user?.uid){
            holder.constraint.setBackgroundColor(Color.LTGRAY)
        }
    }

    fun addTrip(trip: Trip){
        this.list.add(trip)
        this.list.sortWith(compareBy({it.dateTrip}, {it.price}))
        this.notifyDataSetChanged()
    }

    fun clear(){
        this.list.clear()
        this.notifyDataSetChanged()
    }

    fun addAll(list: java.util.ArrayList<Trip>) {
        this.list.addAll(list)
        this.list.sortWith(compareBy({it.dateTrip}, {it.price}))
        this.notifyDataSetChanged()
    }

    interface CharListInterface{
        fun onClickChat(trip: Trip)
    }
}