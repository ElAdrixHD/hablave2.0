package es.adrianmmudarra.hablave.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.ui.base.BaseView

class LoginContract {
    interface View : BaseView<Presenter>{
        fun onEmailError(error: Int)
        fun onClearEmail()
        fun onPasswordError(error: Int)
        fun onClearPassword()
        fun onSuccessLogin()
        fun onFailedLogin()
        fun showLoading()
        fun disableLoading()
        fun onFailedLoginGoogle()
        fun onNotVerifiedEmail()
        fun needRegisterGoogle(user: User)
    }

    interface Presenter{
        fun checkData(email: String, password: String)
        fun signInWithGoogle(acc: GoogleSignInAccount)
        fun forgotPassword(email: String)
    }
}