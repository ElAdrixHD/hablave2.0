package es.adrianmmudarra.hablave.ui.login

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

import es.adrianmmudarra.hablave.R
import kotlinx.android.synthetic.main.fragment_login_view.*

class LoginView : Fragment(), LoginContract.View {

    private var onLoginViewInteract: OnLoginViewInteract? = null
    private var presenter: LoginContract.Presenter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLoginConfirm.setOnClickListener {
            presenter?.checkData(tiledLoginEmail.text.toString(), tiledLoginPassword.text.toString())
        }

        btnLoginRegister.setOnClickListener {
            onLoginViewInteract?.onRegisterPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onLoginViewInteract = context as OnLoginViewInteract
    }

    override fun onDetach() {
        super.onDetach()
        onLoginViewInteract = null
    }

    companion object {
        const val TAG = "LoginView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            LoginView().apply {
                arguments = bundle
            }
    }

    interface OnLoginViewInteract{
        fun onSuccessLogin()
        fun onRegisterPressed()
    }

    override fun onEmailError(error: Int) {
        tilLoginEmail.error = getString(error)
    }

    override fun onClearEmail() {
        tilLoginEmail.error = null
    }

    override fun onPasswordError(error: Int) {
        tilLoginPassword.error = getString(error)
    }

    override fun onClearPassword() {
        tilLoginPassword.error = null
    }

    override fun onSuccessLogin() {
        onLoginViewInteract?.onSuccessLogin()
    }

    override fun onFailedLogin() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.usuario_incorrecto))
            .setMessage(getString(R.string.mensaje_usuario_incorrecto))
            .setPositiveButton(android.R.string.ok, null)
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onToastError(error: Int) {
        Toast.makeText(context!!, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: Int) {
        Snackbar.make(view!!, getString(error), Snackbar.LENGTH_SHORT).show()
    }
}
