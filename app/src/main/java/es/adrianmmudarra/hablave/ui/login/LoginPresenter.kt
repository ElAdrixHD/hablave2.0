package es.adrianmmudarra.hablave.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.repository.FirebaseRepository
import es.adrianmmudarra.hablave.utils.isEmailValid

class LoginPresenter(private val view: LoginContract.View): LoginContract.Presenter, FirebaseRepository.LoginInteract {

    override fun checkData(email: String, password: String) {
        if(checkEmail(email) and checkPassword(password)){
            view.showLoading()
            FirebaseRepository.getInstance().logInWithEmailAndPassword(email,password,this)
        }
    }

    override fun signInWithGoogle(acc: GoogleSignInAccount) {
        view.showLoading()
        FirebaseRepository.getInstance().logInWithGoogle(acc,this)
    }

    override fun forgotPassword(email: String) {
       if(checkForgotEmail(email)){
            view.showLoading()
            FirebaseRepository.getInstance().forgotPassword(email, this)
       }
    }

    private fun checkForgotEmail(email: String): Boolean {
        return if (email.isEmpty()){
            view.onToastError(R.string.correo_vacio)
            false
        }else{
            if (!email.isEmailValid()){
                view.onToastError(R.string.correo_invalido)
                false
            }else{
                view.onClearEmail()
                true
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

    override fun onSuccessLogin() {
        view.onSuccessLogin()
        view.disableLoading()
    }

    override fun onFailedLogin() {
        view.onFailedLogin()
        view.disableLoading()
    }

    override fun notVerifiedEmail() {
        view.onNotVerifiedEmail()
        view.disableLoading()
    }

    override fun onFailedLoginGoogle() {
        view.onFailedLoginGoogle()
        view.disableLoading()
    }

    override fun onSuccessSendNewPassword() {
        view.onSnakbarError(R.string.correo_recuperacion_enviado)
        view.disableLoading()
    }

    override fun onFailedSendNewPassword() {
        view.onToastError(R.string.no_existe_ese_correo)
        view.disableLoading()
    }
}