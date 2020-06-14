package es.adrianmmudarra.hablave.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.ChatListAdapter
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_profile_history_view.*

class ProfileHistoryView : Fragment() , ChatListAdapter.CharListInterface, ProfileHistoryContract.View{

    private var adapter: ChatListAdapter? = null

    private var activity: OnProfileHistoryInterface? = null

    private var presenter: ProfileHistoryContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        adapter?.clear()
        presenter?.loadTrips()
    }

    override fun onStart() {
        super.onStart()
        if (this.isVisible){
            if (adapter?.itemCount == 0){
                ivProfileHistoryNoData.visibility = View.VISIBLE
            }else{
                ivProfileHistoryNoData.visibility = View.GONE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as OnProfileHistoryInterface
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
        return inflater.inflate(R.layout.fragment_profile_history_view, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Snackbar.make((activity as PrincipalActivity).mainCoordinator,getString(R.string.no_tienes_viaje_aun), Snackbar.LENGTH_INDEFINITE)
        adapter = ChatListAdapter(this)
        recyclerHistory.adapter = adapter
        recyclerHistory.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        const val TAG = "ProfileHistoryView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ProfileHistoryView().apply {
                arguments = bundle
            }
    }

    interface OnProfileHistoryInterface{
        fun onClickHistoryTrip(trip: Trip)
    }

    override fun onClickChat(trip: Trip) {
        activity?.onClickHistoryTrip(trip)
    }

    override fun addTrip(trip: Trip) {
        adapter?.addTrip(trip)
        ivProfileHistoryNoData.visibility = View.GONE
    }

    override fun noTrips() {
        onSnakbarError(R.string.no_tienes_viaje_aun)
        ivProfileHistoryNoData.visibility = View.VISIBLE
    }

    override fun setPresenter(presenter: ProfileHistoryContract.Presenter) {
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
