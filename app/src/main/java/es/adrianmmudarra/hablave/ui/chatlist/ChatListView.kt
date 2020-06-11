package es.adrianmmudarra.hablave.ui.chatlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.ChatListAdapter
import es.adrianmmudarra.hablave.data.model.Trip
import kotlinx.android.synthetic.main.fragment_chat_list_view.*

class ChatListView : Fragment(), ChatListAdapter.CharListInterface {

    private val adapter = ChatListAdapter(this)
    private var activity: ChatListInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
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
        fun newInstance(bundle: Bundle) =
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
}