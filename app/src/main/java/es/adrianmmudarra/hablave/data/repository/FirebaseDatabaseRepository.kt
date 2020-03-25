package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.data.model.User

class FirebaseDatabaseRepository {

    interface LoginInteract{
        fun onSuccessLogin()
        fun needRegister(user: User)
    }

    interface RegisterInteract{
        fun onSuccessRegister()
        fun existEmail()
    }

    companion object{
        private var INSTANCE: FirebaseDatabaseRepository? = null

        fun getInstance(): FirebaseDatabaseRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseDatabaseRepository()
            }
            return INSTANCE as FirebaseDatabaseRepository
        }
    }

    private val database = FirebaseFirestore.getInstance()

    fun addUser(user: User, registerInteract: RegisterInteract){
        if (existsEmail(user.email)){
            registerInteract.existEmail()
        }else{
            database.collection("User").document(user.uid).set(user).addOnCompleteListener {
                if (it.isSuccessful){
                    registerInteract.onSuccessRegister()
                }
            }
        }
    }

    fun checkUser(user: FirebaseUser, loginInteract: LoginInteract){
        database.collection("User").document(user.uid).get().addOnSuccessListener {
            if (it.exists()){
                loginInteract.onSuccessLogin()
            }else{
                val newUser = User(user.uid,user.email!!).apply { this.nameAndSurname = user.displayName!!; }
                loginInteract.needRegister(newUser)
            }
        }
    }
     private fun existsEmail(email:String): Boolean{
         return database.collection("User").whereEqualTo("email", email).limit(1).get().result?.documents?.size == 1
     }
}