package es.adrianmmudarra.hablave.ui.chat

import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.data.model.Message
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseTripChatRepository
import java.util.*

class ChatTripPresenter(val view: ChatTripContract.View): ChatTripContract.Presenter, FirebaseDatabaseTripChatRepository.TripChatInteract {

    override fun sendMessage(message: String, uid: String) {
        if (message.trim().isNotEmpty()){
            val mes = Message().apply { user = HablaveApplication.context.user?.nameAndSurname!!; idTrip = uid; this.message = message; createdAt = Calendar.getInstance().timeInMillis}
            FirebaseDatabaseTripChatRepository.getInstance().sendMessagesByTrip(uid,mes, this)
        }
    }

    override fun loadMessages(uid: String) {
        FirebaseDatabaseTripChatRepository.getInstance().getMessagesByTrip(uid, this)
    }

    override fun addMessage(message: Message) {
        view.getNewMessage(message)
    }

    override fun noMessages() {
        view.noMessages()
    }

    override fun sendMessageSuccessful() {
        view.sendMessageSuccessful()
    }

    override fun removedTrip() {
        view.removedTrip()
    }
}