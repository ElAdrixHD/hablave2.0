package es.adrianmmudarra.hablave.ui.login

import android.R.attr
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FederatedAuthProvider
import com.google.firebase.auth.FirebaseAuth
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.ui.application.HablaveApplication
import es.adrianmmudarra.hablave.utils.isEmailValid

//TODO CREAR REPOSITORY Y CAMBIAR TODA ESTA MIERDA AL REPOSITORY
class LoginPresenter(private val view: LoginContract.View): LoginContract.Presenter {
    private var auth: FirebaseAuth? = null
    init {
        auth = FirebaseAuth.getInstance()
    }

    override fun checkData(email: String, password: String) {
        if(checkEmail(email) and checkPassword(password)){
            auth?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener{
                if (!it.isSuccessful) {
                    view.onFailedLogin()
                } else {
                    view.onSuccessLogin()
                }
            }
        }
    }

    private fun checkPassword(password: String): Boolean {
        return if (password.isEmpty()){
            view.onPasswordError(R.string.contrasenia_vacia)
            false
        }else{
            view.onClearPassword()
            true
        }
    }

    private fun checkEmail(email: String): Boolean {
        return if (email.isEmpty()){
            view.onEmailError(R.string.correo_vacio)
            false
        }else{
            if (!email.isEmailValid()){
                view.onEmailError(R.string.correo_invalido)
                false
            }else{
                view.onClearEmail()
                true
            }
        }
    }
}