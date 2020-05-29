package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.ui.base.BaseView

class ProfileDataContract {
    interface View : BaseView<Presenter>{
        fun onNameError(error: Int)
        fun onClearName()
        fun onDateError(error: Int)
        fun onClearDate()
        fun onGenderError(error: Int)
        fun onClearGender()
        fun onSuccessGetAuth(name: String, email: String)
        fun onSuccessGetDatabase( date: String, gender: String)
    }

    interface Presenter{
        fun loadData()
        fun logOut()
    }
}