package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseUserRepository
import es.adrianmmudarra.hablave.utils.getAge

class ProfileDataPresenter(val view: ProfileDataContract.View): ProfileDataContract.Presenter, FirebaseDatabaseUserRepository.ProfileDataInteract {
    override fun loadData() {
        view.showLoading()
        val user = FirebaseAuthRepository.getInstance().getCurrentUser()
        FirebaseDatabaseUserRepository.getInstance().getDataUser(user?.uid!!, this)
    }

    override fun logOut() {
        FirebaseAuthRepository.getInstance().signOut()
    }

    override fun updateProfile(name: String, date: String, gender: String) {
        if (checkDate(date) and checkName(name) and checkGender(gender)){
            view.showLoading()
            FirebaseDatabaseUserRepository.getInstance().updateUser(name, date, gender, this,FirebaseAuthRepository.getInstance().getCurrentUser()?.uid!!)
        }
    }

    override fun changePassword() {
        FirebaseAuthRepository.getInstance().sendPasswordReset()
    }

    private fun checkGender(gender: String): Boolean {
        if (gender.isEmpty()){
            view.onGenderError(R.string.campo_gender_vacio)
            return false
        }
        view.onClearGender()
        return true
    }

    private fun checkDate(date: String): Boolean {
        if (date.isEmpty()){
            view.onDateError(R.string.campo_fecha_vacio)
            return false
        }
        if(date.getAge()!! < 16){
            view.onDateUnderAge(R.string.date_underAge)
            return false
        }
        view.onClearDate()
        return true
    }

    private fun checkName(name: String): Boolean {
        if (name.isEmpty()){
            view.onNameError(R.string.campo_nombre_vacio)
            return false
        }
        view.onClearName()
        return true
    }

    override fun onSuccessGetDatabaseData(user: User
    ) {
        view.onSuccessGetDatabase(user)
        HablaveApplication.context.user = user
        view.disableLoading()
    }

    override fun onSuccessUpdateDatabaseData() {
        view.onSuccessUpdateDatabase()
        view.disableLoading()
    }
}