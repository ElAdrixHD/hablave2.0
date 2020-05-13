package es.adrianmmudarra.hablave.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile_data_view.*

class ProfileDataView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_data_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogOut.setOnClickListener {
            FirebaseAuthRepository.getInstance().signOut()
            startActivity(Intent(context, LoginActivity::class.java))
        }

        tiledProfileName.setText(FirebaseAuthRepository.getInstance().getCurrentUser()?.displayName)
        tiledProfileEmail.setText(FirebaseAuthRepository.getInstance().getCurrentUser()?.email)
    }

    companion object {
       const val TAG = "ProfileDataView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ProfileDataView().apply {
                arguments = bundle
            }
    }
}
