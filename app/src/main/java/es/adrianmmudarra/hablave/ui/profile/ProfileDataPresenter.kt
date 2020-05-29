package es.adrianmmudarra.hablave.ui.profile

import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseRepository

class ProfileDataPresenter(val view: ProfileDataContract.View): ProfileDataContract.Presenter, FirebaseDatabaseRepository.ProfileDataInteract {
    override fun loadData() {
        val user = FirebaseAuthRepository.getInstance().getCurrentUser()
        FirebaseDatabaseRepository.getInstance().getDataUser(user?.uid!!, this)
        view.onSuccessGetAuth(user.displayName.toString(), user.email.toString())

    }

    override fun logOut() {
        FirebaseAuthRepository.getInstance().signOut()
    }

    override fun onSuccessGetDatabaseData(gender: String, birthday: String) {
        view.onSuccessGetDatabase(birthday,gender)
    }
}