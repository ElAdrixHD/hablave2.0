package es.adrianmmudarra.hablave.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import es.adrianmmudarra.hablave.HablaveApplication

class FirebaseAuthRepository {

    interface LoginInteract{
        fun onSuccessLogin()
        fun onFailedLogin()
        fun notVerifiedEmail()
        fun onFailedLoginGoogle()
        fun onSuccessSendNewPassword()
        fun onFailedSendNewPassword()
        fun onSuccessLoginGoogle(user: FirebaseUser)
    }

    interface RegisterInteract{
        fun onSuccessRegister(user: FirebaseUser)
        fun onFailedRegister()
        fun weakPassword()
        fun invalidEmail()
        fun userExists()
    }

    companion object{
        private var INSTANCE: FirebaseAuthRepository? = null

        fun getInstance(): FirebaseAuthRepository{
            if (INSTANCE == null){
                INSTANCE = FirebaseAuthRepository()
            }
            return INSTANCE as FirebaseAuthRepository
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
                     HablaveApplication.userLogged = it.result?.user
                 }
             }
        }
    }

    fun logInWithGoogle(acc: GoogleSignInAccount, loginInteract: LoginInteract) {
        val credential = GoogleAuthProvider.getCredential(acc.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginInteract.onSuccessLoginGoogle(task.result?.user!!)
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

    fun registerWithEmail(email: String, pass: String, registerInteract: RegisterInteract){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){
                registerInteract.onSuccessRegister(auth.currentUser!!)
                auth.currentUser?.sendEmailVerification()
            }else{
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    registerInteract.weakPassword()
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    registerInteract.invalidEmail()
                } catch (e: FirebaseAuthUserCollisionException) {
                    registerInteract.userExists()
                }
            }
        }
    }

    fun signOut(){
        this.auth.signOut()
    }
}