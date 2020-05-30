package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.data.model.User

class FirebaseDatabaseRepository {

    interface LoginInteract {
        fun onSuccessLogin()
        fun needRegister(user: User)
    }

    interface RegisterInteract {
        fun onSuccessRegister()
        fun onSuccessRegisterWithGoogle()
    }

    interface ProfileDataInteract{
        fun onSuccessGetDatabaseData(
            gender: String,
            birthday: String,
            name: String,
            email: String
        )

        fun onSuccessUpdateDatabaseData()
    }

    companion object {
        private var INSTANCE: FirebaseDatabaseRepository? = null

        fun getInstance(): FirebaseDatabaseRepository {
            if (INSTANCE == null) {
                INSTANCE = FirebaseDatabaseRepository()
            }
            return INSTANCE as FirebaseDatabaseRepository
        }
    }

    private val database = FirebaseFirestore.getInstance()

    fun addUser(user: User, registerInteract: RegisterInteract, withGoogle: Boolean) {
        database.collection("User").document(user.uid).set(user).addOnCompleteListener {
            if (it.isSuccessful) {
                if (withGoogle){
                    registerInteract.onSuccessRegisterWithGoogle()
                }else{
                    registerInteract.onSuccessRegister()
                }
            }
        }
    }

        fun checkUser(user: FirebaseUser, loginInteract: LoginInteract) {
            database.collection("User").document(user.uid).get().addOnSuccessListener {
                if (it.exists()) {
                    loginInteract.onSuccessLogin()
                } else {
                    val newUser = User(user.uid, user.email!!).apply {
                        this.nameAndSurname = user.displayName!!;
                    }
                    loginInteract.needRegister(newUser)
                }
            }
        }

    fun getDataUser(
        uid: String,
        profileDataPresenter: ProfileDataInteract
    ) {
        database.collection("User").document(uid).get().addOnSuccessListener {
            if (it.exists()){
                profileDataPresenter.onSuccessGetDatabaseData(
                    it.get("gender").toString(),
                    it.get("birthday").toString(),
                    it.get("nameAndSurname").toString(),
                    it.get("email").toString()
                )
            }
        }
    }

    fun updateUser(
        name: String,
        date: String,
        gender: String,
        profileDataPresenter: ProfileDataInteract,
        user: String
    ) {
        database.collection("User").document(user).update("nameAndSurname",name,"birthday",date,"gender",gender).addOnSuccessListener {
            profileDataPresenter.onSuccessUpdateDatabaseData()
        }
    }
}