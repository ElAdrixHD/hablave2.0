package es.adrianmmudarra.hablave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.utils.toFormatDate
import kotlinx.android.synthetic.main.trip_list_item.view.*

class TripListAdapter(val listener: OnTripListAdapterInterface): RecyclerView.Adapter<TripListAdapter.VH>() {
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrigin = itemView.findViewById<TextView>(R.id.tvOriginTripItem)
        val tvDestiny = itemView.findViewById<TextView>(R.id.tvDestinyTripItem)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDateTripItem)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPriceTripItem)
        val hasTicket = itemView.findViewById<ImageView>(R.id.ivHasTicketTripItem)
    }

    private val listTrip = ArrayList<Trip>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.trip_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return listTrip.count()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tvOrigin.text = listTrip[position].stationOrigin
        holder.tvDestiny.text = listTrip[position].stationDest
        holder.tvDate.text = listTrip[position].dateTrip.toFormatDate()
        holder.tvPrice.text = "${listTrip[position].price} €"
        if (!listTrip[position].hasTicket){
            holder.hasTicket.visibility = View.INVISIBLE
        }else{
            holder.hasTicket.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener { listener.onClick(listTrip[position]) }
        holder.itemView.setOnLongClickListener{
            listener.onLongClick(listTrip[position])
            return@setOnLongClickListener true
        }
    }

    fun addTrip(trip: Trip){
        this.listTrip.add(trip)
        this.notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<Trip>){
        this.listTrip.addAll(list)
        this.notifyDataSetChanged()
    }

    fun clear(){
        this.listTrip.clear()
        this.notifyDataSetChanged()
    }

    fun removeTrip(trip: Trip){
        this.listTrip.remove(trip)
        this.notifyDataSetChanged()
    }

    interface OnTripListAdapterInterface {
        fun onClick(trip: Trip)
        fun onLongClick(trip: Trip)
    }
}