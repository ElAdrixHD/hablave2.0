package es.adrianmmudarra.hablave.ui.register

import com.google.firebase.auth.FirebaseUser
import es.adrianmmudarra.hablave.data.model.Gender
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.ui.base.BaseView

class RegisterContract {
    interface View: BaseView<Presenter>{
        fun onNameError(error: Int)
        fun onClearName()
        fun onDateError(error: Int)
        fun onClearDate()
        fun onEmailError(error: Int)
        fun onClearEmail()
        fun onPasswordError(error: Int)
        fun onClearPassword()
        fun onGenderError(error: Int)
        fun onClearGender()
        fun onSuccessAuthRegister(user: FirebaseUser)
        fun onSuccessDatabaseRegister()
        fun onFailedRegister(error: Int)
        fun showLoading()
        fun disableLoading()
    }

    interface Presenter{
        fun checkData(name:String, date: String, gender: String, email: String, pass: String, user: User? = null)
        fun registerDatabase(uid: String, email: String, name: String, date: String, gender: String)
    }
}