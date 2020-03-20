package es.adrianmmudarra.hablave.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import es.adrianmmudarra.hablave.ui.application.HablaveApplication

class FirebaseRepository {

    interface LoginInteract{
        fun onSuccessLogin()
        fun onFailedLogin()
        fun notVerifiedEmail()
        fun onFailedLoginGoogle()
        fun onSuccessSendNewPassword()
        fun onFailedSendNewPassword()
    }

    companion object{
        private var INSTANCE: FirebaseRepository? = null

        fun getInstance(): FirebaseRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseRepository()
            }
            return INSTANCE as FirebaseRepository
        }
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun logInWithEmailAndPassword(email: String, pass: String, loginInteract: LoginInteract){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
             if(!it.isSuccessful){
                 loginInteract.onFailedLogin()
             }else{
                 if (!it.result?.user?.isEmailVerified!!){
                    loginInteract.notVerifiedEmail()
                 }else{
                     loginInteract.onSuccessLogin()
                 }
             }
        }
    }

    fun logInWithGoogle(acc: GoogleSignInAccount, loginInteract: LoginInteract) {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginInteract.onSuccessLogin()
                } else {
                    loginInteract.onFailedLoginGoogle()
                }
            }
    }

    fun forgotPassword(email: String, loginPresenter: LoginInteract) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                loginPresenter.onSuccessSendNewPassword()
            }else{
                loginPresenter.onFailedSendNewPassword()
            }
        }
    }

}