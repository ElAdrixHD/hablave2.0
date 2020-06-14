package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.data.model.User
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
        fun onSuccessGetDatabase(user: User
        )
        fun onSuccessUpdateDatabase()
        fun onDateUnderAge(error: Int)
    }

    interface Presenter{
        fun loadData()
        fun logOut()
        fun updateProfile(name: String, date:String, gender: String)
        fun changePassword()
    }
}