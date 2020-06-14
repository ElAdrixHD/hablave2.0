package es.adrianmmudarra.hablave.ui.chatlist

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
import kotlinx.android.synthetic.main.fragment_chat_list_view.*

class ChatListView : Fragment(), ChatListAdapter.CharListInterface, ChatListContract.View {

    private val adapter = ChatListAdapter(this)
    private var activity: ChatListInterface? = null
    private var presenter: ChatListContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        adapter.clear()
        presenter?.loadChats()
    }

    override fun onResume() {
        super.onResume()
        activity?.onResumeChatList()
    }

    override fun onStart() {
        super.onStart()
        if (this.isVisible){
            if(adapter.itemCount == 0)
                ivNoDataChatList.visibility = View.VISIBLE
            else
                ivNoDataChatList.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as ChatListInterface
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
        return inflater.inflate(R.layout.fragment_chat_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerChatListView.adapter = adapter
        recyclerChatListView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        const val TAG = "ChatListView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ChatListView().apply {
                arguments = bundle
            }
    }

    interface ChatListInterface{
        fun onResumeChatList()
        fun onClickTrip(trip: Trip)
    }

    override fun onClickChat(trip: Trip) {
        activity?.onClickTrip(trip)
    }

    override fun onSuccessTripList(list: ArrayList<Trip>) {
        adapter.addAll(list)
    }

    override fun onAddTrip(trip: Trip) {
        adapter.addTrip(trip)
        if (this.isVisible){
            ivNoDataChatList.visibility = View.GONE
        }
    }

    override fun noTrips() {
        onSnakbarError(R.string.no_tienes_viaje_aun)
        if (this.isVisible){
            ivNoDataChatList.visibility = View.VISIBLE
        }
    }

    override fun setPresenter(presenter: ChatListContract.Presenter) {
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