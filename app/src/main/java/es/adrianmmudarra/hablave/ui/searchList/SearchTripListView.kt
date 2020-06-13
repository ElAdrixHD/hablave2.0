package es.adrianmmudarra.hablave.ui.searchList

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.TripListAdapter
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_search_trip_list_view.*

class SearchTripListView : Fragment(), TripListAdapter.OnTripListAdapterInterface, SearchListContract.View{

    private var adapter : TripListAdapter? = null
    private var presenter : SearchListContract.Presenter? = null
    private var activity: OnSearchTripListViewInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        adapter?.clear()
        presenter?.loadTrips(arguments?.getParcelable("ORIGIN")!!, arguments?.getParcelable("DESTINY")!!, arguments?.getString("DATE")!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as OnSearchTripListViewInterface
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.trips_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sortPriceAsc->adapter?.sortPriceAsc()
            R.id.sortPriceDesc->adapter?.sortPriceDesc()
        }
        return super.onOptionsItemSelected(item)
    }

    interface OnSearchTripListViewInterface{
        fun onSelectedTrip(trip: Trip)
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
        activity?.onSelectedTrip(trip)
    }

    override fun onLongClick(trip: Trip) {
        if (trip.hasTicket){
            onSnakbarError(R.string.has_ticket)
        }else{
            onSnakbarError(R.string.no_ticket)
        }
    }

    override fun onSuccessTripList(list: ArrayList<Trip>) {
        if(this.isVisible){
            adapter?.clear()
            adapter?.addAll(list)
            ivNoDataSearchTripList.visibility = View.GONE
        }
    }

    override fun onAddTrip(trip: Trip) {
        if (this.isVisible){
            adapter?.addTrip(trip)
            if (adapter?.itemCount == 0){
                noTrips()
            }else{
                ivNoDataSearchTripList.visibility = View.GONE
            }
        }
    }

    override fun onRemoveTrip(trip: Trip) {
        if (this.isVisible){
            adapter?.removeTrip(trip)
            if (adapter?.itemCount == 0){
                noTrips()
            }else{
                ivNoDataSearchTripList.visibility = View.GONE
            }
        }
    }

    override fun noTrips() {
        if (this.isVisible){
            adapter?.clear()
            ivNoDataSearchTripList.visibility = View.VISIBLE
        }
    }

    override fun onUpdatedTrip(trip: Trip) {
        adapter?.updateTrip(trip)
    }

    override fun setPresenter(presenter: SearchListContract.Presenter) {
        this.presenter = presenter
    }

    override fun onToastError(error: Int) {
        Toast.makeText(context, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun onToastError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: Int) {
        Snackbar.make((activity as PrincipalActivity).mainCoordinator,getString(error), Snackbar.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: String) {
        Snackbar.make((activity as PrincipalActivity).mainCoordinator,error, Snackbar.LENGTH_SHORT).show()
    }
}