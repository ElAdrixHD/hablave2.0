package es.adrianmmudarra.hablave.ui.chat

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.MessageListAdapter
import es.adrianmmudarra.hablave.data.model.Message
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_chat_trip_view.*

class ChatTripView : Fragment(), ChatTripContract.View {

    private var adapter: MessageListAdapter = MessageListAdapter()
    private var presenter: ChatTripContract.Presenter? = null

    private var activity: OnChatTripInterface? = null

    interface OnChatTripInterface{
        fun onDeletedTrip()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        adapter.clear()
        presenter?.loadMessages(arguments?.getString("UID")!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as OnChatTripInterface
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
        return inflater.inflate(R.layout.fragment_chat_trip_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reyclerview_message_list.adapter = adapter
        reyclerview_message_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false).apply {
            stackFromEnd = true
        }

        button_chatbox_send.setOnClickListener {
            presenter?.sendMessage(edittext_chatbox.text.toString(), arguments?.getString("UID")!!)
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

    override fun getNewMessage(message: Message) {
        if (this.isVisible){
            adapter.add(message)
            reyclerview_message_list.smoothScrollToPosition(reyclerview_message_list.adapter?.itemCount?.minus(
                1
            )!!);
        }
    }

    override fun noMessages() {
    }

    override fun sendMessageSuccessful() {
        if (this.isVisible){
            edittext_chatbox.setText("")
        }
    }

    override fun removedTrip() {
        if (this.isVisible){
            MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                .setTitle(getString(R.string.trip_deleted))
                .setMessage(getString(R.string.mensaje_viaje_eliminado))
                .setPositiveButton(android.R.string.ok){ dialogInterface: DialogInterface, _: Int ->
                    activity?.onDeletedTrip()
                    dialogInterface.dismiss()
                }
                .setIcon(R.drawable.ic_warning_24dp)
                .show()
        }
    }

    override fun setPresenter(presenter: ChatTripContract.Presenter) {
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