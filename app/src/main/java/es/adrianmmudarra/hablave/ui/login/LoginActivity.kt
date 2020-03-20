package es.adrianmmudarra.hablave.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity

class LoginActivity : AppCompatActivity(), LoginView.OnLoginViewInteract {

    private var loginView: LoginView? = null
    private var loginPresenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        initialise()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.loginView = null
        this.loginPresenter = null
    }

    private fun initialise() {
        loginView = supportFragmentManager.findFragmentByTag(LoginView.TAG) as LoginView?
        if (loginView == null){
            loginView = LoginView.newInstance(null)
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.contenido, loginView!!, LoginView.TAG)
            .commit()

        loginPresenter = LoginPresenter(this.loginView!!)
        loginView?.setPresenter(this.loginPresenter!!)
    }

    override fun onSuccessLogin() {
        startActivity(Intent(this, PrincipalActivity::class.java))
        finish()
    }

    override fun onRegisterPressed() {
    }
}
