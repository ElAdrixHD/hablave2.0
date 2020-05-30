package es.adrianmmudarra.hablave.ui.profile

import com.google.firebase.auth.FirebaseAuth
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseRepository

class ProfileDataPresenter(val view: ProfileDataContract.View): ProfileDataContract.Presenter, FirebaseDatabaseRepository.ProfileDataInteract {
    override fun loadData() {
        view.showLoading()
        val user = FirebaseAuthRepository.getInstance().getCurrentUser()
        FirebaseDatabaseRepository.getInstance().getDataUser(user?.uid!!, this)

    }

    override fun logOut() {
        FirebaseAuthRepository.getInstance().signOut()
    }

    override fun updateProfile(name: String, date: String, gender: String) {
        view.showLoading()
        FirebaseDatabaseRepository.getInstance().updateUser(name, date, gender, this,FirebaseAuthRepository.getInstance().getCurrentUser()?.uid!!)
    }

    override fun onSuccessGetDatabaseData(
        gender: String,
        birthday: String,
        name: String,
        email: String
    ) {
        view.onSuccessGetDatabase(birthday, gender,name,email)
        view.disableLoading()
    }

    override fun onSuccessUpdateDatabaseData() {
        view.onSuccessUpdateDatabase()
        view.disableLoading()
    }
}