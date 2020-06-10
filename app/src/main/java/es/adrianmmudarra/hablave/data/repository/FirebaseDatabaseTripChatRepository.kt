package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import es.adrianmmudarra.hablave.data.model.Message
import es.adrianmmudarra.hablave.data.model.Trip

class FirebaseDatabaseTripChatRepository {

    private val database = FirebaseFirestore.getInstance()

    interface TripChatInteract{
        fun addMessage(message: Message)
        fun noMessages()
        fun sendMessageSuccessful()
        fun removedTrip()
    }

    companion object{
        private var INSTANCE: FirebaseDatabaseTripChatRepository? = null

        fun getInstance(): FirebaseDatabaseTripChatRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseDatabaseTripChatRepository()
            }
            return INSTANCE as FirebaseDatabaseTripChatRepository
        }
    }

    fun getMessagesByTrip(uid: String, listener: TripChatInteract){
        database.collection("Trip").document(uid).collection("Messages").orderBy("createdAt").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException!= null){
                return@addSnapshotListener
            }

            if (querySnapshot!!.documentChanges.count() == 0){
                listener.noMessages()
            }

            loop@ for (dc in querySnapshot!!.documentChanges) {
                when(dc.type){
                    DocumentChange.Type.ADDED -> listener.addMessage(dc.document.toObject(Message::class.java))
                    DocumentChange.Type.REMOVED -> {
                        listener.removedTrip()
                        break@loop;
                    }
                }
            }
        }
    }

    fun sendMessagesByTrip(uid: String, message: Message, listener: TripChatInteract){
        database.collection("Trip").document(uid).collection("Messages").document(message.idTrip+"-"+message.createdAt).set(message).addOnSuccessListener {
            listener.sendMessageSuccessful()
        }.addOnFailureListener {

        }
    }
}