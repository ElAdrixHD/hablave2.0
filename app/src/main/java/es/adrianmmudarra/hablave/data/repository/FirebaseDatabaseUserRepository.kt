package es.adrianmmudarra.hablave.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.data.model.User

class FirebaseDatabaseUserRepository {

    interface LoginInteract {
        fun onSuccessLogin(user: User?)
        fun needRegister(user: User)
        fun onSuccessGetUser(user: User?)
    }

    interface RegisterInteract {
        fun onSuccessRegister()
        fun onSuccessRegisterWithGoogle()
    }

    interface ProfileDataInteract{
        fun onSuccessGetDatabaseData(user: User)

        fun onSuccessUpdateDatabaseData()
    }

    companion object {
        private var INSTANCE: FirebaseDatabaseUserRepository? = null

        fun getInstance(): FirebaseDatabaseUserRepository {
            if (INSTANCE == null) {
                INSTANCE = FirebaseDatabaseUserRepository()
            }
            return INSTANCE as FirebaseDatabaseUserRepository
        }
    }

    private val database = FirebaseFirestore.getInstance()

    fun addUser(user: User, registerInteract: RegisterInteract, withGoogle: Boolean) {
        database.collection("User").document(user.uid!!).set(user).addOnCompleteListener {
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
                    loginInteract.onSuccessLogin(it.toObject(User::class.java))
                } else {
                    val newUser = User().apply {
                        this.uid = user.uid
                        this.email = user.email
                        this.nameAndSurname = user.displayName!!;
                    }
                    loginInteract.needRegister(newUser)
                }
            }
        }

    fun checkUser(user: String, loginInteract: LoginInteract) {
        database.collection("User").document(user).get().addOnSuccessListener {
            if (it.exists()) {
                loginInteract.onSuccessLogin(it.toObject(User::class.java))
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
                    it.toObject(User::class.java)!!
                )
            }
        }
    }

    fun getDataUser(
        uid: String,
        loginInteract: LoginInteract
    ) {
        database.collection("User").document(uid).get().addOnSuccessListener {
            if (it.exists()){
                loginInteract.onSuccessGetUser(it.toObject(User::class.java))
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

        database.collection("User").document(user).get().addOnSuccessListener {
            if (it.exists()){
                HablaveApplication.context.user = it.toObject(User::class.java)
            }
        }
    }
}