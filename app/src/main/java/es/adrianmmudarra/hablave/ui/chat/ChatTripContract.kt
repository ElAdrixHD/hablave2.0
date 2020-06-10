package es.adrianmmudarra.hablave.ui.chat

import es.adrianmmudarra.hablave.data.model.Message
import es.adrianmmudarra.hablave.ui.base.BaseView

class ChatTripContract {
    interface View : BaseView<Presenter>{
        fun getNewMessage(message: Message)
        fun noMessages()
        fun sendMessageSuccessful()
        fun removedTrip()
    }

    interface Presenter{
        fun sendMessage(message: String, uid: String)
        fun loadMessages(uid: String)
    }
}