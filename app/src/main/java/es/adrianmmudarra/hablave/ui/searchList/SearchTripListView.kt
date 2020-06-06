package es.adrianmmudarra.hablave.ui.searchList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.TripListAdapter
import es.adrianmmudarra.hablave.data.model.Trip
import kotlinx.android.synthetic.main.fragment_search_trip_list_view.*

class SearchTripListView : Fragment(), TripListAdapter.OnTripListAdapterInterface{

    var adapter : TripListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_trip_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TripListAdapter(this)
        recyclerSearchTripList.adapter = adapter
        recyclerSearchTripList.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        const val TAG = "SearchTripListView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            SearchTripListView().apply {
                arguments = bundle
            }
    }

    override fun onClick(trip: Trip) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(trip: Trip) {
        TODO("Not yet implemented")
    }
}