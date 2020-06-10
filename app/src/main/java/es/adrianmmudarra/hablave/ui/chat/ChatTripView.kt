package es.adrianmmudarra.hablave.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.MessageListAdapter
import kotlinx.android.synthetic.main.fragment_chat_trip_view.*

class ChatTripView : Fragment() {

    private var adapter: MessageListAdapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_trip_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reyclerview_message_list.adapter = adapter
        reyclerview_message_list.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
            reverseLayout = true
        }
    }

    companion object {
        const val TAG = "ChatTripView"
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            ChatTripView().apply {
                arguments = bundle
            }
    }
}