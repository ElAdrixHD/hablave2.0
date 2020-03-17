package es.adrianmmudarra.hablave.ui.login

import es.adrianmmudarra.hablave.ui.base.BaseView

class LoginContract {
    interface View : BaseView<Presenter>{
        fun onEmailError(error: Int)
        fun onClearEmail()
        fun onPasswordError(error: Int)
        fun onClearPassword()
        fun onSuccessLogin()
        fun onFailedLogin()
    }

    interface Presenter{
        fun checkData(email: String, password: String)
    }
}