package es.adrianmmudarra.hablave

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

class HablaveApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object{
        lateinit var context : HablaveApplication
            private set

        var userLogged : FirebaseUser? = null
    }


}