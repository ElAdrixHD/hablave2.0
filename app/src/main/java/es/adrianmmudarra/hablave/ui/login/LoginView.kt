package es.adrianmmudarra.hablave.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.adrianmmudarra.hablave.R

class LoginView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_view, container, false)
    }

    companion object {
        const val TAG = "LoginView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            LoginView().apply {
                arguments = bundle
            }
    }
}
