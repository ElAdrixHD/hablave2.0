package es.adrianmmudarra.hablave.ui.register

import com.google.firebase.auth.FirebaseUser
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.User
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.data.repository.FirebaseDatabaseRepository
import es.adrianmmudarra.hablave.utils.isEmailValid

//TODO AÑADIR MAS CASOS DE USO
class RegisterPresenter(private val view: RegisterContract.View): RegisterContract.Presenter, FirebaseAuthRepository.RegisterInteract, FirebaseDatabaseRepository.RegisterInteract {
    override fun checkData(
        name: String,
        date: String,
        gender: String,
        email: String,
        pass: String
    ) {
        if (checkName(name) and checkDate(date) and checkGender(gender) and checkEmail(email) and checkPassword(pass)){
            view.showLoading()
            FirebaseAuthRepository.getInstance().registerWithEmail(email,pass,this)
        }
    }

    private fun checkPassword(pass: String): Boolean {
        if (pass.isEmpty()){
            view.onPasswordError(R.string.campo_pass_vacio)
            return false
        }
        view.onClearPassword()
        return true
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

    override fun registerDatabase(
        uid: String,
        email: String,
        name: String,
        date: String,
        gender: String
    ) {
        FirebaseDatabaseRepository.getInstance().addUser(User(uid,email).apply { this.nameAndSurname = name; this.birthday = date; this.gender = gender },this,false)
    }

    override fun deleteUser() {
        FirebaseAuthRepository.getInstance().deleteUser(this)
    }

    override fun checkDataGoogle(
        name: String,
        date: String,
        gender: String,
        email: String,
        user: User?
    ) {
        if (checkName(name) and checkDate(date) and checkGender(gender) and checkEmail(email)){
            user?.apply { birthday = date; this.nameAndSurname = name; this.gender = gender; }
            FirebaseDatabaseRepository.getInstance().addUser(user!!, this, true)
        }
    }

    override fun onSuccessRegister(user: FirebaseUser) {
        view.disableLoading()
        view.onSuccessAuthRegister(user)
    }

    override fun onFailedRegister() {
        view.disableLoading()
        view.onFailedRegister(R.string.fallo_general)
    }

    override fun weakPassword() {
        view.disableLoading()
        view.onPasswordError(R.string.contrasenia_no_segura)
    }

    override fun invalidEmail() {
        view.disableLoading()
        view.onEmailError(R.string.correo_invalido)
    }

    override fun userExists() {
        view.disableLoading()
        view.onFailedRegister(R.string.correo_electronico_usado)
    }

    override fun onSuccessDelete() {
        view.disableLoading()
        view.onSuccessCancel()
    }

    override fun onSuccessRegister() {
        view.disableLoading()
        FirebaseAuthRepository.getInstance().signOut()
        view.onSuccessDatabaseRegister()
    }

    override fun onSuccessRegisterWithGoogle() {
        view.disableLoading()
        view.onSuccessDatabaseRegister()
    }
}