package es.adrianmmudarra.hablave.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.adrianmmudarra.hablave.R

class LoginActivity : AppCompatActivity() {

    private var loginView: LoginView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login_register)
        initialise()
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
    }
}
