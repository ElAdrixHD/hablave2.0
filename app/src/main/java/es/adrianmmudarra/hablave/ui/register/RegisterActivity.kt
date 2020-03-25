package es.adrianmmudarra.hablave.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.User

class RegisterActivity : AppCompatActivity(), RegisterEmailView.OnRegisterEmailViewInteract {

    private var registerEmailView: RegisterEmailView? = null

    private var registerPresenter: RegisterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        initialise()
    }

    override fun onDestroy() {
        super.onDestroy()
        registerEmailView = null
    }

    private fun initialise() {
        registerEmailView = supportFragmentManager.findFragmentByTag(RegisterEmailView.TAG) as RegisterEmailView?
        if (registerEmailView == null){
            var b: Bundle? = null
            if (intent.getParcelableExtra<User>(User.TAG) != null){
                b = Bundle().apply { putParcelable(User.TAG, intent.getParcelableExtra<User>(User.TAG)) }
            }
            registerEmailView = RegisterEmailView.newInstance(b)
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.contenido, registerEmailView!!, RegisterEmailView.TAG)
            .commit()

        registerPresenter = RegisterPresenter(registerEmailView!!)
        registerEmailView?.setPresenter(registerPresenter!!)
    }

    override fun onCancel() {
        finish()
    }

    override fun onSuccessRegister() {
        finish()
    }
}
