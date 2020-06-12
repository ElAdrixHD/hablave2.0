package es.adrianmmudarra.hablave.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
        val ivCompleted = itemView.findViewById<ImageView>(R.id.ivCompletedTripItem)
        val constain = itemView.findViewById<ConstraintLayout>(R.id.constainTripItem)
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
        holder.tvPrice.text = "${listTrip[position].price} € "
        if (!listTrip[position].hasTicket){
            holder.hasTicket.visibility = View.INVISIBLE
        }else{
            holder.hasTicket.visibility = View.VISIBLE
        }
        if(listTrip[position].traveler1 == null || listTrip[position].traveler2 == null || listTrip[position].traveler3 == null){
            holder.itemView.setOnClickListener { listener.onClick(listTrip[position]) }
            holder.itemView.setOnLongClickListener{
                listener.onLongClick(listTrip[position])
                return@setOnLongClickListener true
            }
            holder.ivCompleted.visibility = View.GONE
            holder.constain.setBackgroundColor(Color.parseColor("#E1BEE7"))
        }else{
            holder.itemView.setOnClickListener { null }
            holder.itemView.setOnLongClickListener{
                null
                return@setOnLongClickListener true
            }
            holder.ivCompleted.visibility = View.VISIBLE
            holder.constain.setBackgroundColor(Color.LTGRAY)
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

    fun updateTrip(trip: Trip){
        if (this.listTrip.indexOf(trip) != -1){
            this.listTrip[this.listTrip.indexOf(trip)] = trip
            this.notifyDataSetChanged()
        }
    }

    fun sortPriceDesc() {
        this.listTrip.sortByDescending { x-> x.price }
        this.notifyDataSetChanged()
    }

    fun sortPriceAsc() {
        this.listTrip.sortBy { x->x.price }
        this.notifyDataSetChanged()
    }

    interface OnTripListAdapterInterface {
        fun onClick(trip: Trip)
        fun onLongClick(trip: Trip)
    }
}