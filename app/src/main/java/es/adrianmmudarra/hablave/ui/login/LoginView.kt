package es.adrianmmudarra.hablave.ui.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.User
import kotlinx.android.synthetic.main.fragment_login_view.*


class LoginView : Fragment(), LoginContract.View {

    private val SIGN_IN: Int = 9001
    private lateinit var googleSignInClient: GoogleSignInClient

    private var onLoginViewInteract: OnLoginViewInteract? = null
    private var presenter: LoginContract.Presenter? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        if (presenter?.checkUserLogged()!!){
            presenter?.getUser()
            onLoginViewInteract?.onSuccessLogin()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        btnLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, SIGN_IN)
        }

        btnLoginConfirm.setOnClickListener {
            presenter?.checkData(tiledLoginEmail.text.toString(), tiledLoginPassword.text.toString())
        }

        btnLoginRegister.setOnClickListener {
            onLoginViewInteract?.onRegister(null)
        }

        tvLoginForgotPassword.setOnClickListener {
            val customview = layoutInflater.inflate(R.layout.layout_forgot_password, null)

            MaterialAlertDialogBuilder(context!!,R.style.AlertDialogTheme)
                .setTitle(R.string.introduce_correo_electronico)
                .setView(customview)
                .setNegativeButton(android.R.string.no,null)
                .setPositiveButton(android.R.string.ok){ _: DialogInterface, _: Int ->
                    presenter?.forgotPassword(customview.findViewById<TextInputEditText>(R.id.tiledForgotPasswordEmail).text.toString())
                }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                presenter?.signInWithGoogle(account!!)
            } catch (e: ApiException) {
                onSnakbarError(R.string.error_inicio_sesion_google)
            }
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
        fun onRegister(user: User?)
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

    override fun showLoading() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun disableLoading() {
        progressBarLogin.visibility = View.INVISIBLE
    }

    override fun onFailedLoginGoogle() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.error_generico))
            .setMessage(getString(R.string.error_inicio_sesion_google))
            .setPositiveButton(android.R.string.ok, null)
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
    }

    override fun onNotVerifiedEmail() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.email_no_verifiado))
            .setMessage(getString(R.string.correo_electronico_necesita_verificarse))
            .setPositiveButton(android.R.string.ok, null)
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
        presenter?.signout()
    }

    override fun needRegisterGoogle(user: User) {
        onLoginViewInteract?.onRegister(user)
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun onToastError(error: Int) {
        Toast.makeText(context!!, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun onToastError(error: String) {
        Toast.makeText(context!!, error, Toast.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: Int) {
        Snackbar.make(view!!, getString(error), Snackbar.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: String) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_SHORT).show()
    }
}
