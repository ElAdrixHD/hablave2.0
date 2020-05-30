package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.ui.base.BaseView

class ProfileDataContract {
    interface View : BaseView<Presenter>{
        fun onNameError(error: Int)
        fun onClearName()
        fun onDateError(error: Int)
        fun onClearDate()
        fun onGenderError(error: Int)
        fun showLoading()
        fun disableLoading()
        fun onClearGender()
        fun onSuccessGetDatabase(
            date: String,
            gender: String,
            name: String,
            email: String
        )
        fun onSuccessUpdateDatabase()
    }

    interface Presenter{
        fun loadData()
        fun logOut()
        fun updateProfile(name: String, date:String, gender: String)
    }
}