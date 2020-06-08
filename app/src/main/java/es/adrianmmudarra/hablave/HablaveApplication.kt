package es.adrianmmudarra.hablave

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseUserRepository

class HablaveApplication: Application() {

    var user: User? = null

    override fun onCreate() {
        super.onCreate()
        context = this
    }



    companion object{
        lateinit var context : HablaveApplication
            private set

    }



}